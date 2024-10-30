package sky.pro.bankstar.service;

import org.springframework.stereotype.Service;
import sky.pro.bankstar.model.Recommendations;
import sky.pro.bankstar.repository.RuleRecommendRepository;

@Service
public class RuleRecommendService {

    private RuleRecommendRepository ruleRecommendRepository;

    private RuleRecommendService(RuleRecommendRepository ruleRecommendRepository) {
        this.ruleRecommendRepository = ruleRecommendRepository;
    }

    public Recommendations addRecommendation(Recommendations recommendation) {
        return ruleRecommendRepository.addRecommendations(recommendation);
    }

    public void delRecommendation(Long id) {
        ruleRecommendRepository.delRecommendation(id);
    }

}
