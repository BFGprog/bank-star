package sky.pro.bankstar.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sky.pro.bankstar.service.RecommendationsService;

import java.util.UUID;

@RestController
@RequestMapping("star")
public class RecommendationsController {
    private RecommendationsService recommendationsService;

    public RecommendationsController(RecommendationsService recommendationsService) {
        this.recommendationsService = recommendationsService;
    }

    @GetMapping("transaction")
    public int getRandomTransactionAmount(UUID user) {
        return recommendationsService.getRandomTransactionAmount(user);
    }

    @GetMapping("{users_id}")
    public String getListOfRecommendationsForUser(@PathVariable("users_id") UUID users_id) {
        return "user_id: " + users_id + ", \nrecommendations: " +
                recommendationsService.getRecommendations(users_id);
    }
}
