package com.ruf.shiftlab_crm.controller;

import com.ruf.shiftlab_crm.model.request.CreateSellerRequest;
import com.ruf.shiftlab_crm.model.response.SellerResponse;
import com.ruf.shiftlab_crm.service.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/sellers")
public class SellerController {

    private final SellerService sellerService;

    @GetMapping
    public ResponseEntity<List<SellerResponse>> findAllSellers() {
        List<SellerResponse> sellerResponses = sellerService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(sellerResponses);
    }

    @GetMapping("/{sellerId}")
    public ResponseEntity<SellerResponse> findById(@PathVariable Long sellerId) {
            SellerResponse sellerResponse = sellerService.findById(sellerId);
            return ResponseEntity.ok().body(sellerResponse);
    }

    @PostMapping("/create")
    public ResponseEntity<SellerResponse> create(@RequestBody CreateSellerRequest createSellerRequest) {
        SellerResponse sellerResponse = sellerService.save(createSellerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(sellerResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SellerResponse> updateSeller(@PathVariable Long id, @RequestBody CreateSellerRequest createSellerRequest) {
            SellerResponse sellerResponse = sellerService.update(id, createSellerRequest);
            return ResponseEntity.status(HttpStatus.OK).body(sellerResponse);
    }

    @GetMapping("/best/day")
    public ResponseEntity<SellerResponse> getBestSellerForDay(@RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate date) {
        LocalDateTime startDate = date.atStartOfDay();
        LocalDateTime endDate = LocalDateTime.of(date, LocalTime.MAX);
        SellerResponse sellerResponse = sellerService.getBestSeller(startDate, endDate);
        return ResponseEntity.status(HttpStatus.OK).body(sellerResponse);
    }

    @GetMapping("/best/month")
    public ResponseEntity<SellerResponse> getBestSellerForMonth(@RequestParam @DateTimeFormat(pattern = "MM-yyyy") YearMonth date) {
        LocalDateTime startDate = date.atDay(1).atStartOfDay();
        LocalDateTime endDate = date.atEndOfMonth().atTime(LocalTime.MAX);
        SellerResponse sellerResponse = sellerService.getBestSeller(startDate, endDate);
        return ResponseEntity.status(HttpStatus.OK).body(sellerResponse);
    }

    @GetMapping("/best/year")
    public ResponseEntity<SellerResponse> getBestSellerForYear(@RequestParam @DateTimeFormat(pattern = "yyyy") Year date) {
        LocalDateTime startDate = date.atDay(1).atStartOfDay();
        LocalDateTime endDate = date.atMonth(Month.DECEMBER).atEndOfMonth().atTime(LocalTime.MAX);
        SellerResponse sellerResponse = sellerService.getBestSeller(startDate, endDate);
        return ResponseEntity.status(HttpStatus.OK).body(sellerResponse);
    }

    @GetMapping("/best/quarter")
    public ResponseEntity<SellerResponse> getBestSellerForQuarter(@RequestParam Integer quarter, @RequestParam @DateTimeFormat(pattern = "yyyy") Year date) {
        LocalDateTime startDate = null;
        LocalDateTime endDate = null;

        switch (quarter) {
            case 1:
                startDate = LocalDateTime.of(date.getValue(), Month.JANUARY, 1, 0, 0, 0);
                endDate = LocalDateTime.of(date.getValue(), Month.MARCH, 31, 23, 59, 59);
                break;
            case 2:
                startDate = LocalDateTime.of(date.getValue(), Month.APRIL, 1, 0, 0, 0);
                endDate = LocalDateTime.of(date.getValue(), Month.JUNE, 30, 23, 59, 59);
                break;
            case 3:
                startDate = LocalDateTime.of(date.getValue(), Month.JULY, 1, 0, 0, 0);
                endDate = LocalDateTime.of(date.getValue(), Month.SEPTEMBER, 30, 23, 59, 59);
                break;
            case 4:
                startDate = LocalDateTime.of(date.getValue(), Month.OCTOBER, 1, 0, 0, 0);
                endDate = LocalDateTime.of(date.getValue(), Month.DECEMBER, 31, 23, 59, 59);
                break;
            default:
                throw new IllegalArgumentException("Invalid quarter. Quarter need be 1-4 not "+ quarter);
        }
        SellerResponse sellerResponse = sellerService.getBestSeller(startDate, endDate);
        return ResponseEntity.status(HttpStatus.OK).body(sellerResponse);
    }


    @GetMapping("/losers")
    public ResponseEntity<List<SellerResponse>> getAmountTransactionsLess(@RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate start, @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate end, @RequestParam BigDecimal amount) {
        LocalDateTime startDate = start.atStartOfDay();
        LocalDateTime endDate = end.atTime(LocalTime.MAX);
        List<SellerResponse> sellerResponses = sellerService.getSellersWithTotalAmountLessThan(startDate, endDate, amount);
        return ResponseEntity.status(HttpStatus.OK).body(sellerResponses);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSeller(@PathVariable Long id) {
            sellerService.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
