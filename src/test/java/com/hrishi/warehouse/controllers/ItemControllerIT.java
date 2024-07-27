package com.hrishi.warehouse.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hrishi.warehouse.domain.Item;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static com.hrishi.warehouse.TestData.testItem;


@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class ItemControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testThatItemIsCreated() throws Exception {
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

}