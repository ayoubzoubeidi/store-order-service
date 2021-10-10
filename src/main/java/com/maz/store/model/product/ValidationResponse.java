package com.maz.store.model.product;

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
public class ValidationResponse implements Serializable {
    static final long serialVersionUID = -4612085944470168822L;
    private UUID orderId;
    private boolean isValid;
}
