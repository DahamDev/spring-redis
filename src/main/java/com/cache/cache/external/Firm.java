package com.cache.cache.external;

import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@RedisHash("Firm")
public class Firm implements Serializable {
    int id;
    String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
