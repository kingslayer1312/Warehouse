package com.hrishi.warehouse.services;

import com.hrishi.warehouse.domain.Item;

import java.util.List;
import java.util.Optional;

public interface ItemService {

    boolean doesItemExist(Item item);
    Item save(Item item);
    Optional<Item> findById(Integer itemId);
    List<Item> listItems();
    void deleteItemById(Integer itemId);
}
