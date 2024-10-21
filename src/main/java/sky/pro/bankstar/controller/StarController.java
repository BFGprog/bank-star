package sky.pro.bankstar.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
    public int getRandomTransactionAmount() {
        return starService.getCountOfUsers();
    }

    @GetMapping("500")
    public List<String> getUsersInvest500() {
        return starService.getUsersInvest500();
    }
    @GetMapping("saving")
    public List<String> getUsersTopSaving() {
        return starService.getUsersTopSaving();
    }
    @GetMapping("credit")
    public List<String> getUsersCredit() {
        return starService.getUsersCredit();
    }

}
