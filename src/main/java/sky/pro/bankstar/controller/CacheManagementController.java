package sky.pro.bankstar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.CacheManager;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sky.pro.bankstar.model.Info;
import sky.pro.bankstar.service.InfoService;

@RestController
@RequestMapping("/management")
public class CacheManagementController {

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private InfoService infoService;

    @Autowired
    @Qualifier("recommendationsJdbcTemplate") // Указываем конкретный JdbcTemplate для RecommendationsRepository
    private JdbcTemplate recommendationsJdbcTemplate;

    @Autowired
    @Qualifier("defaultJdbcTemplate") // Указываем конкретный JdbcTemplate для RuleRecommendRepository
    private JdbcTemplate defaultJdbcTemplate;

    @PostMapping("/clear-caches")
    public void clearCaches() {

        clearCache();

        updateDatabaseForRecommendations();

        updateDatabaseForRules();
    }

    private void clearCache() {
        for (String cacheName : cacheManager.getCacheNames()) {
            cacheManager.getCache(cacheName).clear();
        }
        System.out.println("Cache cleared.");
    }

    private void updateDatabaseForRecommendations() {
        recommendationsJdbcTemplate.update("UPDATE transactions SET amount = amount + 1 WHERE amount < 100");
        System.out.println("Database updated for RecommendationsRepository.");
    }

    private void updateDatabaseForRules() {
        defaultJdbcTemplate.update("UPDATE rules SET count = count + 1 WHERE count < 100");
        System.out.println("Database updated for RuleRecommendRepository.");
    }

    @GetMapping("/info")
    public Info getInfoService() {
        return infoService.getInfo();
    }
}