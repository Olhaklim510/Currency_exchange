package com.github.goitproject.bot;

import com.github.goitproject.bot.button.ButtonContainer;
import com.github.goitproject.bot.button.Settings;
import com.github.goitproject.bot.service.SendMessageBotService;
import com.github.goitproject.bot.service.timer.TimeUpdate;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.*;
import java.util.Map;
import java.util.Optional;

public class TelegramBot extends TelegramLongPollingBot {

    private final ButtonContainer buttonContainer;
    private static final String BOT_USER_NAME = "Currency_Exchange_CoIT_Bot";
    private static final String TOKEN = "5751558801:AAFa8sEoRF4LIXBNbsHkL1q_S-LaTCf-8J0";
    private static final String RELATIVE_PATH = "src/main/resources/file_with_users_settings.json";
    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public TelegramBot() {
        this.buttonContainer = new ButtonContainer(new SendMessageBotService(this));
        TimeUpdate timeUpdate = new TimeUpdate(this);
        timeUpdate.startTimer();
    }

    @Override
    public String getBotUsername() {
        return BOT_USER_NAME;
    }

    @Override
    public String getBotToken() {
        return TOKEN;
    }

    @Override
    public void onUpdateReceived(Update update) {

        String buttonIdentifier;
        Long chatId;
        Settings settings = null;
        if (update.hasMessage()) {
            buttonIdentifier = update.getMessage().getText().trim();
            chatId = update.getMessage().getChatId();
            settings = buttonContainer.getSettingsCurrentUser(chatId);
            buttonContainer.retrieveButton(buttonIdentifier).execute(update, settings);
        } else if (update.hasCallbackQuery()) {
            chatId = update.getCallbackQuery().getMessage().getChatId();
            buttonIdentifier = update.getCallbackQuery().getData();
            settings = buttonContainer.getSettingsCurrentUser(chatId);
            buttonContainer.retrieveButton(buttonIdentifier).execute(update, settings);
        }
        if (Optional.of(update)
                .map(Update::getCallbackQuery)
                .map(CallbackQuery::getData)
                .filter("/back"::equals)
                .isPresent()) {


            String settingsSave = gson.toJson(settings);
            String chatID = String.valueOf(update.getCallbackQuery().getMessage().getChatId());
            File fileWrite = new File(RELATIVE_PATH);

            try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileWrite))) {
                bufferedWriter.write(settingsSave);
            } catch (IOException exc) {
                System.err.print(exc.getMessage());
            }
        }
    }

    private static void checkIfFileAvailable(File file) {
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            try {
                file.createNewFile();
            } catch (IOException exc) {
                System.err.print(exc.getMessage());
            }
        }
    }

    public Map<Long, Settings> getSettings() {
        return buttonContainer.getAlUserSettings();
    }
}
