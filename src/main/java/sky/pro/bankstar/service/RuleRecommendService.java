package sky.pro.bankstar.service;

import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Service;
import sky.pro.bankstar.model.Recommendations;
import sky.pro.bankstar.model.Rule;
import sky.pro.bankstar.repository.RuleRecommendRepository;

import java.util.List;
import java.util.Map;

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

    public List<Rule> getRules(Long id) {
        return ruleRecommendRepository.getRules(id);
    }

    public List<Long> listRecommendationsId() {
        return ruleRecommendRepository.listRecommendationsId();
    }

    public Recommendations getRecommendation(Long id) {
        return ruleRecommendRepository.getRecommendation(id);
    }

    public void addCountRule(Long ruleId) {
        ruleRecommendRepository.addCountRule(ruleId);
    }

    public Map<String, String> getIdAndCountRules() {
        return ruleRecommendRepository.getIdAndCountRules();
    }

}
