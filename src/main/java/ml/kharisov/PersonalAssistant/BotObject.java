package ml.kharisov.PersonalAssistant;

import java.sql.Connection;

interface BotObject<Type>
{
	boolean delete(Type object);
	String getId();
	String getName();

}
