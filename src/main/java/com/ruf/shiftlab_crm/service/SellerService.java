package com.ruf.shiftlab_crm.service;

import com.ruf.shiftlab_crm.model.request.CreateSellerRequest;
import com.ruf.shiftlab_crm.model.response.SellerResponse;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface SellerService {
    public List<SellerResponse> findAll();

    public SellerResponse findById(Long sellerId);

    public SellerResponse save(CreateSellerRequest createSellerRequest);

    public void deleteById(Long sellerId);

    public SellerResponse update(Long sellerId, CreateSellerRequest createSellerRequest);

    public LocalDateTime getMostProductivePeriodForSeller(Long sellerId);

    public List<SellerResponse> getSellersWithTotalAmountLessThan(LocalDateTime startDate, LocalDateTime endDate,BigDecimal amount);

    public SellerResponse getBestSeller(LocalDateTime startDate, LocalDateTime endDate);
}
