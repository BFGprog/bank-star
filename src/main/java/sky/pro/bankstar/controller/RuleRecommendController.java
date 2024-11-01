package sky.pro.bankstar.controller;

import org.springdoc.core.configuration.SpringDocUIConfiguration;
import org.springframework.web.bind.annotation.*;
import sky.pro.bankstar.model.Recommendations;
import sky.pro.bankstar.model.Rule;
import sky.pro.bankstar.service.RuleRecommendService;

import java.util.List;

@RestController
@RequestMapping("dynamic-rules")
public class RuleRecommendController {

    private RuleRecommendService ruleRecommendService;

    private RuleRecommendController(RuleRecommendService ruleRecommendService) {
        this.ruleRecommendService = ruleRecommendService;
    }

    @PostMapping()
    public Recommendations addRecommendation(@RequestBody Recommendations recommendations) {
        return ruleRecommendService.addRecommendation(recommendations);
    }

    @DeleteMapping("{id}")
    public void delRecommendation(@PathVariable Long id) {
        ruleRecommendService.delRecommendation(id);
    }

    @GetMapping("{id}")
    public List<Rule> getRules(@PathVariable Long id) {
        return ruleRecommendService.getRules(id);
    }

}
