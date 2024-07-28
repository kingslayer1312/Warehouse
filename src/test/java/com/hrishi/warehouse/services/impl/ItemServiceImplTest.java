package com.hrishi.warehouse.services.impl;

import com.hrishi.warehouse.domain.Item;
import com.hrishi.warehouse.domain.ItemEntity;
import com.hrishi.warehouse.repositories.ItemRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.hrishi.warehouse.TestData.testItem;
import static com.hrishi.warehouse.TestData.testItemEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

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

        final Item result = underTest.save(item);
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
    public void testThatFindByIdReturnsItemWhenExists() {
        final Item item = testItem();
        final ItemEntity itemEntity = testItemEntity();

        when(itemRepository.findById(eq(item.getItemId()))).thenReturn(Optional.of(itemEntity));

        final Optional<Item> result = underTest.findById(item.getItemId());
        assertEquals(Optional.of(item), result);
    }

    @Test
    public void testListItemsReturnsEmptyListWhenNoItemsExist() {
        when(itemRepository.findAll()).thenReturn(new ArrayList<ItemEntity>());
        final List<Item> result = underTest.listItems();
        assertEquals(0, result.size());
    }

    @Test
    public void testListItemsReturnsItemsWhenExist() {
        final ItemEntity itemEntity = testItemEntity();
        when(itemRepository.findAll()).thenReturn(List.of(itemEntity));
        final List<Item> result = underTest.listItems();
        assertEquals(1, result.size());
    }

    @Test
    public void testItemExistsReturnsFalseWhenBookDoesntExist() {
        when(itemRepository.existsById(any())).thenReturn(false);
        final boolean result = underTest.doesItemExist(testItem());
        assertEquals(false, result);
    }

    @Test
    public void testItemExistsReturnsFalseWhenBookDoesExist() {
        when(itemRepository.existsById(any())).thenReturn(true);
        final boolean result = underTest.doesItemExist(testItem());
        assertEquals(true, result);
    }

    @Test
    public void testDeleteItemDeletesItem() {
        final Integer itemId = 101;
        underTest.deleteItemById(itemId);
        verify(itemRepository, times(1)).deleteById(eq(itemId));

    }
}
