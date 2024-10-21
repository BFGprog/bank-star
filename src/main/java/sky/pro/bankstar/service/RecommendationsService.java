package sky.pro.bankstar.service;

import org.springframework.stereotype.Service;
import sky.pro.bankstar.model.Recommendations;
import sky.pro.bankstar.rule.RecommendationsRuleSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RecommendationsService {

    private final List<RecommendationsRuleSet> recommendationsRuleSetList;

    public RecommendationsService(List<RecommendationsRuleSet> recommendationsRuleSetList) {
        this.recommendationsRuleSetList = recommendationsRuleSetList;
    }


    public List<Recommendations> getRecommendations(UUID users_id) {
        List<Recommendations> recommendationsList = new ArrayList<>();
        for (RecommendationsRuleSet recommendationsRuleSet : recommendationsRuleSetList) {
            Optional<Recommendations> recommendations = recommendationsRuleSet.getRecommendations(users_id);
            if (recommendations.isPresent()) {
                recommendationsList.add(recommendations.get());
            }
        }
        return recommendationsList;
    }

}

