package com.koinxApplication.service;

import com.koinxApplication.exceptions.CryptoNotFoundException;

import com.koinxApplication.model.Crypto;
import com.koinxApplication.repository.CryptoRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.*;

/**
 * The type Crypto service.
 */
@Service
@NoArgsConstructor
public class CryptoServiceImpl implements CryptoService {


    /**
     * The Coin url.
     */
    @Value("${coin.data.url}")
    private String coinUrl;

    /**
     * The Crypto repository.
     */
    @Autowired
    private CryptoRepository cryptoRepository;

    /**
     * The Rest template.
     */
    @Autowired
    private RestTemplate restTemplate;


    @Override
    @Scheduled(fixedRate = 7200000) // Runs every 2 hours
    public void fetchCryptoData() {
        List<String> coins = List.of("bitcoin", "matic-network", "ethereum");

        // URL to fetch current price ("usd"), market cap ("usd_market_cap"), and 24h change ("usd_24h_change")

        try {
            // Create the comma-separated string of coin ids
            String ids = String.join(",", coins);

            // Fetch data from the CoinGecko API
            Map<String, Map<String, Object>> response = restTemplate.getForObject(coinUrl, Map.class, ids);

            // Debug: Log the response structure
            System.out.println("API Response: " + response);

            // Ensure the response is not null and contains data
            if (response.isEmpty()) {
                throw new RuntimeException("Received null response from CoinGecko API");
            }

            // Process each coin in the list
            addCoinData(coins, response);

        } catch (Exception e) {
            // Log the exception message to help identify the issue
            System.err.println("Error fetching data from CoinGecko API: " + e.getMessage());
        }
    }

    /**
     * Add coin data.
     *
     * @param coins    the coins
     * @param response the response
     */
    private void addCoinData(List<String> coins, Map<String, Map<String, Object>> response) {
        coins.forEach(coin -> {
            Map<String, Object> data = response.get(coin);

            if (data == null) {
                System.err.println("No data found for coin: " + coin);
                return;
            }

            try {
                // Extract the required values, ensuring proper casting
                double priceInUsd = (data.get("usd") instanceof Number) ? ((Number) data.get("usd")).doubleValue() : 0;
                double marketCap = (data.get("usd_market_cap") instanceof Number) ? ((Number) data.get("usd_market_cap")).doubleValue() : 0;
                double change24h = (data.get("usd_24h_change") instanceof Number) ? ((Number) data.get("usd_24h_change")).doubleValue() : 0;

                // Create a Crypto object and save it to the repository
                Crypto crypto = new Crypto();
                crypto.setCoin(coin);
                crypto.setPrice(priceInUsd);
                crypto.setMarketCap(marketCap);
                crypto.setChange24h(change24h);
                crypto.setCreatedAt(LocalDateTime.now());
                cryptoRepository.save(crypto);  // Save to MongoDB

            } catch (Exception e) {
                System.err.println("Error parsing data for " + coin + ": " + e.getMessage());
            }
        });
    }

    @Override
    public Map<String, Object> getLatestCryptoStats(String coin) {
        List<Crypto> latestCrypto = cryptoRepository.findTopByCoinOrderByCreatedAtDesc(coin);
        if (latestCrypto == null || latestCrypto.isEmpty()) {
            throw new CryptoNotFoundException("Cryptocurrency not found: " + coin);
        }
        Map<String, Object> data = new HashMap<>();
        latestCrypto.forEach(crypto -> {
            if (crypto.getCoin().equals(coin)) {

                data.put("price", crypto.getPrice());
                data.put("marketCap", crypto.getMarketCap());
                data.put("24hChange", crypto.getChange24h());

            }
        });
        return data;
    }

    @Override
    public double calculatePriceDeviation(String coin) {
        // Fetch the latest records for the coin (limit to 100 or whatever is available)
        List<Crypto> cryptoList = cryptoRepository.findTopNByCoinOrderByCreatedAtDesc(coin);

        // If there are no records, return 0 or throw an exception
        if (cryptoList.isEmpty()) {
            throw new RuntimeException("No data found for the coin: " + coin);
        }

        // Calculate the mean price
        double mean = cryptoList.stream()
                .mapToDouble(Crypto::getPrice) // Assuming getPrice() returns the price of the crypto
                .average()
                .orElse(0);

        // Calculate the squared differences from the mean
        double variance = cryptoList.stream()
                .mapToDouble(crypto -> Math.pow(crypto.getPrice() - mean, 2))
                .average()
                .orElse(0);

        // Return the standard deviation
        return Math.sqrt(variance);
    }

}
