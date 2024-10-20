package com.ruf.shiftlab_crm.service.implementation;

import com.ruf.shiftlab_crm.entity.Transaction;
import com.ruf.shiftlab_crm.exceptionHandling.NoSuchException;
import com.ruf.shiftlab_crm.mapper.impl.TransactionMapper;
import com.ruf.shiftlab_crm.model.request.CreateTransactionRequest;
import com.ruf.shiftlab_crm.model.response.TransactionResponse;
import com.ruf.shiftlab_crm.repository.SellerRepository;
import com.ruf.shiftlab_crm.repository.TransactionRepository;
import com.ruf.shiftlab_crm.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;
    private final SellerRepository sellerRepository;

    @Override
    public List<TransactionResponse> findAll() {
        Optional< List<Transaction>> transactions =Optional.ofNullable(transactionRepository.findAll());

        if(transactions.isEmpty()||transactions.get().isEmpty()){
            throw new NoSuchException("isn't transaction");
        }
        return transactionMapper.toResponse(transactions.get());
    }

    @Override
    public TransactionResponse findById(Long id) {
        Transaction transaction = transactionRepository.findById(id).orElseThrow(()-> new NoSuchException("isn't transaction"+ id));
        return transactionMapper.toResponse(transaction);
    }

    @Override
    public TransactionResponse save(CreateTransactionRequest createTransactionRequest) {

       if(sellerRepository.findById(createTransactionRequest.getSellerId()).isEmpty()){
           throw new NoSuchException("isn't seller "+ createTransactionRequest.getSellerId());
       }
       createTransactionRequest.setSeller(sellerRepository.findById(createTransactionRequest.getSellerId()).get());
        Transaction transaction = transactionMapper.toEntity(createTransactionRequest);
        Transaction savedTransaction = transactionRepository.save(transaction);
        return transactionMapper.toResponse(savedTransaction);
    }

    @Override
    public List<TransactionResponse> getTransactionsBySellerId(Long id) {
        if (sellerRepository.findById(id).isEmpty()) {
            throw new NoSuchException("isn't seller "+ id);
        }
        Optional< List<Transaction>> transactionsList =Optional.ofNullable( transactionRepository.findTransactionsBySellerId(id));
        if(transactionsList.isEmpty()||transactionsList.get().isEmpty()){
            throw new NoSuchException("isn't transaction for this seller "+ id);
        }
        return transactionMapper.toResponse(transactionsList.get());
    }
}
