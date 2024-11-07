package sky.pro.bankstar.service.impl;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import sky.pro.bankstar.listener.TelegramBotUpdatesListener;
import sky.pro.bankstar.service.MessageSender;

@Component
public class MessageSenderImpl implements MessageSender {
    private final TelegramBot telegramBot;
    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    public MessageSenderImpl(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    @Override
    public void send(Long chatId, String messageText) {
        SendMessage sendMessage=new SendMessage(chatId,messageText);
        SendResponse sendResponse = telegramBot.execute(sendMessage);

        if (sendResponse.isOk()) {
            logger.info("Message for user{} with text{}has been successfully send", chatId, messageText);
        } else {
            logger.error("Something goes wrong with sending message for user {} with text {}", chatId, messageText);
        }

    }
}
