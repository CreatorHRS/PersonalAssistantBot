package ml.kharisov.PersonalAssistant;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * User
 */
public class User
{

	private String name = null;
	private String user_id;
	DialogueStack lastDialogueStack;
	private static final String GET_USER_SQL_QUERY = "SELECT * FROM users WHERE id = %s";
	private static final String CREATE_USER_SQL_QUERY = "INSERT INTO users(%s) VALUES (%s);";
	private static final String COLUMN_NAME_USER_ID = "id";
	private static final String COLUMN_NAME_USER_NAME = "username";

	//user attr

	private User(String user_id, String name, DialogueStack lastDialogueStack)
	{
		this.lastDialogueStack = lastDialogueStack;
		this.user_id = user_id;
		this.name = name;
	}

	/**
	 * Tries find a user in the cash and then in the database, if the user was not found return null
	 *
	 * @param userId user id
	 * @param databaseConnection
	 * @param cash
	 * @return found user
	 */
	public static User findUser(String userId, Connection databaseConnection, UserCash cash)
	{
		/*
			first try to find user in cash
		 */
		User personalBotUser = cash.getObject(userId);
		/*
			if user was not found in the cash, then it tries to find user in the database
		 */
		if(personalBotUser == null)
		{
			try
			{
				/*
					send query in the database
				 */
				ResultSet queryResult = databaseConnection.createStatement().executeQuery(String.format(GET_USER_SQL_QUERY, userId));
				/*
					if the user was found, return him, else return null
				 */
				queryResult.next();
				if(queryResult.getRow() > 0)
				{
					String username = queryResult.getString(COLUMN_NAME_USER_NAME);
					{
						return new User(userId, username, new DialogueStack());
					}
				} else
				{
					return null;
				}
			} catch(SQLException sqlException)
			{
				sqlException.printStackTrace();
			}
		}
		return personalBotUser;
	}

	/**
	 * Create user in the database
	 *
	 * @param databaseConnection Database connection to put user entry
	 * @param user_id            User id form chat with user
	 * @param username           Name of new creating user
	 * @return Created user
	 * @throws DuplicateEntryUserException
	 */
	public static User create(Connection databaseConnection, String user_id, String username) throws DuplicateEntryUserException
	{
		try
		{
			/*
				put the user int the database
			 */
			String userColumns = COLUMN_NAME_USER_ID;
			String userValues = user_id;
			boolean idUserCreated = databaseConnection.createStatement().execute(String.format(CREATE_USER_SQL_QUERY, userColumns, userValues));
		} catch(SQLException sqlException)
		{
			if(sqlException.getErrorCode() == 1024)
			{
				throw new DuplicateEntryUserException();
			} else
			{
				sqlException.printStackTrace();
			}
		}
		return new User(user_id, username, null);
	}

//	DialogueStack getDialogueStack(){
//
//	}



	/**
	 * Return a user id
	 *
	 * @return user id
	 */
	public String getId()
	{
		return user_id;
	}

	/**
	 * Return a user name
	 *
	 * @return user name
	 */
	public String getName()
	{
		return name;
	}

	public boolean delete(User user)
	{
		return false;
	}
}


class DuplicateEntryUserException extends RuntimeException
{
	DuplicateEntryUserException()
	{
	}
}

