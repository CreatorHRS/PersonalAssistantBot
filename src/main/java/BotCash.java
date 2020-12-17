import java.util.*;

public class BotCash
{
	private ArrayList<User> cash;
	private long limit;
	private long usersInCash = 0;
	Stack<User> stack;

	/**
	 * Itit a UserCash object with limit
	 *
	 * @since 2020.11.18
	 */
	public BotCash(long limit)
	{
		cash = new ArrayList<>();
		this.limit = limit;
	}

	/**
	 * Itit a UserCash object
	 *
	 * @since 2020.11.18
	 */
	public BotCash()
	{
		cash = new ArrayList<>();
		this.limit = 1024;
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
	public User getUser(String userId)
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
	 * @param user
	 * @return index of user
	 */
	public int getUserIndex(User user)
	{
		for(int userIndex = 0; userIndex < cash.size(); userIndex++)
		{
			if(cash.get(userIndex).getId().equals(user.getId()))
			{
				return userIndex;
			}
		}
		return -1;
	}

	/**
	 * Put the user to the cash,
	 * if user already in cash put him in the top
	 *
	 * @param user
	 */
	public void putUser(User user)
	{
		int indexOfUser = this.getUserIndex(user);
		if(indexOfUser != -1)
		{
			cash.remove(indexOfUser);
		}

		String userId = user.getId();
		cash.add(user);
		usersInCash++;
		if(usersInCash > limit)
		{
			cash.remove(0);
		}
	}
}
