package com.ruf.shiftlab_crm.model.response;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class SellerResponse {
    private Long id;
    private String name;
    private String contactInfo;
    private LocalDateTime registrationDate;
}
