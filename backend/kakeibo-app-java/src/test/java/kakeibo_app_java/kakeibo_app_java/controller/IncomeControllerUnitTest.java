package kakeibo_app_java.kakeibo_app_java.controller;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;


import kakeibo_app_java.kakeibo_app_java.service.IncomeService;

@WebMvcTest(IncomeController.class)
@AutoConfigureMockMvc(addFilters = false)
public class IncomeControllerUnitTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private IncomeService incomeService;
    @Test
    void testPublicIncome() throws Exception{
        when(incomeService.getPublicIncome(anyLong(),anyInt(), anyInt())).thenReturn(BigDecimal.valueOf(1000));
        mockMvc.perform(get("/api/incomes/public/{partnerId}",1L).param("year", "2025").param("month", "10"))
            .andExpect(status().isOk())
            .andExpect(content().string("1000"));
    }
}
