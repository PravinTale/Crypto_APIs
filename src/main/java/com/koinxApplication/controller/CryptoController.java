package com.koinxApplication.controller;

import com.koinxApplication.exceptions.CryptoNotFoundException;
import com.koinxApplication.repository.CryptoRepository;
import com.koinxApplication.service.CryptoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * The type Crypto controller.
 */
@RestController
@RequestMapping("/crypto")
public class CryptoController {
    /**
     * The Crypto repository.
     */
    @Autowired
    public CryptoRepository cryptoRepository;

    /**
     * The Crypto service.
     */
    @Autowired
    public CryptoServiceImpl cryptoService;

    /**
     * Gets crypto stats.
     *
     * @param coin the coin
     * @return the crypto stats
     */
// API to get the latest stats of a cryptocurrency
    @GetMapping("/stats/{coin}")
    public ResponseEntity<Map<String, Object>> getCryptoStats(@PathVariable String coin) {
        try {
            Map<String, Object> stats = cryptoService.getLatestCryptoStats(coin);
            return ResponseEntity.ok(stats); // Returns 200 OK with the stats
        } catch (CryptoNotFoundException e) {
            throw e; // Handled by global exception handler
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Something went wrong"));
        }
    }

    /**
     * Gets crypto price deviation.
     *
     * @param coin the coin
     * @return the crypto price deviation
     */
// API to calculate the standard deviation for the last 100 prices
    @GetMapping("/deviation/{coin}")
    public ResponseEntity<Map<String, Object>> getCryptoPriceDeviation(@PathVariable String coin) {
        try {
            double deviation = cryptoService.calculatePriceDeviation(coin);
            return ResponseEntity.ok(Map.of("deviation", deviation)); // 200 OK
        } catch (CryptoNotFoundException e) {
            throw e; // Handled by global exception handler
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Something went wrong"));
        }
    }


}
