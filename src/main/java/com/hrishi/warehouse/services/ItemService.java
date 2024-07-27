package com.hrishi.warehouse.services;

import com.hrishi.warehouse.domain.Item;
import org.springframework.stereotype.Service;

import java.util.Optional;

public interface ItemService {

    Item create(Item item);
    Optional<Item> findById(Integer itemId);
}
