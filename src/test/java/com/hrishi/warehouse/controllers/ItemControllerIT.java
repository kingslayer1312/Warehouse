package com.hrishi.warehouse.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hrishi.warehouse.TestData;
import com.hrishi.warehouse.domain.Item;
import com.hrishi.warehouse.services.ItemService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static com.hrishi.warehouse.TestData.testItem;


@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ItemControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ItemService itemService;

    @Test
    public void testThatItemIsCreatedReturnsHttp200() throws Exception {
        final Item item = testItem();
        final ObjectMapper objectMapper = new ObjectMapper();
        final String itemJson = objectMapper.writeValueAsString(item);
        mockMvc.perform(MockMvcRequestBuilders.put("/items/" + item.getItemId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(itemJson))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.itemId").value(item.getItemId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.itemName").value(item.getItemName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(item.getDescription()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.quantity").value(item.getQuantity()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(item.getPrice()));

    }

    @Test
    public void testThatItemIsUpdatedReturnsHttp201() throws Exception {
        final Item item = testItem();
        itemService.save(item);

        item.setDescription("An OLED 65 inch TV");

        final ObjectMapper objectMapper = new ObjectMapper();
        final String itemJson = objectMapper.writeValueAsString(item);
        mockMvc.perform(MockMvcRequestBuilders.put("/items/" + item.getItemId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(itemJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.itemId").value(item.getItemId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.itemName").value(item.getItemName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(item.getDescription()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.quantity").value(item.getQuantity()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(item.getPrice()));

    }

    @Test
    public void testThatRetrieveItemReturnsHttp404WhenItemNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/items/1001"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testThatRetrieveItemReturnsHttp200AndItemWhenExists() throws Exception {
        final Item item = TestData.testItem();
        itemService.save(item);

        mockMvc.perform(MockMvcRequestBuilders.get("/items/" + item.getItemId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.itemId").value(item.getItemId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.itemName").value(item.getItemName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(item.getDescription()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.quantity").value(item.getQuantity()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(item.getPrice()));
    }

    @Test
    public void testThatListItemsReturnsHttp200EmptyListWhenNoItemsExist() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/items"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    public void testThatListItemsReturnsHttp200AndItemsWhenItemsExist() throws Exception {
        final Item item = TestData.testItem();
        itemService.save(item);

        mockMvc.perform(MockMvcRequestBuilders.get("/items"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].itemId").value(item.getItemId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].itemName").value(item.getItemName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].description").value(item.getDescription()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].quantity").value(item.getQuantity()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].price").value(item.getPrice()));

    }

    @Test
    public void testThatHttp204IsReturnedWhenItemDoesntExist() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/items/39202"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void testThatHttp204IsReturnedWhenExistingItemIsDeleted() throws Exception {
        final Item item = TestData.testItem();
        itemService.save(item);

        mockMvc.perform(MockMvcRequestBuilders.delete("/items/" + item.getItemId()))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

}