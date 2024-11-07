package sky.pro.bankstar.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import sky.pro.bankstar.model.Query;
import sky.pro.bankstar.model.Rule;
import sky.pro.bankstar.repository.RecommendationsRepository;
import sky.pro.bankstar.repository.RuleRecommendRepository;
import sky.pro.bankstar.rule.RecommendationsRuleSet;
import sky.pro.bankstar.model.Recommendations;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RecommendationsService {

    Logger logger = LoggerFactory.getLogger(RecommendationsService.class);
    private final List<RecommendationsRuleSet> recommendationsRuleSetList;
    private RuleRecommendRepository ruleRecommendRepository;
    private RecommendationsRepository recommendationsRepository;
    private RuleRecommendService ruleRecommendService;

    public RecommendationsService(List<RecommendationsRuleSet> recommendationsRuleSetList,
                                  RecommendationsRepository recommendationsRepository,
                                  RuleRecommendRepository ruleRecommendRepository,
                                  RuleRecommendService ruleRecommendService) {
        this.recommendationsRuleSetList = recommendationsRuleSetList;
        this.recommendationsRepository = recommendationsRepository;
        this.ruleRecommendRepository = ruleRecommendRepository;
        this.ruleRecommendService = ruleRecommendService;
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


    // Task 2
    public List<Recommendations> getRecommendationsNew(UUID users_id) {
        List<Recommendations> recommendationsList = new ArrayList<>();
        List<Rule> rulesList = new ArrayList<>();
        List<Long> recommendationsId = ruleRecommendService.listRecommendationsId();
        logger.debug("Recommend size - " + String.valueOf(recommendationsId.size()));

        for (Long i = 0L; i < recommendationsId.size(); i++) {
            boolean temp = false;
            long recommId = recommendationsId.get(Math.toIntExact(i));
            rulesList = ruleRecommendService.getRules(recommId);
            for (int j = 0; j < rulesList.size(); j++) {
                Rule rule = rulesList.get(j);
                if (rule.getQuery() == Query.USER_OF) {
                    temp = checkUserOf(users_id, rule.getArguments(), rule.isNegate());
                    if (!temp) {
                        break;
                    }
                    ruleRecommendService.addCountRule(rule.getId());
                }
                if (rule.getQuery() == Query.ACTIVE_USER_OF) {
                    temp = checkActiveUserOf(users_id, rule.getArguments(), rule.isNegate());
                    if (!temp) {
                        break;
                    }
                    ruleRecommendService.addCountRule(rule.getId());
                }
                if (rule.getQuery() == Query.TRANSACTION_SUM_COMPARE) {
                    temp = checkTransactionSumCompare(users_id, rule.getArguments(), rule.isNegate());
                    if (!temp) {
                        break;
                    }
                    ruleRecommendService.addCountRule(rule.getId());
                }
                if (rule.getQuery() == Query.TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW) {
                    temp = checkTransactionSumCompareDepositWithdraw(users_id, rule.getArguments(), rule.isNegate());
                    if (!temp) {
                        break;
                    }
                    ruleRecommendService.addCountRule(rule.getId());
                }
                logger.debug("For rule - " + String.valueOf(j + 1) + " Temp = " + String.valueOf(temp));
            }
            if (temp) {
                recommendationsList.add(ruleRecommendService.getRecommendation(recommId));
                logger.debug("add Recommend id - " + String.valueOf(recommId));
            } else {
                logger.debug("Temp = " + String.valueOf(temp) + " in Recommend id - " + String.valueOf(recommId));
            }
        }

        return recommendationsList;
    }


    private boolean checkUserOf(UUID user_id, List<String> arguments, boolean negate) {
        return recommendationsRepository.checkUserOf(user_id, arguments, negate);
    }

    private boolean checkActiveUserOf(UUID user_id, List<String> arguments, boolean negate) {
        return recommendationsRepository.checkActiveUserOf(user_id, arguments, negate);
    }

    private boolean checkTransactionSumCompare(UUID user_id, List<String> arguments, boolean negate) {
        return recommendationsRepository.checkTransactionSumCompare(user_id, arguments, negate);
    }

    private boolean checkTransactionSumCompareDepositWithdraw(UUID user_id, List<String> arguments, boolean negate) {
        return recommendationsRepository.checkTransactionSumCompareDepositWithdraw(user_id, arguments, negate);
    }

}



