package com.shahinnazarov.reactive.sse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/stock/transaction")
@CrossOrigin(origins = "*")
public class StockResource {
    @Autowired
    StockTransactionService stockTransactionService;

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<StockTransaction>> stockTransactionEvents(){
        return stockTransactionService.getStockTransactions();
    }
}
