package com.hrishi.warehouse.controllers;

import com.hrishi.warehouse.domain.Item;
import com.hrishi.warehouse.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class ItemController {
    private final ItemService itemService;

    @Autowired
    public ItemController(final ItemService itemService) {
        this.itemService = itemService;
    }

    @PutMapping(path = "/items/{itemId}")
    public ResponseEntity<Item> createItem(
            @PathVariable final Integer itemId,
            @RequestBody final Item item) {
        item.setItemId(itemId);
        final Item savedItem = itemService.create(item);
        return new ResponseEntity<Item>(savedItem, HttpStatus.CREATED);
    }

    @GetMapping(path = "/items/{itemId}")
    public ResponseEntity<Item> retrieveItem(@PathVariable final Integer itemId) {
        final Optional<Item> foundItem = itemService.findById(itemId);
        return foundItem.map(item -> new ResponseEntity(item, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}
