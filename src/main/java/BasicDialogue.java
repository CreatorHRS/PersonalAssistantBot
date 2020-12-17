import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Iterator;

public class BasicDialogue implements Dialogue
{

	@Override
	public Message getResponce(User user)
	{
		DialogueStack ds = user.getDialogueStack();
		Message mesToReturn = new Message();

		Iterator<Message> mesIter = ds.iterator();
		Message tempMes = null;
		if(mesIter.hasNext()){
			tempMes = mesIter.next();
			if(tempMes.getText().equalsIgnoreCase("/hello")){
				mesToReturn.getText();
			}else if(tempMes.getText().equalsIgnoreCase("/options")){

			}else{

			}
		}else {
			mesToReturn = null;
		}
		return mesToReturn;
	}


}
