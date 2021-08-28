package com.maz.store.order.repositories;

import com.maz.store.order.domain.BaseOrder;
import com.maz.store.order.domain.OrderLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface OrderRepository extends JpaRepository<BaseOrder, UUID> {

    @Modifying
    @Query(value = "UPDATE OrderLine line SET line.quantityAllocated = :quantityAllocated WHERE line.id = :id")
    void updateOrderLine(@Param("id") UUID id, @Param("quantityAllocated") Integer quantityAllocated);

}