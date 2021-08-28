package com.maz.store.model.cancellation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CancelOrder implements Serializable {

    static final long serialVersionUID = -4369839568758997567L;
    private UUID orderId;
}
