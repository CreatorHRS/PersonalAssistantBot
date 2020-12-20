package ml.kharisov.PersonalAssistant;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class UserCash
{
	private ArrayList<User> cash;
	//TODO:make Autowired
	private long limit;
	private long objectsInCash = 0;
	Stack<User> stack;

	/**
	 * Init a BotCash object
	 *
	 * @since 2020.11.18
	 */
	public UserCash()
	{
		cash = new ArrayList<>();
	}
	
	public void expandLimit(long expandOn){
		limit = limit + expandOn;
	}
	
	public long getLimit(){
		return this.limit;
	}

	/**
	 * Search user in cash and return null if user in not found
	 *
	 * @param userId
	 * @return user object, if user not in cash return null
	 */
	public User getObject(String userId)
	{
		User user = null;
		for(int userIndex = 0; userIndex < cash.size(); userIndex++)
		{
			if(cash.get(userIndex).getId().equals(userId))
			{
				user = cash.get(userIndex);
				cash.remove(userIndex);
				cash.add(user);
				return user;
			}
		}

		return null;

	}

	/**
	 * Get the index of user
	 *
	 * @param object
	 * @return index of bot object in cash
	 */
	public int getUserIndex(User user)
	{
		for(int objectIndex = 0; objectIndex < cash.size(); objectIndex++)
		{
			if(cash.get(objectIndex).getId().equals(user.getId()))
			{
				return objectIndex;
			}
		}
		return -1;
	}

	/**
	 * Put the user to the cash,
	 * if user already in cash put him in the top
	 *
	 * @param object
	 */
	public void putUser(User user)
	{
		int indexOfUser = this.getUserIndex(user);
		if(indexOfUser != -1)
		{
			cash.remove(indexOfUser);
		}

		cash.add(user);
		objectsInCash++;
		if(objectsInCash > limit)
		{
			cash.remove(0);
		}
	}
}
