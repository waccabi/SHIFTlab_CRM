package com.ruf.shiftlab_crm.model.request;

import com.ruf.shiftlab_crm.entity.PaymentType;
import com.ruf.shiftlab_crm.entity.Seller;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class CreateTransactionRequest {
    private Long sellerId;
    private BigDecimal amount;
    private PaymentType paymentType;
    private Seller seller;
}
