package com.github.goitproject.bot.button;

import com.github.goitproject.bot.service.SendMessageBotService;
import org.telegram.telegrambots.meta.api.objects.Update;

public class UnknownCommand implements Button{
    private final SendMessageBotService sendMessageBotService;

    public UnknownCommand(SendMessageBotService sendMessageBotService) {
        this.sendMessageBotService = sendMessageBotService;
    }


    @Override
    public void execute(Update update, Settings settings) {
        String chatId = update.getMessage().getChatId().toString();
        sendMessageBotService.SendMessage(chatId,"Wrong command \uD83E\uDD2A");
    }
}
