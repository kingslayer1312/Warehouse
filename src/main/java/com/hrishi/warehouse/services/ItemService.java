package com.hrishi.warehouse.services;

import com.hrishi.warehouse.domain.Item;
import org.springframework.stereotype.Service;

@Service
public interface ItemService {

    Item create(Item item);
}
