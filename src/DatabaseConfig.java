import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConfig {

    private static Properties loadPropertiesFile() {
        Properties properties = new Properties();
        try (FileInputStream input = new FileInputStream("config/db.properties")) {
            properties.load(input);
        } catch (IOException e) {
            System.out.println("Errore durante il caricamento del file di configurazione: " + e.getMessage());
        }
        return properties;
    }

    public static Connection getConnection() {
        Properties properties = loadPropertiesFile();
        String url = properties.getProperty("db.url");
        String user = properties.getProperty("db.user");
        String password = properties.getProperty("db.password");

        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, password);


        } catch (SQLException e) {
            System.out.println("Errore di connessione: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("Driver JDBC non trovato: " + e.getMessage());
        }
        return connection;
    }
}
