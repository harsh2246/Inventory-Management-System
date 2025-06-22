package com.InventoryApp.Inventory.controller;

import com.InventoryApp.Inventory.models.Item;
import com.InventoryApp.Inventory.payload.ApiResponse;
import com.InventoryApp.Inventory.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Item>> createItemSupply(@RequestBody Item item) {
        Item created = inventoryService.createSupply(item);
        return ResponseEntity.ok(new ApiResponse<>(true, "Item created successfully", created));
    }

    @PostMapping("/reserve/{itemId}/{quantity}")
    public ResponseEntity<ApiResponse<String>> reserveItem(@PathVariable Long itemId, @PathVariable int quantity) {
        inventoryService.reserveItem(itemId, quantity);
        return ResponseEntity.ok(new ApiResponse<>(true, "Item reserved successfully", "Item ID: " + itemId));
    }

    @PostMapping("/cancel/{itemId}/{quantity}")
    public ResponseEntity<ApiResponse<String>> cancelReservationItem(@PathVariable Long itemId, @PathVariable int quantity) {
        inventoryService.cancelReservation(itemId, quantity);
        return ResponseEntity.ok(new ApiResponse<>(true, "Reservation cancelled", "Item ID: " + itemId));
    }

    @GetMapping("/available/{itemId}")
    public ResponseEntity<ApiResponse<Item>> checkAvailabilityItem(@PathVariable Long itemId) {
        Item item = inventoryService.checkAvailability(itemId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Item availability fetched", item));
    }
}
