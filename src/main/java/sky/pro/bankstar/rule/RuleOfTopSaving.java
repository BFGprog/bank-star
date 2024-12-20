package sky.pro.bankstar.rule;

import org.springframework.stereotype.Component;
import sky.pro.bankstar.model.Recommendations;
import sky.pro.bankstar.repository.RecommendationsRepository;

import java.util.Optional;
import java.util.UUID;

//Рекомендации пользователю на инвестиционный продукт "TopSaving"
//Пользователь использует как минимум один продукт с типом DEBIT.
//Сумма пополнений по всем продуктам типа DEBIT больше или равна 50 000 ₽ ИЛИ Сумма пополнений по всем продуктам типа SAVING больше или равна 50 000 ₽.
//Сумма пополнений по всем продуктам типа DEBIT больше, чем сумма трат по всем продуктам типа DEBIT.


@Component
public class RuleOfTopSaving implements RecommendationsRuleSet {

    private final RecommendationsRepository recommendationsRepository;

    public RuleOfTopSaving(RecommendationsRepository recommendationsRepository) {
        this.recommendationsRepository = recommendationsRepository;
    }

    @Override
    public Optional<Recommendations> getRecommendations(UUID users_id) {
        if (recommendationsRepository.hasDebitProduct(users_id) &&
                (recommendationsRepository.getDebitAmount(users_id) >= 50_000 ||
                        recommendationsRepository.getSavingAmount(users_id) >= 50_000) &&
                recommendationsRepository.getDebitAmount(users_id) > recommendationsRepository.getDebitExpenses(users_id)
        ) {
            return Optional.of(new Recommendations("Top Saving",
                    UUID.fromString("59efc529-2fff-41af-baff-90ccd7402925"),
                    "Откройте свою собственную «Копилку» с нашим банком!\n«Копилка» — это уникальный банковский " +
                            "инструмент, который поможет вам легко и удобно накапливать деньги на важные цели. " +
                            "Больше никаких забытых чеков и потерянных квитанций — всё под контролем!" +
                            "\nПреимущества «Копилки»:\n" +
                            "Накопление средств на конкретные цели. Установите лимит и срок накопления, и банк будет " +
                            "автоматически переводить определенную сумму на ваш счет.\n" +
                            "Прозрачность и контроль. Отслеживайте свои доходы и расходы, контролируйте процесс " +
                            "накопления и корректируйте стратегию при необходимости.\n" +
                            "Безопасность и надежность. Ваши средства находятся под защитой банка, а доступ к ним " +
                            "возможен только через мобильное приложение или интернет-банкинг.\n" +
                            "Начните использовать «Копилку» уже сегодня и станьте ближе к своим финансовым целям!"));
        }
        return Optional.empty();
    }
}