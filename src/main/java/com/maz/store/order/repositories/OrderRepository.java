package com.maz.store.order.repositories;

import com.maz.store.order.domain.BaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderRepository extends JpaRepository<BaseOrder, UUID> {
}