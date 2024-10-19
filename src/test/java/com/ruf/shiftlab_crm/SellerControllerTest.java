package com.ruf.shiftlab_crm;

import com.ruf.shiftlab_crm.entity.Seller;
import com.ruf.shiftlab_crm.model.response.SellerResponse;
import com.ruf.shiftlab_crm.service.SellerService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SellerControllerTestTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SellerService sellerService;

    @Test
    void testFindAllSellers() throws Exception {
        // Выполнение HTTP-запроса и проверка
        mockMvc.perform(get("/api/sellers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2)) // Замените на ожидаемое значение
                .andExpect(jsonPath("$[0].name").value("John Doe"))
                .andExpect(jsonPath("$[1].name").value("Jane Smith"));
    }
}