import org.telegram.telegrambots.bots.*;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class PersonalAssistant {

    public static void main(String[] args) {
        BotProperties.initProperties();
        initProxyServer();
        PersonalAssistantBotHandler bot = new PersonalAssistantBotHandler();
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(bot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    /**
     * Initialize proxyServer, if need
     * @return void
     */
    public static void initProxyServer(){
        if("true".equalsIgnoreCase(BotProperties.getProperty(BotProperties.key.USE_PROXY))){
            System.setProperty("http.proxyHost",BotProperties.getProperty(BotProperties.key.PROXY_SERVER));
            System.setProperty("http.proxyPort", BotProperties.getProperty(BotProperties.key.PROXY_PORT));
        }
    }

}


class PersonalAssistantBotHandler extends TelegramLongPollingBot{


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