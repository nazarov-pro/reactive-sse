package com.shahinnazarov.reactive.sse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockTransaction {
    private String user;
    private Stock stock;
    private Date when;
}
