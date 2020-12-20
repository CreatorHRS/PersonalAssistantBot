package ml.kharisov.PersonalAssistant;

import org.telegram.telegrambots.meta.api.objects.Message;

interface  Dialogue
{
	Message getResponce(User user);


}
