package sky.pro.bankstar.configuration;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.DeleteMyCommands;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TelegramBotConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(TelegramBotConfiguration.class);

    @Value("${telegram.bot.token}")
    private String token;

    @Bean
    public TelegramBot telegramBot() {
        if (token == null || token.isEmpty()) {
            throw new IllegalArgumentException("Токен бота не может быть пустым");
        }

        TelegramBot bot = new TelegramBot(token);
        try {
            bot.execute(new DeleteMyCommands());
        } catch (Exception ignored) {
        }
        return bot;
    }


}
