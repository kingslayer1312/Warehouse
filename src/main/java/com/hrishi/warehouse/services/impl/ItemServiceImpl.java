package com.hrishi.warehouse.services.impl;

import com.hrishi.warehouse.domain.Item;
import com.hrishi.warehouse.domain.ItemEntity;
import com.hrishi.warehouse.repositories.ItemRepository;
import com.hrishi.warehouse.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    @Autowired
    public ItemServiceImpl(final ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public Item create(final Item item) {
        final ItemEntity itemEntity = itemToItemEntity(item);
        final ItemEntity savedItemEntity = itemRepository.save(itemEntity);
        return itemEntityToItem(savedItemEntity);
    }

    private ItemEntity itemToItemEntity(Item item) {
        return ItemEntity.builder()
                .itemId(item.getItemId())
                .itemName(item.getItemName())
                .description(item.getDescription())
                .quantity(item.getQuantity())
                .price(item.getPrice()).build();
    }

    private Item itemEntityToItem(ItemEntity itemEntity) {
        return Item.builder()
                .itemId(itemEntity.getItemId())
                .itemName(itemEntity.getItemName())
                .description(itemEntity.getDescription())
                .quantity(itemEntity.getQuantity())
                .price(itemEntity.getPrice()).build();
    }

    @Override
    public Optional<Item> findById(Integer itemId) {
        final Optional<ItemEntity> foundItem = itemRepository.findById(itemId);
        return foundItem.map(item -> itemEntityToItem(item));
    }

}