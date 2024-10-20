package com.ruf.shiftlab_crm;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruf.shiftlab_crm.controller.TransactionController;
import com.ruf.shiftlab_crm.entity.Seller;
import com.ruf.shiftlab_crm.exceptionHandling.NoSuchException;
import com.ruf.shiftlab_crm.model.request.CreateTransactionRequest;
import com.ruf.shiftlab_crm.model.response.TransactionResponse;
import com.ruf.shiftlab_crm.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        mockMvc = MockMvcBuilders.standaloneSetup(new TransactionController(transactionService)).build();
    }

    @Test
    void testCreateTransaction() throws Exception {
        // Подготовка запроса на создание транзакции
        CreateTransactionRequest request = new CreateTransactionRequest();
        request.setSellerId(1L);
        request.setAmount(new BigDecimal("100.00"));

        // Подготовка мока ответа
        Seller mockSeller = new Seller();
        mockSeller.setId(1L);
        mockSeller.setName("John Doe");
        mockSeller.setContactInfo("john@example.com");
        mockSeller.setRegistrationDate(LocalDateTime.now());

        TransactionResponse mockResponse = new TransactionResponse();
        mockResponse.setId(1L);
        mockResponse.setSeller(mockSeller); // Установите объект Seller
        mockResponse.setAmount(new BigDecimal("100.00"));

        // Настройка мока сервиса
        Mockito.when(transactionService.save(Mockito.any(CreateTransactionRequest.class))).thenReturn(mockResponse);

        // Выполнение запроса и проверка результатов
        mockMvc.perform(post("/api/transactions/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.seller.id").value(1))
                .andExpect(jsonPath("$.amount").value("100.0"));
    }

    @Test
    void testGetAllTransactions() throws Exception {
        // Создание мока для продавцов
        Seller seller1 = new Seller();
        seller1.setId(1L);
        seller1.setName("John Doe");
        seller1.setContactInfo("john@example.com");
        seller1.setRegistrationDate(LocalDateTime.now());

        Seller seller2 = new Seller();
        seller2.setId(2L);
        seller2.setName("John Doe");
        seller2.setContactInfo("john@example.com");
        seller2.setRegistrationDate(LocalDateTime.now());


        // Создание мока для транзакций
        TransactionResponse mockResponse1 = new TransactionResponse()
                .setId(1L)
                .setSeller(seller1) // Установка объекта Seller
                .setAmount(new BigDecimal("100.00"));

        TransactionResponse mockResponse2 = new TransactionResponse()
                .setId(2L)
                .setSeller(seller2) // Установка объекта Seller
                .setAmount(new BigDecimal("200.00"));

        List<TransactionResponse> mockResponses = Arrays.asList(mockResponse1, mockResponse2);

        Mockito.when(transactionService.findAll()).thenReturn(mockResponses);

        mockMvc.perform(get("/api/transactions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].amount").value("100.0"))
                .andExpect(jsonPath("$[0].seller.id").value(1)) // Проверка seller.id
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].amount").value("200.0"))
                .andExpect(jsonPath("$[1].seller.id").value(2)); // Проверка seller.id
    }

    @Test
    void testGetTransactionById() throws Exception {
        // Создание мока для продавца
        Seller seller = new Seller();
        seller.setId(1L);
        seller.setName("John Doe");
        seller.setContactInfo("john@example.com");
        seller.setRegistrationDate(LocalDateTime.now());

        // Создание мока для транзакции
        TransactionResponse mockResponse = new TransactionResponse()
                .setId(1L)
                .setSeller(seller) // Установка объекта Seller
                .setAmount(new BigDecimal("100.00"));

        Mockito.when(transactionService.findById(1L)).thenReturn(mockResponse);

        mockMvc.perform(get("/api/transactions/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.amount").value("100.0"))
                .andExpect(jsonPath("$.seller.id").value(1)); // Проверка seller.id
    }

    @Test
    void testGetSellersTransactions() throws Exception {
        // Создание мока для продавца
        Seller seller = new Seller();
        seller.setId(1L);
        seller.setName("John Doe");
        seller.setContactInfo("john@example.com");
        seller.setRegistrationDate(LocalDateTime.now());

        // Создание мока для транзакции
        TransactionResponse mockResponse1 = new TransactionResponse()
                .setId(1L)
                .setSeller(seller) // Установка объекта Seller
                .setAmount(new BigDecimal("100.00"));

        List<TransactionResponse> mockResponses = Arrays.asList(mockResponse1);

        Mockito.when(transactionService.getTransactionsBySellerId(1L)).thenReturn(mockResponses);

        mockMvc.perform(get("/api/sellers/transactions/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].amount").value("100.0"))
                .andExpect(jsonPath("$[0].seller.id").value(1)); // Проверка seller.id
    }

}
