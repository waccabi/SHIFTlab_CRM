package com.ruf.shiftlab_crm.repository;

import com.ruf.shiftlab_crm.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    public List<Transaction> findTransactionsBySellerId(Long sellerId);
}
