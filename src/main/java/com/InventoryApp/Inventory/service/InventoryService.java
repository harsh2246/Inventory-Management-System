package com.InventoryApp.Inventory.service;

import com.InventoryApp.Inventory.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import com.InventoryApp.Inventory.models.Item;

@Service
public class InventoryService {
    @Autowired
    ItemRepository itemRepository;
    // 1. Create item supply
    public Item createSupply(Item item) {
        return itemRepository.save(item);
    }

    // 2. Reserve quantity (concurrent-safe)
    public boolean reserveItem(Long itemId, int quantity) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item not found"));

        if (item.getAvailableQuantity() >= quantity) {
            item.setAvailableQuantity(item.getAvailableQuantity() - quantity);
            itemRepository.save(item);
            return true;
        } else {
            return false;
        }
    }

    // 3. Cancel reservation
    public void cancelReservation(Long itemId, int quantity) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item not found"));

        item.setAvailableQuantity(item.getAvailableQuantity() + quantity);
        itemRepository.save(item);
    }

    // 4. Query availability
    @Cacheable(value = "itemAvailability", key = "#itemId")
    public Item checkAvailability(Long itemId) {
        System.out.println("Fetching from DB for itemId: " + itemId);
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item not found"));

        return item;
    }
    /*
    * I have used value	itemAvailability → used as a prefix
key	#itemId → becomes 5, 6, etc.
Full Redis Key	itemAvailability::5
Redis Value	Serialized return of checkAvailability(5)
    * */
}
