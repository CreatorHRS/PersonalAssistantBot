package ml.kharisov.PersonalAssistant;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.springframework.stereotype.Component;

@Component
public class BotResourseBundle {
	
	//TODO: make it relative
	final String PROPERTY_PATH_RU = "/home/mikhailkhr/MyProject/Java projects/PersonalAssistant/src/main/resources/Messages_en_EN.property";
	Properties ruResourses;
	Properties enResourses;
	public BotResourseBundle() 
	{
		enResourses = new Properties();
		try {
			enResourses.load(new FileInputStream(PROPERTY_PATH_RU));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public String gerMessage(String key, String locale) {
		String tempString = "";
		if("en_EN".equals(locale)) {
			tempString = enResourses.getProperty(key);
		}
		return tempString;
	}
	
}
