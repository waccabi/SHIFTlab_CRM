package com.ruf.shiftlab_crm.service;

import com.ruf.shiftlab_crm.entity.Transaction;
import com.ruf.shiftlab_crm.model.request.CreateTransactionRequest;
import com.ruf.shiftlab_crm.model.response.TransactionResponse;


import java.util.List;

public interface TransactionService {

    public List<TransactionResponse> findAll();

    public TransactionResponse findById(Long id);

    public TransactionResponse save (CreateTransactionRequest createTransactionRequest);

    public List<TransactionResponse> getTransactionsBySellerId(Long sellerId);
}
