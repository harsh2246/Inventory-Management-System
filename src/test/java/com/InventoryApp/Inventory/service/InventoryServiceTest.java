package com.InventoryApp.Inventory.service;

import com.InventoryApp.Inventory.models.Item;
import com.InventoryApp.Inventory.repository.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InventoryServiceTest {

    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private InventoryService inventoryService;

    private Item item;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        item = new Item();
        item.setId(1L);
        item.setName("Laptop");
        item.setAvailableQuantity(10);
    }

    @Test
    void testCreateSupply() {
        when(itemRepository.save(any(Item.class))).thenReturn(item);
        Item created = inventoryService.createSupply(item);
        assertNotNull(created);
        assertEquals("Laptop", created.getName());
    }

    @Test
    void testReserveItemSuccess() {
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));
        boolean result = inventoryService.reserveItem(1L, 5);
        assertTrue(result);
        verify(itemRepository).save(any());
    }

    @Test
    void testReserveItemFail() {
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));
        boolean result = inventoryService.reserveItem(1L, 20);
        assertFalse(result);
        verify(itemRepository, never()).save(any());
    }

    @Test
    void testCancelReservation() {
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));
        inventoryService.cancelReservation(1L, 3);
        verify(itemRepository).save(argThat(saved -> saved.getAvailableQuantity() == 13));
    }

    @Test
    void testCheckAvailability() {
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));
        Item found = inventoryService.checkAvailability(1L);
        assertEquals("Laptop", found.getName());
    }
}
