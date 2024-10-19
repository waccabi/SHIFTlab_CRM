package com.ruf.shiftlab_crm.mapper;

import com.ruf.shiftlab_crm.entity.Seller;
import com.ruf.shiftlab_crm.model.request.CreateSellerRequest;
import com.ruf.shiftlab_crm.model.response.SellerResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SellerMapper {

    SellerResponse toResponse(Seller seller);

    List<SellerResponse> toResponse(List<Seller> sellers);

    @Mapping(target = "registrationDate", expression = "java(java.time.LocalDateTime.now())")
    Seller toEntity(CreateSellerRequest createSellerRequest);

    void updateSeller(CreateSellerRequest createSellerRequest, @MappingTarget Seller seller);
}
