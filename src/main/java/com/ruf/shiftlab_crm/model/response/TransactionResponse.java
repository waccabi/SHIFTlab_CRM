package com.ruf.shiftlab_crm.model.response;

import com.ruf.shiftlab_crm.entity.PaymentType;
import com.ruf.shiftlab_crm.entity.Seller;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class TransactionResponse {
    private Long id;
    private Seller seller;
    private BigDecimal amount;
    private PaymentType paymentType;
    private LocalDateTime transactionDate;
}
