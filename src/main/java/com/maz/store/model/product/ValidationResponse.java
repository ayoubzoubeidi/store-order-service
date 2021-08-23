package com.maz.store.model.product;

import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Data
@Builder
public class ValidationResponse implements Serializable {
    static final long serialVersionUID = -4612085944470168822L;
    private UUID orderId;
    private boolean isValid;
}
