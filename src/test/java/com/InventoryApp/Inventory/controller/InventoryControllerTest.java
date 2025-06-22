package com.InventoryApp.Inventory.controller;

import com.InventoryApp.Inventory.models.Item;
import com.InventoryApp.Inventory.payload.ApiResponse;
import com.InventoryApp.Inventory.service.InventoryService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(InventoryController.class)
class InventoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InventoryService inventoryService;

    private Item item;

    @BeforeEach
    void setup() {
        item = new Item();
        item.setId(1L);
        item.setName("Laptop");
        item.setAvailableQuantity(10);
    }

    @Test
    void testCreateItemSupply() throws Exception {
        when(inventoryService.createSupply(any())).thenReturn(item);

        mockMvc.perform(post("/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(item)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Item created successfully"))
                .andExpect(jsonPath("$.data.name").value("Laptop"));
    }

    @Test
    void testReserveItem() throws Exception {
        when(inventoryService.reserveItem(1L, 5)).thenReturn(true);

        mockMvc.perform(post("/reserve/1/5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Item reserved successfully"));
    }

    @Test
    void testCancelReservation() throws Exception {
        doNothing().when(inventoryService).cancelReservation(1L, 3);

        mockMvc.perform(post("/cancel/1/3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Reservation cancelled"));
    }

    @Test
    void testCheckAvailabilityItem() throws Exception {
        when(inventoryService.checkAvailability(1L)).thenReturn(item);

        mockMvc.perform(get("/available/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Item availability fetched"))
                .andExpect(jsonPath("$.data.availableQuantity").value(10));
    }
}
