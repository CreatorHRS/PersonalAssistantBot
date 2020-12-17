import org.telegram.telegrambots.bots.*;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class PersonalAssistantBotHandler {

    private static final String PROPERTY_PATH = "/etc/personal_assistant/BotSettings.properties";


    public static void main(String[] args) {
        BotProperties properties = new BotProperties(PROPERTY_PATH);
        PersonalAssistantBot bot = new PersonalAssistantBot(properties);
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(bot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

}


class PersonalAssistantBot extends TelegramLongPollingBot{

    BotProperties properties;
    PersonalAssistantBot(BotProperties properties){
        this.properties = properties;
        /*Initialize proxyServer, if need*/
        if("true".equalsIgnoreCase(properties.getProperty(BotProperties.key.USE_PROXY))){
            System.setProperty("http.proxyHost",properties.getProperty(BotProperties.key.PROXY_SERVER));
            System.setProperty("http.proxyPort", properties.getProperty(BotProperties.key.PROXY_PORT));
        }


    }

    public void onUpdateReceived(Update update) {
        SendMessage message = new SendMessage();
        message.setChatId(update.getMessage().getChatId().toString());
        message.setText("Hello");
        System.out.println(Thread.currentThread().getName());
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public String getBotUsername() {
        return BotProperties.getProperty(BotProperties.key.BOT_NAME);
    }

    public String getBotToken() {
        return BotProperties.getProperty(BotProperties.key.BOT_TOKEN);
    }


}