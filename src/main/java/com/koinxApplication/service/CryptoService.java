package com.koinxApplication.service;

import java.util.Map;

/**
 * The interface Crypto service.
 */
public interface CryptoService {
    /**
     * Fetch crypto data.
     */
    public void fetchCryptoData();

    /**
     * Gets latest crypto stats.
     *
     * @param coin the coin
     * @return the latest crypto stats
     */
    public Map<String, Object> getLatestCryptoStats(String coin);

    /**
     * Calculate price deviation double.
     *
     * @param coin the coin
     * @return the double
     */
    public double calculatePriceDeviation(String coin);
}
