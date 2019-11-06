package com.shahinnazarov.reactive.sse;

import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;

import java.time.Duration;
import java.util.*;
import java.util.stream.Stream;

@Service
public class StockTransactionService {
    List<Stock> stockList = new ArrayList<>();
    List<String> stockNames = Arrays.asList("mango,banana,guava,infinity".split(","));

    public Flux<ServerSentEvent<StockTransaction>> getStockTransactions() {

        Flux<Long> interval = Flux.interval(Duration.ofSeconds(1));

        interval.subscribe((i) -> stockList.forEach(stock -> stock.setPrice(changePrice(stock.getPrice()))));

        Flux<ServerSentEvent<StockTransaction>> stockTransactionFlux = Flux.fromStream(Stream.generate(() ->
                ServerSentEvent.<StockTransaction>builder()
                        .data(new StockTransaction(getRandomUser(), getRandomStock(), new Date()))
                        .event("eone")
                        .id(UUID.randomUUID().toString())
                        .comment("HEY")
                        .build()
                ));

        return Flux.zip(interval, stockTransactionFlux).map(Tuple2::getT2);

    }

    {
        stockNames.forEach(stockName -> {
            stockList.add(new Stock(stockName, generateRandomStockPrice()));
        });
    }

    float generateRandomStockPrice() {
        float min = 30;
        float max = 50;
        return min + roundFloat(new Random().nextFloat() * (max - min));
    }

    float changePrice(float price) {
        return roundFloat(Math.random() >= 0.5 ? price * 1.05f : price * 0.95f);
    }

    String getRandomUser() {
        String users[] = "adam,tom,john,mike,bill,tony".split(",");
        return users[new Random().nextInt(users.length)];
    }

    Stock getRandomStock() {
        return stockList.get(new Random().nextInt(stockList.size()));
    }

    float roundFloat(float number) {
        return Math.round(number * 100.0) / 100.0f;
    }
}
