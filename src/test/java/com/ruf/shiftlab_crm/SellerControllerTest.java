package com.ruf.shiftlab_crm;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruf.shiftlab_crm.entity.Seller;
import com.ruf.shiftlab_crm.exceptionHandling.NoSuchException;
import com.ruf.shiftlab_crm.model.request.CreateSellerRequest;
import com.ruf.shiftlab_crm.model.response.SellerResponse;
import com.ruf.shiftlab_crm.service.SellerService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class SellerControllerTestTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SellerService sellerService;

    private final ObjectMapper objectMapper = new ObjectMapper();

@Test
void testFindAllSellers() throws Exception {
    List<SellerResponse> mockSellers = new ArrayList<>();
    mockSellers.add(new SellerResponse().setId(1L).setName("Jane Smith").setContactInfo("jane@example.com"));
    mockSellers.add(new SellerResponse().setId(2L).setName("John Doe").setContactInfo("john@example.com"));

    Mockito.when(sellerService.findAll()).thenReturn(mockSellers);

    mockMvc.perform(get("/api/sellers"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.size()").value(2))
            .andExpect(jsonPath("$[0].name").value("Jane Smith"))
            .andExpect(jsonPath("$[1].name").value("John Doe"));
}

    @Test
    void testFindAllSellers_NoSellers() throws Exception {
        Mockito.when(sellerService.findAll()).thenThrow(new NoSuchException("Isn't sellers"));

        mockMvc.perform(get("/api/sellers"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.info").value("Isn't sellers")); // Предполагается, что исключение будет возвращать это сообщение
    }

    @Test
    void testFindById() throws Exception {
        SellerResponse mockResponse = new SellerResponse().setId(1L).setName("Jane Smith").setContactInfo("jane@example.com");

        Mockito.when(sellerService.findById(1L)).thenReturn(mockResponse);

        mockMvc.perform(get("/api/sellers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Jane Smith"))
                .andExpect(jsonPath("$.contactInfo").value("jane@example.com"));
    }

    @Test
    void testFindById_NotFound() throws Exception {
        Mockito.when(sellerService.findById(1L)).thenThrow(new NoSuchException("Seller id 1 not found"));

        mockMvc.perform(get("/api/sellers/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.info").value("Seller id 1 not found")); // Проверка на сообщение об ошибке
    }

    @Test
    void testCreateSeller() throws Exception {
        CreateSellerRequest request = new CreateSellerRequest().setName("John Doe").setContactInfo("john@example.com");
        SellerResponse mockResponse = new SellerResponse().setId(153L).setName("John Doe").setContactInfo("john@example.com");

        Mockito.when(sellerService.save(Mockito.any(CreateSellerRequest.class))).thenReturn(mockResponse);

        mockMvc.perform(post("/api/sellers/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))) // Используем ObjectMapper для преобразования объекта в JSON
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.contactInfo").value("john@example.com"));
    }

    @Test
    void testUpdateSeller() throws Exception {
        CreateSellerRequest updateRequest = new CreateSellerRequest().setName("Updated Name").setContactInfo("updated@example.com");
        SellerResponse mockResponse = new SellerResponse().setId(1L).setName("Updated Name").setContactInfo("updated@example.com");

        Mockito.when(sellerService.update(1L, updateRequest)).thenReturn(mockResponse);

        mockMvc.perform(put("/api/sellers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Name"))
                .andExpect(jsonPath("$.contactInfo").value("updated@example.com"));
    }

    @Test
    void testUpdateSeller_NotFound() throws Exception {
        CreateSellerRequest updateRequest = new CreateSellerRequest().setName("Updated Name").setContactInfo("updated@example.com");

        Mockito.when(sellerService.update(1L, updateRequest)).thenThrow(new NoSuchException("Seller with id 1 not found"));

        mockMvc.perform(put("/api/sellers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.info").value("Seller with id 1 not found"));
    }

    @Test
    void testDeleteSeller() throws Exception {
        Mockito.doNothing().when(sellerService).deleteById(1L);

        mockMvc.perform(delete("/api/sellers/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteSeller_NotFound() throws Exception {
        Mockito.doThrow(new NoSuchException("Seller id 1 not found")).when(sellerService).deleteById(1L);

        mockMvc.perform(delete("/api/sellers/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.info").value("Seller id 1 not found"));
    }

        @Test
    void testGetBestSellerForDay() throws Exception {
        SellerResponse mockResponse = new SellerResponse().setId(153L).setName("John Doe").setContactInfo("john@example.com");

        Mockito.when(sellerService.getBestSeller(Mockito.any(LocalDateTime.class), Mockito.any(LocalDateTime.class)))
                .thenReturn(mockResponse);

        mockMvc.perform(get("/api/sellers/best/day?date=15-10-2023"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.contactInfo").value("john@example.com"));
    }

    @Test
    void testGetSellersWithAmountLess() throws Exception {
        List<SellerResponse> mockResponse = List.of(
                new SellerResponse().setId(153L).setName("John Doe").setContactInfo("john@example.com"),
                new SellerResponse().setId(198L).setName("Jane Smith").setContactInfo("jane@example.com")
        );

        Mockito.when(sellerService.getSellersWithTotalAmountLessThan(Mockito.any(LocalDateTime.class), Mockito.any(LocalDateTime.class), Mockito.any(BigDecimal.class)))
                .thenReturn(mockResponse);

        mockMvc.perform(get("/api/sellers/losers?start=01-01-2023&end=15-10-2023&amount=500"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].name").value("John Doe"))
                .andExpect(jsonPath("$[1].name").value("Jane Smith"));
    }

}

