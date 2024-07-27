package com.hrishi.warehouse.services.impl;

import com.hrishi.warehouse.domain.Item;
import com.hrishi.warehouse.domain.ItemEntity;
import com.hrishi.warehouse.repositories.ItemRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.hrishi.warehouse.TestData.testItem;
import static com.hrishi.warehouse.TestData.testItemEntity;
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
        final Item item = testItem();
        final ItemEntity itemEntity = testItemEntity();

        when(itemRepository.save(eq(itemEntity))).thenReturn(itemEntity);

        final Item result = underTest.create(item);
        assertEquals(item, result);

    }

    @Test
    public void testThatFindByIdReturnsEmptyWhenNoItem() {
        final Integer itemId = 1001;
        when(itemRepository.findById(eq(itemId))).thenReturn(Optional.empty());
        final Optional<Item> result = underTest.findById(itemId);
        assertEquals(Optional.empty(), result);
    }

    @Test
    public void testThatFindByIdReturnsBookWhenExists() {
        final Item item = testItem();
        final ItemEntity itemEntity = testItemEntity();

        when(itemRepository.findById(eq(item.getItemId()))).thenReturn(Optional.of(itemEntity));

        final Optional<Item> result = underTest.findById(item.getItemId());
        assertEquals(Optional.of(item), result);
    }
}
