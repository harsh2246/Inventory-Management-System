package com.InventoryApp.Inventory.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.github.cdimascio.dotenv.Dotenv;

@Configuration
public class RedissonConfig {

    @Bean
    public RedissonClient redissonClient() {
        Dotenv dotenv = Dotenv.configure().load();
        String redisHost = dotenv.get("REDIS_HOST");
        String redisPort = dotenv.get("REDIS_PORT");
        String redisPassword = dotenv.get("REDIS_PASSWORD");
        Config config = new Config();
        config.useSingleServer()
                .setAddress("redis://" + redisHost + ":" + redisPort)
                .setPassword(redisPassword)
                .setConnectionPoolSize(5)
                .setConnectionMinimumIdleSize(1)
                .setTimeout(10000);
        return Redisson.create(config);
    }
    @Bean
    public RedissonSpringCacheManager cacheManager(RedissonClient redissonClient) {
        return new RedissonSpringCacheManager(redissonClient);
    }
    //this i have done to because spring uses its own in memory cache\
    //so to remove that i want it uses redsis spring cahce i made this

}
