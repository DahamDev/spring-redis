package com.cache.cache.external;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository
public class FirmRepositoryImpl implements FirmRepository {

    private final String hashReference = "Firm";

    @Resource(name="redisTemplate")
    private HashOperations<String, Integer, Firm> hashOperations;

    @Override
    public void saveFirm(Firm firm) {
        hashOperations.put(hashReference, firm.getId(), firm);
    }


    @Override
    public Firm getFirm(Integer id) {
        return hashOperations.get(hashReference, id);
    }
}


