package com.ruf.shiftlab_crm.repository;

import com.ruf.shiftlab_crm.entity.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SellerRepository extends JpaRepository<Seller, Long> {

    @Query(value = "SELECT s.* FROM transaction t " +
            "JOIN seller s ON t.id_seller = s.id " +
            "WHERE t.transaction_date BETWEEN :startDate AND :endDate " +
            "GROUP BY s.id " +
            "ORDER BY SUM(t.amount) DESC " +
            "LIMIT 1", nativeQuery = true)
    Seller findBestSellerForPeriod(@Param("startDate") LocalDateTime startDate,
                                   @Param("endDate") LocalDateTime endDate);

    @Query(value = "SELECT s.* FROM transaction t " +
            "JOIN seller s ON t.id_seller = s.id " +
            "WHERE t.transaction_date BETWEEN :startDate AND :endDate " +
            "GROUP BY s.id " +
            "HAVING SUM(t.amount) < :amount " +
            "ORDER BY SUM(t.amount) DESC", nativeQuery = true)
    List<Seller> findAmountTransactionsLess(@Param("startDate") LocalDateTime startDate,@Param("endDate") LocalDateTime endDate,@Param("amount") BigDecimal amount);


}
