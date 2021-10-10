package com.maz.store.model.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDto {

    private UUID id;

    private String upc;

    private String label;

    private Integer quantityOnHand;

    private String category;

    private Date createDate;

}
