package kakeibo_app_java.kakeibo_app_java.controller;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import java.time.LocalDate;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import kakeibo_app_java.kakeibo_app_java.dto.JoinRequest;
import kakeibo_app_java.kakeibo_app_java.dto.ProfileDto;
import kakeibo_app_java.kakeibo_app_java.service.ShareService;

@WebMvcTest(ShareController.class)
public class ShareControllerUnitTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ShareService shareService;
    @Autowired
    private ObjectMapper objectMapper;
    @Test
    void testJoinShared() throws Exception{
        ProfileDto mockProfile = ProfileDto.builder()
            .name("Taro")
            .email("taro@example.com")
            .memo("テスト")
            .sharedAt(LocalDate.of(2025, 9, 17))
            .build();
        given(shareService.joinShared(100L, "abc123")).willReturn(mockProfile);
    JoinRequest request = JoinRequest.builder()
        .partnerId(100L)
        .code("abc123")
        .build();

    mockMvc.perform(post("/api/shared/join")
                    .with(csrf())
                    .with(user("testUser").roles("USER"))
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                    .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Taro"))
                .andExpect(jsonPath("$.email").value("taro@example.com"))
                .andExpect(jsonPath("$.memo").value("テスト"))
                .andExpect(jsonPath("$.sharedAt").value("2025-09-17"));
    }
}
