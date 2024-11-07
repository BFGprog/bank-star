package sky.pro.bankstar.service;

import org.springframework.stereotype.Component;

@Component
public interface MessageSender {
    void send(Long chatId,String messageText );
}
