package sky.pro.bankstar.rule;


import sky.pro.bankstar.model.Recommendations;

import java.util.Optional;
import java.util.UUID;

// интерфейс с одним методом, который получает id пользователя и
// возвращает объект рекомендации или null.
public interface RecommendationsRuleSet {


    Optional<Recommendations> getRecommendations(UUID users_id);
}
