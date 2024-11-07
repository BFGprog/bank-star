package sky.pro.bankstar.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import sky.pro.bankstar.model.Recommendations;
import sky.pro.bankstar.model.UserDB;
import sky.pro.bankstar.repository.RecommendationsRepository;
import sky.pro.bankstar.service.MessageSender;
import sky.pro.bankstar.service.RecommendationsService;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//Добавьте в ваш сервис бота, который работает по следующему протоколу.
@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    private final String WELCOME_MESSAGE = "Привет друг";
    private final TelegramBot telegramBot;
    private final MessageSender messageSender;
    private final RecommendationsRepository recommendationsRepository;

    private final RecommendationsService recommendationsService;

    public TelegramBotUpdatesListener(TelegramBot telegramBot, MessageSender messageSender, RecommendationsRepository recommendationsRepository, RecommendationsService recommendationsService) {
        this.telegramBot = telegramBot;
        this.messageSender = messageSender;
        this.recommendationsRepository = recommendationsRepository;
        this.recommendationsService = recommendationsService;
    }

    // Патерн для получения UserName
    private final Pattern pattern = Pattern.compile("^/recommend\\s+(.+)$");

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            logger.info("Processing update: {}", update);
            if (Objects.isNull(update.message())) {
                return;
            }
            Long chatId = update.message().chat().id();
            String messageText = update.message().text();
            Matcher matcher = pattern.matcher(messageText);

//Проверка на наличие текста

            try {
                if (Objects.isNull(update.message().text())) {
                    throw new RuntimeException("Нет текста");
                }
            } catch (RuntimeException e) {
                messageSender.send(update.message().chat().id(), "Я работаю только с текстом");
                return;
            }
//При первом обращении бот приветствует пользователя и печатает справку.
            if (messageText.equals("/start")) {
                String message = "Привет! Для получения информации по банковским продуктам введите \"/recommend <username>\"";
                messageSender.send(chatId, message);
                logger.info("Entered in the telegram");

            }
//              Единственная команда — recommend username :
            if (messageText.equals("/recommend")) {
                String message = "Введите запрос в формате \"/recommend <username>\"";
                messageSender.send(chatId, message);
                logger.info("Received recommendations");
            }

            if (matcher.matches()) {
                String item = matcher.group(1);
                System.out.println(matcher.toString());
                UserDB userDB = null;
                try {
                    userDB = recommendationsRepository.getUser(item);
                    logger.info("User has been found: {}", userDB.getFirstName());
                }
// Если пользователь не найден, бот выдает сообщение «Пользователь не найден».

                catch (Exception e) {
                    messageSender.send(chatId, "Пользователь не найден!");
                    logger.info("User not found.");

                    return;
                }
//Команда возвращает рекомендации в форме:/
//Здравствуйте <Имя и фамилия пользователя>// (данные есть в базе).
// //Новые продукты для вас:
// список продуктов с рекомендациями, удобно отформатированный.
                List<Recommendations> recommendationsList = recommendationsService.getRecommendations(userDB.getId());
                messageSender.send(chatId, "Здравствуйте, " + userDB.getFirstName() + " " + userDB.getLastName());
                messageSender.send(chatId, "Новые продукты для вас: ");

                for (Recommendations recommendations : recommendationsList) {
                    messageSender.send(chatId, "Продукт: < " + recommendations.getProduct_name() + "> " + '\n' + recommendations.getProduct_text());

                }
            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }
}