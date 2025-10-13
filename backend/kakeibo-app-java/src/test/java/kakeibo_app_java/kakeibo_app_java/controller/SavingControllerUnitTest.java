package kakeibo_app_java.kakeibo_app_java.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import kakeibo_app_java.kakeibo_app_java.dto.SavingSummaryDto;
import kakeibo_app_java.kakeibo_app_java.service.SavingService;

@WebMvcTest(SavingController.class)
@AutoConfigureMockMvc(addFilters = false)
public class SavingControllerUnitTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private SavingService savingService;
    @Test
    void testPublicSummaries() throws Exception{
        List<SavingSummaryDto> mockList = List.of(
            new SavingSummaryDto(1L,"同棲資金",BigDecimal.valueOf(50000)),
            new SavingSummaryDto(2L, "旅行", BigDecimal.valueOf(30000))
        );
        when(savingService.getPublicSummaries(anyLong(), any(LocalDate.class))).thenReturn(mockList);
        mockMvc.perform(get("/api/savings/public/{partnerId}",1L).param("year", "2025").param("month", "10")
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].name").value("同棲資金"))
            .andExpect(jsonPath("$[0].totalAmount").value(50000))
            .andExpect(jsonPath("$[1].name").value("旅行"))
            .andExpect(jsonPath("$[1].totalAmount").value(30000));
        verify(savingService,times(1))
            .getPublicSummaries(anyLong(), any(LocalDate.class));
    }
}
