package com.ruf.shiftlab_crm.mapper;

import com.ruf.shiftlab_crm.entity.Transaction;
import com.ruf.shiftlab_crm.model.request.CreateTransactionRequest;
import com.ruf.shiftlab_crm.model.response.TransactionResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    @Mapping(target = "transactionDate", expression = "java(java.time.LocalDateTime.now())")
    Transaction toEntity(CreateTransactionRequest createTransactionRequest);

    @Mapping(source = "seller", target = "seller")
    TransactionResponse toResponse(Transaction transaction);

    List<TransactionResponse> toResponse(List<Transaction> transactions);

    void updateTransaction(CreateTransactionRequest createTransactionRequest, @MappingTarget Transaction transaction);

}
