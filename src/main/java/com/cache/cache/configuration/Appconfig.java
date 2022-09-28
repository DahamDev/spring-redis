package com.cache.cache.configuration;

import com.cache.cache.external.Firm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import java.time.Duration;

@Configuration
public class Appconfig {

    @Value("${spring.redis.port}")
    int port;

    @Value("${spring.redis.host}")
    String hostname;
    @Bean
    JedisConnectionFactory jedisConnectionFactory() {

        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(hostname);
        redisStandaloneConfiguration.setPort(port);

        JedisClientConfiguration.JedisClientConfigurationBuilder jedisClientConfiguration = JedisClientConfiguration.builder();
        jedisClientConfiguration.connectTimeout(Duration.ofSeconds(60));// 60s connection timeout

        JedisConnectionFactory jedisConFactory = new JedisConnectionFactory(redisStandaloneConfiguration,
                jedisClientConfiguration.build());

        return jedisConFactory;
    }

    @Bean(name="redisTemplate")
    public RedisTemplate<String, Firm> redisTemplate() {
        RedisTemplate<String, Firm> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());

        Jackson2JsonRedisSerializer<Firm> jsonRedisSerializer = new Jackson2JsonRedisSerializer<Firm>(Firm.class);

        template.setDefaultSerializer(jsonRedisSerializer);
        return template;
    }

}
