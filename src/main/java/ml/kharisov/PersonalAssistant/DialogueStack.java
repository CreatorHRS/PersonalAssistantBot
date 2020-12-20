package ml.kharisov.PersonalAssistant;

import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.ArrayList;
import java.util.Iterator;

public class DialogueStack implements Iterable<Message>
{
	final int DEFAULT_SIZE = 16;
	int limit = 0;
	Message stack[];
	private int size = 0;

	DialogueStack()
	{
		limit = DEFAULT_SIZE;
		stack = new Message[DEFAULT_SIZE];
	}

	/**
	 * Get the size of the stack
	 * @return size
	 */
	int size()
	{
		return size;
	}

	/**
	 * Put message in the stack
	 * @param message
	 */
	void putMessage(Message message)
	{
		if(size < limit)
		{
			size++;
			stack[size - 1] = message;
		}
		else
		{
			Message tempStack[] = stack;
			limit =+ DEFAULT_SIZE;
			stack = new Message[limit];
			for(int tempStackIndex = 0; tempStackIndex < tempStack.length; tempStackIndex++)
			{
				stack[tempStackIndex] = tempStack[tempStackIndex];
			}
			putMessage(message);
		}
	}

	/**
	 * Get the dialog stack iterator
	 * @return Iterator for dialogStack
	 */
	@Override
	public Iterator<Message> iterator()
	{

		return new StackMessageIterator(stack, size);
	}

	/**
	 * iterator for dialog stack;
	 */
	private class StackMessageIterator implements Iterator<Message>{

		Message messageStack[];
		int size;
		int index = 0;
		StackMessageIterator(Message dialogueStack[], int size){
			messageStack = dialogueStack;
			this.size = size;
		}

		@Override
		public boolean hasNext()
		{
			if(index < size)
				return true;
			else
				return false;
		}

		@Override
		public Message next()
		{
			return messageStack[index++];
		}
	}
}


