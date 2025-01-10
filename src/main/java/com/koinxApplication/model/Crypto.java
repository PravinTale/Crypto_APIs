package com.koinxApplication.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Document(collation = "cryptos")
@Getter
@Setter
@AllArgsConstructor
public class Crypto {
    @Id
    private String id;
    private String coin;
    private double price;
    private double marketCap;
    private double change24h;
    private LocalDateTime createdAt;

}
