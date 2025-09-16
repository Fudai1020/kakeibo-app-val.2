package kakeibo_app_java.kakeibo_app_java.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
public class ShareControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void generateShareCode_shouldReturnCode() throws Exception{
        mockMvc.perform(post("/api/shared/generate/1"))
            .andExpect(status().isOk())
            .andExpect(content().string(Matchers.hasLength(6)));
    }
}
