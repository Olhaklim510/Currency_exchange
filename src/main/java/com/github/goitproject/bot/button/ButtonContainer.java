package com.github.goitproject.bot.button;

import com.github.goitproject.bot.service.SendMessageBotService;
import com.google.common.collect.ImmutableMap;

import java.util.HashMap;
import java.util.Map;

import static com.github.goitproject.bot.button.enum_button.ButtonCallBack.HELP_CALLBACK;
import static com.github.goitproject.bot.button.enum_button.ButtonName.*;
import static com.github.goitproject.bot.button.enum_button.ButtonCallBack.*;

public class ButtonContainer {
    private final ImmutableMap<String, Button> buttonMap;
    private final Map<Long, Settings> userSettings;
    private final Button unknownCommand;

    public ButtonContainer(SendMessageBotService sendMessageBotService) {
        userSettings = new HashMap<>();
        CurrencyButton currencyButton = new CurrencyButton(sendMessageBotService);
        BankButton bankButton = new BankButton(sendMessageBotService);
        PrecisionButton precisionButton = new PrecisionButton(sendMessageBotService);
        TimeUpdateButton timeUpdateButton = new TimeUpdateButton(sendMessageBotService);
        buttonMap = ImmutableMap.<String, Button>builder()
                .put(START.getName(), new StartButton(sendMessageBotService))
                .put(INFO_CALLBACK.getCallback(), new GetInfoButton(sendMessageBotService))

                .put(SETTINGS_CALLBACK.getCallback(), new SettingsButton(sendMessageBotService))

                .put(CURRENCY_CALLBACK.getCallback(), currencyButton)
                .put(USD_CALLBACK.getCallback(), currencyButton)
                .put(EUR_CALLBACK.getCallback(), currencyButton)
                .put(CZK_CALLBACK.getCallback(), currencyButton)
                .put(PLN_CALLBACK.getCallback(), currencyButton)
                .put(GBP_CALLBACK.getCallback(), currencyButton)
                .put(BANK_CALLBACK.getCallback(), bankButton)
                .put(NBU_CALLBACK.getCallback(), bankButton)
                .put(MONOBANK_CALLBACK.getCallback(), bankButton)
                .put(PRIVATBANK_CALLBACK.getCallback(), bankButton)
                .put(PRECISION_CALLBACK.getCallback(), precisionButton)
                .put(PRECISION_TWO_CALLBACK.getCallback(), precisionButton)
                .put(PRECISION_THREE_CALLBACK.getCallback(), precisionButton)
                .put(PRECISION_FOUR_CALLBACK.getCallback(), precisionButton)
                .put(TIME_UPDATE_CALLBACK.getCallback(), timeUpdateButton)

                .put(TIME_UPDATE_NINE_CALLBACK.getCallback(), timeUpdateButton)
                .put(TIME_UPDATE_TEN_CALLBACK.getCallback(), timeUpdateButton)
                .put(TIME_UPDATE_ELEVEN_CALLBACK.getCallback(), timeUpdateButton)
                .put(TIME_UPDATE_TWELVE_CALLBACK.getCallback(), timeUpdateButton)
                .put(TIME_UPDATE_THIRTEEN_CALLBACK.getCallback(), timeUpdateButton)
                .put(TIME_UPDATE_FOURTEEN_CALLBACK.getCallback(), timeUpdateButton)
                .put(TIME_UPDATE_FIFTEEN_CALLBACK.getCallback(), timeUpdateButton)
                .put(TIME_UPDATE_SIXTEEN_CALLBACK.getCallback(), timeUpdateButton)
                .put(TIME_UPDATE_SEVENTEEN_CALLBACK.getCallback(), timeUpdateButton)
                .put(TIME_UPDATE_EIGHTEEN_CALLBACK.getCallback(), timeUpdateButton)

                .put(TIME_UPDATE_DISABLE_CALLBACK.getCallback(), timeUpdateButton)
                .put(BACK_CALLBACK.getCallback(), new   BackButton(sendMessageBotService))
                .put(BACK_TO_START_CALLBACK.getCallback(), new BackToStartButton(sendMessageBotService))

                .put(HELP_CALLBACK.getCallback(), new HelpButton(sendMessageBotService))
                .put(MY_SETTINGS_CALLBACK.getCallback(), new MySettings(sendMessageBotService))
                .build();
        unknownCommand = new UnknownCommand(sendMessageBotService);
    }

    public Button retrieveButton(String buttonIdentifier) {
        return buttonMap.getOrDefault(buttonIdentifier, unknownCommand);
    }

    public Settings getSettingsCurrentUser(Long chatId) {
        if (userSettings.containsKey(chatId)) {
            return userSettings.get(chatId);
        }
        userSettings.put(chatId, new Settings(chatId));
        return userSettings.get(chatId);
    }

    public Map<Long, Settings> getAlUserSettings() {
        return userSettings;
    }
}
