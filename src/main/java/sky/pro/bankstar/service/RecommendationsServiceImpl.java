package sky.pro.bankstar.service;

import org.springframework.stereotype.Service;
import sky.pro.bankstar.model.Recommendations;
import sky.pro.bankstar.repository.RecommendationsRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RecommendationsServiceImpl implements RecommendationsService {

    RecommendationsRepository repository;

    public RecommendationsServiceImpl(RecommendationsRepository repository) {
        this.repository = repository;
    }

    public int getRandomTransactionAmount(UUID user) {
        return repository.getRandomTransactionAmount(user);
    }

    @Override
    public String getRecommendations(UUID users_id) {
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
