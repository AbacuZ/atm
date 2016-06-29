import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {

	private Config config = new Config();
	InputStream inputStream = null;

	public Config getProperties() {
		try {
			Properties properties = new Properties();
			String configProperties = "config.properties";

			inputStream = getClass().getClassLoader().getResourceAsStream(configProperties);
			properties.load(inputStream);

			config.setDatabaseDriver(properties.getProperty("DATABASE_DRIVER"));
			config.setDatabaseURL(properties.getProperty("DATABASE_URL"));
			config.setDatabaseUsername(properties.getProperty("DATABASE_USERNAME"));
			config.setDatabasePassword(properties.getProperty("DATABASE_PASSWORD"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return config;
	}

	public Connection getDatabaseConnection() throws Exception {
		
		Connection connection = null;
		config = getProperties();
		try {
			Class.forName(config.getDatabaseDriver());
			connection = DriverManager.getConnection(config.getDatabaseURL(), config.getDatabaseUsername(), config.getDatabasePassword()); 
		} catch (SQLException se) {
			se.printStackTrace();
		}
		return connection;
	}
}