package com.maz.store.model.product;

import lombok.*;

import java.util.Date;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ProductDto {

    private UUID id;

    private String upc;

    private String label;

    private Integer quantityOnHand;

    private String category;

    private Date createDate;

}
