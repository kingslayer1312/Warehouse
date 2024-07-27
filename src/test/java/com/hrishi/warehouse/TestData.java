package com.hrishi.warehouse;

import com.hrishi.warehouse.domain.Item;
import com.hrishi.warehouse.domain.ItemEntity;

public final class TestData {
    private TestData() {

    }

    public static Item testItem() {
        return Item.builder()
                .itemId(101)
                .itemName("Televisions")
                .description("55 inch Plasma TV")
                .quantity(120)
                .price(1999.99F)
                .build();
    }

    public static ItemEntity testItemEntity() {
        return ItemEntity.builder()
                .itemId(101)
                .itemName("Televisions")
                .description("55 inch Plasma TV")
                .quantity(120)
                .price(1999.99F)
                .build();
    }
}
