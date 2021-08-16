package com.maz.store.order.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class OrderLine extends BaseEntity {

    @ManyToOne
    private Order order;
    private String upc;
    @Type(type = "org.hibernate.type.UUIDCharType")
    @Column(length = 36, columnDefinition = "varchar(36)")
    private UUID productId;
    private Integer orderQuantity = 0;
    private Integer quantityAllocated = 0;

}
