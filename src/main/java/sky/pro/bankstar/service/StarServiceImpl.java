package sky.pro.bankstar.service;

import org.springframework.stereotype.Service;
import sky.pro.bankstar.model.Recommendation;
import sky.pro.bankstar.repository.RecommendationsRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class StarServiceImpl implements StarService {

    private final RecommendationsRepository repository;

    public StarServiceImpl(RecommendationsRepository repository) {
        this.repository = repository;
    }

    private List<Recommendation> allRecommendations = new ArrayList<>(List.of(
            new Recommendation("Invest 500", "147f6a0f-3b91-413b-ab99-87f081d60d5a",
                    "Откройте свой путь к успеху с индивидуальным инвестиционным счетом (ИИС) от нашего банка! " +
                            "Воспользуйтесь налоговыми льготами и начните инвестировать с умом. " +
                            "Пополните счет до конца года и получите выгоду в виде вычета на взнос в " +
                            "следующем налоговом периоде. Не упустите возможность разнообразить свой портфель, " +
                            "снизить риски и следить за актуальными рыночными тенденциями. Откройте ИИС сегодня и " +
                            "станьте ближе к финансовой независимости!"),
            new Recommendation("Top Saving", "59efc529-2fff-41af-baff-90ccd7402925",
                    "Откройте свою собственную «Копилку» с нашим банком! «Копилка» — это уникальный банковский инструмент, который поможет вам легко и удобно накапливать деньги на важные цели. Больше никаких забытых чеков и потерянных квитанций — всё под контролем!" +

                            "Преимущества «Копилки»:" +

                            "Накопление средств на конкретные цели. Установите лимит и срок накопления, и банк будет автоматически переводить определенную сумму на ваш счет." +
                            
                            "Прозрачность и контроль. Отслеживайте свои доходы и расходы, контролируйте процесс накопления и корректируйте стратегию при необходимости." +
                            
                            "Безопасность и надежность. Ваши средства находятся под защитой банка, а доступ к ним возможен только через мобильное приложение или интернет-банкинг." +
                            
                            "Начните использовать «Копилку» уже сегодня и станьте ближе к своим финансовым целям!"),
            new Recommendation("Простой кредит", "ab138afb-f3ba-4a93-b74f-0fcee86d447f",
                    "Откройте мир выгодных кредитов с нами!" +
                            
                            "Ищете способ быстро и без лишних хлопот получить нужную сумму? Тогда наш выгодный кредит — именно то, что вам нужно! Мы предлагаем низкие процентные ставки, гибкие условия и индивидуальный подход к каждому клиенту." +

                            "Почему выбирают нас:" +
                            
                            "Быстрое рассмотрение заявки. Мы ценим ваше время, поэтому процесс рассмотрения заявки занимает всего несколько часов." +
                            
                            "Удобное оформление. Подать заявку на кредит можно онлайн на нашем сайте или в мобильном приложении." +
                            
                            "Широкий выбор кредитных продуктов. Мы предлагаем кредиты на различные цели: покупку недвижимости, автомобиля, образование, лечение и многое другое." +
                            
                            "Не упустите возможность воспользоваться выгодными условиями кредитования от нашей компании!"
                            )
    ));

    public int getRandomTransactionAmount(UUID user) {
        return repository.getRandomTransactionAmount(user);
    }

    @Override
    public int getCountOfUsers() {
        return repository.getCountOfUsers();
    }

    @Override
    public List<Recommendation> getRecommendation(UUID user) {
        String userString = user.toString();
        List<Recommendation> recommendations = new ArrayList<>();
        if (repository.getUsersInvest500().contains(userString)) {
            recommendations.add(allRecommendations.get(0));
        }
        if (repository.getTopSaving().contains(userString)) {
            recommendations.add(allRecommendations.get(1));
        }
        if (repository.getCredit().contains(userString)) {
            recommendations.add(allRecommendations.get(2));
        }
        return recommendations;
    }

    public List<String> getListOfUsersForTwoRecommendation() {
        return repository.getListOfUsersForTwoRecommendation();
    }

}
