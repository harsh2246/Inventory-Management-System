package com.InventoryApp.Inventory.controller;

import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/redis")
public class RedisHealthController {

    @Autowired
    private RedissonClient redissonClient;

    @GetMapping("/health")
    public ResponseEntity<String> checkRedis() {
        try {
            // Simple health check: set and get a key
            redissonClient.getBucket("health-check").set("ok");
            String value = (String) redissonClient.getBucket("health-check").get();

            if ("ok".equals(value)) {
                return ResponseEntity.ok(" Redis is connected and working.");
            } else {
                return ResponseEntity.status(500).body("Redis connected, but value check failed.");
            }

        } catch (Exception e) {
            return ResponseEntity
                    .status(500)
                    .body("Redis is not reachable: " + e.getMessage());
        }
    }
}
