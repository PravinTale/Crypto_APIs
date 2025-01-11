package com.koinxApplication.repository;

import com.koinxApplication.model.Crypto;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The interface Crypto repository.
 */
@Repository
public interface CryptoRepository extends MongoRepository<Crypto, String> {
    /**
     * Find top by coin order by created at desc list.
     *
     * @param coin the coin
     * @return the list
     */
    @Query("{coin : ?0}")
    List<Crypto> findTopByCoinOrderByCreatedAtDesc(String coin);

    /**
     * Find top n by coin order by created at desc crypto.
     *
     * @param coin the coin
     * @param n    the n
     * @return the crypto
     */
    @Query("{coin : ?0")
    List<Crypto> findTopNByCoinOrderByCreatedAtDesc(String coin);

}
