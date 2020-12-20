package ml.kharisov.PersonalAssistant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Component
public class ConnectionPoolShell{
	
	HikariConfig connectionPoolConfig;
	@Autowired
	@Value("${databaseSettings.Server}")
	private String DATABASE_SERVER;
	@Autowired
	@Value("${databaseSettings.Port}")
	private String DATABASE_PORT;
	@Autowired
	@Value("${databaseSettings.Name}")
	private String DATABASE_NAME;
	private String JDBC_URL = "jdbc:mysql";
	@Autowired
	@Value("${databaseSettings.User}")
	private String DATABASE_USER;
	@Autowired
	@Value("${databaseSettings.Password}")
	private String DATABASE_PASSWORD;
	
	@Bean(name = "connectionPool")
	public HikariDataSource getConnectionPull(){
		connectionPoolConfig = new HikariConfig();
        String databaseHost = DATABASE_SERVER;
        String databasePort = DATABASE_PORT;
        String databaseName = DATABASE_NAME;
        connectionPoolConfig.setJdbcUrl(JDBC_URL + "://" + databaseHost + ":" + databasePort + "/" + databaseName);
        System.out.println("url = " + connectionPoolConfig.getJdbcUrl());
        connectionPoolConfig.setUsername(DATABASE_USER);
        connectionPoolConfig.setPassword(DATABASE_PASSWORD);
        return new HikariDataSource(connectionPoolConfig);
	}
}
