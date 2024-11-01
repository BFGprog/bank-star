package sky.pro.bankstar.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sky.pro.bankstar.repository.RecommendationsRepository;
import sky.pro.bankstar.service.RecommendationsService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("recommendation")
public class RecommendationsController {

    private final RecommendationsRepository recommendationsRepository;
    private final RecommendationsService recommendationsService;

    public RecommendationsController(RecommendationsRepository recommendationsRepository,
                                     RecommendationsService recommendationsService) {
        this.recommendationsRepository = recommendationsRepository;
        this.recommendationsService = recommendationsService;
    }

    @GetMapping("transaction_amount")
    public Integer getRandomTransactionAmountForCurrentUser(UUID userID) {
        return recommendationsRepository.getRandomTransactionAmount(userID);
    }

    @GetMapping("{users_id}")
    public String getListOfRecommendationsForUser(@PathVariable("users_id") UUID users_id) {
        return "user_id: " + users_id + ", \nrecommendations: " +
                recommendationsService.getRecommendations(users_id);
    }

    // Task 2
    @GetMapping("recommendations{users_id}")
    public String getRecommendationsForUser(@PathVariable("users_id") UUID users_id) {
        return "user_id: " + users_id + ", \nrecommendations: " +
                recommendationsService.getRecommendationsNew(users_id);
    }
}
