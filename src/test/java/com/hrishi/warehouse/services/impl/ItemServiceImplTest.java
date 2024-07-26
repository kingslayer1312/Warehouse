package com.hrishi.warehouse.services.impl;

import com.hrishi.warehouse.domain.Item;
import com.hrishi.warehouse.domain.ItemEntity;
import com.hrishi.warehouse.repositories.ItemRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ItemServiceImplTest {
    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private ItemServiceImpl underTest;

    @Test
    public void testIfItemIsSaved() {
        final Item item = Item.builder()
                .itemId(101)
                .itemName("Televisions")
                .description("55 inch Plasma TV")
                .quantity(120)
                .price(1999.99F)
                .build();
        final ItemEntity itemEntity = ItemEntity.builder()
                .itemId(101)
                .itemName("Televisions")
                .description("55 inch Plasma TV")
                .quantity(120)
                .price(1999.99F)
                .build();

        when(itemRepository.save(eq(itemEntity))).thenReturn(itemEntity);

        final Item result = underTest.create(item);
        assertEquals(item, result);

    }
}
