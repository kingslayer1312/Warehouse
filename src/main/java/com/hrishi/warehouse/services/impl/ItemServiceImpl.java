package com.hrishi.warehouse.services.impl;

import com.hrishi.warehouse.domain.Item;
import com.hrishi.warehouse.domain.ItemEntity;
import com.hrishi.warehouse.repositories.ItemRepository;
import com.hrishi.warehouse.services.ItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    @Autowired
    public ItemServiceImpl(final ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public Item save(final Item item) {
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
        return foundItem.map(this::itemEntityToItem);
    }

    @Override
    public List<Item> listItems() {
        final List<ItemEntity> foundItems = itemRepository.findAll();
        return foundItems.stream().map(item -> itemEntityToItem(item)).collect(Collectors.toList());
    }

    @Override
    public void deleteItemById(Integer itemId) {
        try {
            itemRepository.deleteById(itemId);
        }
        catch (final EmptyResultDataAccessException ex) {
            log.debug("Attempted to delete non-existent item", ex);
        }
    }

    @Override
    public boolean doesItemExist(Item item) {
        return itemRepository.existsById(item.getItemId());
    }
}