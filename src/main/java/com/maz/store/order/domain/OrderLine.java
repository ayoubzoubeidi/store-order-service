package com.maz.store.order.domain;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class OrderLine extends BaseEntity {

    @ManyToOne
    private BaseOrder baseOrder;

    private String upc;
    @Type(type = "org.hibernate.type.UUIDCharType")
    @Column(length = 36, columnDefinition = "varchar(36)")
    private UUID productId;
    private Integer orderQuantity = 0;
    private Integer quantityAllocated = 0;
    private BigDecimal price;

}
