package sky.pro.bankstar.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sky.pro.bankstar.model.Recommendation;
import sky.pro.bankstar.service.StarService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("star")
public class StarController {
    private StarService starService;

    public StarController(StarService starService) {
        this.starService = starService;
    }

    @GetMapping("transaction")
    public int getRandomTransactionAmount(UUID user) {
        return starService.getRandomTransactionAmount(user);
    }

    @GetMapping
    public int getCountOfUsers() {
        return starService.getCountOfUsers();
    }

    @GetMapping("recommendation")
    public List<Recommendation> getRecommendation(UUID user) {

        return starService.getRecommendation(user);
    }

    @GetMapping("list")
    public List<String> getListOfUsersForTwoRecommendation() {
        return starService.getListOfUsersForTwoRecommendation();
    }


}
