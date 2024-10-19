package com.ruf.shiftlab_crm.model.request;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class CreateSellerRequest {
    private String name;
    private String contactInfo;
}
