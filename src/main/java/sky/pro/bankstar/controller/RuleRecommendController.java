package sky.pro.bankstar.controller;

import org.springframework.web.bind.annotation.*;
import sky.pro.bankstar.model.Recommendations;
import sky.pro.bankstar.service.RuleRecommendService;

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


}
