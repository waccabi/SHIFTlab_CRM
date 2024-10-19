package com.ruf.shiftlab_crm.controller;

import com.ruf.shiftlab_crm.model.request.CreateTransactionRequest;
import com.ruf.shiftlab_crm.model.response.TransactionResponse;
import com.ruf.shiftlab_crm.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/")
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/transactions/create")
    public ResponseEntity<TransactionResponse> create(@RequestBody CreateTransactionRequest createTransactionRequest) {
        TransactionResponse createTransaction = transactionService.save(createTransactionRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createTransaction);
    }

    @GetMapping("/transactions")
    public ResponseEntity<List<TransactionResponse>> getAllTransactions() {
       List<TransactionResponse> transactions = transactionService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(transactions);
    }

    @GetMapping("/transactions/{id}")
    public ResponseEntity<TransactionResponse> getTransactionById(@PathVariable Long id) {
        TransactionResponse transactionResponse = transactionService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(transactionResponse);
    }

    @GetMapping("sellers/transactions/{id}")
    public ResponseEntity<List<TransactionResponse>> sellersTransactions(@PathVariable Long id) {
        List<TransactionResponse> sellersTransactions = transactionService.getTransactionsBySellerId(id);
        return ResponseEntity.status(HttpStatus.OK).body(sellersTransactions);
    }



}
