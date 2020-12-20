package ml.kharisov.PersonalAssistant;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan("ml.kharisov.PersonalAssistant")
@PropertySource("file:/etc/personal_assistant/BotSettings.properties")
public class BotSpringSettings {

}
