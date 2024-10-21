package sky.pro.bankstar.service;

import org.springframework.stereotype.Service;
import sky.pro.bankstar.repository.RecommendationsRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class StarServiceImpl implements StarService {

    RecommendationsRepository repository;

    public StarServiceImpl(RecommendationsRepository repository) {
        this.repository = repository;
    }

    @Override
    public int getRandomTransactionAmount(UUID user) {
        return repository.getRandomTransactionAmount(user).orElse(0);
    }

    @Override
    public int getCountOfUsers() {
        return repository.getCountOfUsers();
    }

    @Override
    public List<String> getUsersInvest500() {
        return null;
    }

    @Override
    public List<String> getUsersTopSaving() {
        return null;
    }

    @Override
    public List<String> getUsersCredit() {
        return null;
    }
}
