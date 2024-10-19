package com.ruf.shiftlab_crm.service.implementation;

import com.ruf.shiftlab_crm.entity.Seller;
import com.ruf.shiftlab_crm.exceptionHandling.NoSuchException;
import com.ruf.shiftlab_crm.mapper.SellerMapper;
import com.ruf.shiftlab_crm.model.request.CreateSellerRequest;
import com.ruf.shiftlab_crm.model.response.SellerResponse;
import com.ruf.shiftlab_crm.repository.SellerRepository;
import com.ruf.shiftlab_crm.repository.TransactionRepository;
import com.ruf.shiftlab_crm.service.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SellerServiceImpl implements SellerService {

    private final SellerRepository sellerRepository;
    private final TransactionRepository transactionRepository;
    private final SellerMapper sellerMapper;

    @Override
    public List<SellerResponse> findAll() {
        Optional<List<Seller>> sellers = Optional.ofNullable(sellerRepository.findAll());
        if(sellers.isEmpty()||sellers.get().isEmpty()){
            throw new NoSuchException("Isn't sellers");
        }
        return sellerMapper.toResponse(sellers.get());
    }

    @Override
    public SellerResponse findById(Long sellerId) {
        Seller seller = sellerRepository.findById(sellerId).orElseThrow(() -> new NoSuchException("Seller id " + sellerId + " not found"));
        return sellerMapper.toResponse(seller);
    }

    @Override
    public SellerResponse save(CreateSellerRequest createSellerRequest) {
        Seller seller = sellerMapper.toEntity(createSellerRequest);
        Seller saveSeller = sellerRepository.save(seller);
        return sellerMapper.toResponse(saveSeller);
    }

    @Override
    public void deleteById(Long sellerId) {
        sellerRepository.findById(sellerId).orElseThrow(() -> new NoSuchException("Seller id " + sellerId + " not found"));
        sellerRepository.deleteById(sellerId);
    }

    @Override
    public SellerResponse update(Long sellerId, CreateSellerRequest createSellerRequest) {
        Seller bdSeller = sellerRepository.findById(sellerId).orElseThrow(() -> new RuntimeException("Seller with id " + sellerId + " not found"));
        if (Objects.nonNull(bdSeller.getName()) && !"".equalsIgnoreCase(createSellerRequest.getName())) {
            bdSeller.setName(createSellerRequest.getName());
        } else {
            throw new RuntimeException("Seller with id " + sellerId + " not found name");
        }
        if (Objects.nonNull(bdSeller.getContactInfo()) && !"".equalsIgnoreCase(createSellerRequest.getContactInfo())) {
            bdSeller.setContactInfo(createSellerRequest.getContactInfo());
        } else {
            throw new RuntimeException("Seller with id " + sellerId + " not found contactInfo");
        }
        sellerRepository.save(bdSeller);
        return sellerMapper.toResponse(bdSeller);
    }

    @Override
    public LocalDateTime getMostProductivePeriodForSeller(Long sellerId) {
        return null;
    }

    @Override
    public List<SellerResponse> getSellersWithTotalAmountLessThan(LocalDateTime start, LocalDateTime end, BigDecimal amount) {
        List<Seller> sellers = sellerRepository.findAmountTransactionsLess(start, end, amount);
        return sellerMapper.toResponse(sellers);
    }

    @Override
    public SellerResponse getBestSeller(LocalDateTime startDate, LocalDateTime endDate) {
        Optional<Seller> bestSellerForPeriod = Optional.ofNullable(sellerRepository.findBestSellerForPeriod(startDate, endDate));
        if(bestSellerForPeriod.isEmpty()){
            throw new NoSuchException("No transactions in this time "+startDate+" "+endDate);
        }
        return sellerMapper.toResponse(bestSellerForPeriod.get());
    }

}
