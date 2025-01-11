package com.koinxApplication.model;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * The type Crypto.
 */
@Component
@Document(collection = "koinx")

public class Crypto {
    /**
     * The Id.
     */
    @Id
    private String id;
    /**
     * The Coin.
     */
    private String coin;
    /**
     * The Price.
     */
    private double price;
    /**
     * The Market cap.
     */
    private double marketCap;
    /**
     * The Change 24 h.
     */
    private double change24h;
    /**
     * The Created at.
     */
    @CreatedDate
    private LocalDateTime createdAt; // Timestamp for when the record is created


    /**
     * Instantiates a new Crypto.
     */
    public Crypto() {

    }


    /**
     * Instantiates a new Crypto.
     *
     * @param id        the id
     * @param coin      the coin
     * @param price     the price
     * @param marketCap the market cap
     * @param change24h the change 24 h
     * @param createdAt the created at
     */
    public Crypto(String id, String coin, double price, double marketCap, double change24h, LocalDateTime createdAt) {
        this.id = id;
        this.coin = coin;
        this.price = price;
        this.marketCap = marketCap;
        this.change24h = change24h;
        this.createdAt = createdAt;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets coin.
     *
     * @return the coin
     */
    public String getCoin() {
        return coin;
    }

    /**
     * Sets coin.
     *
     * @param coin the coin
     */
    public void setCoin(String coin) {
        this.coin = coin;
    }

    /**
     * Gets price.
     *
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets price.
     *
     * @param price the price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Gets market cap.
     *
     * @return the market cap
     */
    public double getMarketCap() {
        return marketCap;
    }

    /**
     * Sets market cap.
     *
     * @param marketCap the market cap
     */
    public void setMarketCap(double marketCap) {
        this.marketCap = marketCap;
    }

    /**
     * Gets change 24 h.
     *
     * @return the change 24 h
     */
    public double getChange24h() {
        return change24h;
    }

    /**
     * Sets change 24 h.
     *
     * @param change24h the change 24 h
     */
    public void setChange24h(double change24h) {
        this.change24h = change24h;
    }

    /**
     * Gets created at.
     *
     * @return the created at
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets created at.
     *
     * @param createdAt the created at
     */
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
