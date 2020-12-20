package ml.kharisov.PersonalAssistant;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import com.zaxxer.hikari.HikariDataSource;

/**
 * Main bot class
 * @since 2020.11.17
 */
@Component
public class PersonalAssistantBot extends TelegramLongPollingBot{
	
	public static final String START = "\\start";
	  public static final String HELP = "\\help";
	  public static final String INFO = "\\info"; 
	
	@Autowired
	@Qualifier("connectionPool")
    private HikariDataSource connectionPool;
	@Autowired
	@Qualifier("userCash")
    private UserCash userCash;
	@Autowired
	@Value("${basicBotSettings.Name}")
    private String botName;
	@Autowired
	@Value("${basicBotSettings.Token}")
    private String botToken;
	@Autowired
	@Qualifier("botResourseBundle")
	private BotResourseBundle botResourseBundle;

    /**
     * Bsic constructor
     * @since 2020.11.17
     */
    PersonalAssistantBot(){
    	System.out.println("botToken = " + botToken);
    	System.out.println("botToken = " + botName);
    }

    /**
     * Executing when the bot get a message
     * @param update
     * @since 2020.11.15
     */
    public void onUpdateReceived(Update update) {
        try(Connection databaseConnection = connectionPool.getConnection())
        {
        	String chatId = update.getMessage().getChatId().toString();
        	User user = User.findUser(chatId, databaseConnection, userCash);
            SendMessage replyMessage = new SendMessage();
            Chat chatWithUser = update.getMessage().getChat();
            replyMessage.setChatId(chatWithUser.getId().toString());
            Message incomedMessage = update.getMessage();
            String messageBody = incomedMessage.getText();
            System.out.println(messageBody);
            
            switch (messageBody) {
			case START:{
				String replyBody = botResourseBundle.gerMessage("BotMessage.Hello", "en_EN");
				replyBody = String.format(replyBody, user.getName());
				replyMessage.setText(replyBody);
				break;
			}
			case HELP:{
				System.out.println("help");
				String replyBody = botResourseBundle.gerMessage("BotMessage.Help", "en_EN");
				replyMessage.setText(replyBody);
				System.out.println("replyBody = " + replyBody);
				break;
						}
			case INFO:{
				String replyBody = botResourseBundle.gerMessage("BotMessage.Info", "en_EN");
				replyBody = String.format(replyBody, botName, "mikhailkhr", "null", "0.0.1");
				replyMessage.setText(replyBody);
				break;
			}
			
			default:{
				System.out.println("def");
				String replyBody = botResourseBundle.gerMessage("BotMessage.Help", "en_EN");
				replyMessage.setText(replyBody);
				break;
			}
			}
            /*
             * Send the reply to user
             */
            
            execute(replyMessage);
            
        }catch(SQLException exepSqlException){
            exepSqlException.printStackTrace();
        } catch(TelegramApiException telegramApiExc)
        {
            telegramApiExc.printStackTrace();
        }catch(Exception otherExc)
        {
            otherExc.printStackTrace();
        }
    }
    /**
     * Get bot name
     * @since 2020.11.15
     */
    public String getBotUsername() {
        return botName;
    }
    /**
     * Get bot token
     * @since 2020.11.15
     */
    public String getBotToken() {
        return botToken;
    }

    
}

