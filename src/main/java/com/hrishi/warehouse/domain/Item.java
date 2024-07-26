package com.hrishi.warehouse.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Item {
    private Integer itemId;
    private String itemName;
    private String description;
    private Integer quantity;
    private Float price;
}
