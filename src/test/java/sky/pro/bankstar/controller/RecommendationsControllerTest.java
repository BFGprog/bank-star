package sky.pro.bankstar.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import sky.pro.bankstar.model.Recommendations;
import sky.pro.bankstar.repository.RecommendationsRepository;
import sky.pro.bankstar.service.RecommendationsService;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RecommendationsController.class)
public class RecommendationsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RecommendationsRepository recommendationsRepository;

    @MockBean
    private RecommendationsService recommendationsService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test //Этот метод проверяет, что контроллер возвращает правильное случайное значение транзакции для данного пользователя
    public void testGetRandomTransactionAmountForCurrentUser() throws Exception {
        UUID userId = UUID.fromString("00000000-0000-0000-0000-000000000001");
        when(recommendationsRepository.getRandomTransactionAmount(userId)).thenReturn(100);

        mockMvc.perform(MockMvcRequestBuilders.get("/recommendation/transaction_amount")
                        .param("userID", userId.toString()))
                .andExpect(status().isOk())
                .andExpect(content().string("100"));
    }

    @Test //Этот метод проверяет, что контроллер возвращает правильный список рекомендаций для данного пользователя
    public void testGetListOfRecommendationsForUser() throws Exception {
        UUID userId = UUID.fromString("00000000-0000-0000-0000-000000000001");
        Recommendations recommendation1 = new Recommendations("Recommendation 1", userId, "Text 1");
        Recommendations recommendation2 = new Recommendations("Recommendation 2", userId, "Text 2");
        List<Recommendations> recommendations = Arrays.asList(recommendation1, recommendation2);
        when(recommendationsService.getRecommendations(userId)).thenReturn(recommendations);

        String expectedResponse = "user_id: " + userId + ", \nrecommendations: " + recommendations;

        mockMvc.perform(MockMvcRequestBuilders.get("/recommendation/{users_id}", userId))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedResponse));
    }
}