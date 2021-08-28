package com.maz.store.order;

import com.maz.store.order.services.listeners.ValidationListener;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@SpringBootTest
@ComponentScan(basePackages = "com.maz", excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE))
class StoreBaseOrderServiceApplicationTests {

    @Test
    void contextLoads() {
    }

}
