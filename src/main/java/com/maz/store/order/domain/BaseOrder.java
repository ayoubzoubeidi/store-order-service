package com.maz.store.order.domain;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class BaseOrder extends BaseEntity {

    @OneToMany(mappedBy = "baseOrder", cascade = CascadeType.ALL)
    @Fetch(FetchMode.JOIN)
    private Set<OrderLine> orderLines = new HashSet<>();
    private UUID customerId ;

    //state machine's state
    private OrderStatus status;

}
