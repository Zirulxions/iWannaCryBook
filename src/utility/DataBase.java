package utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBase {
	private Connection conn;
    private static PropertiesReader prop = PropertiesReader.getInstance();
    
    public Connection getConnection() {
        try {
                Class.forName(prop.getValue("dbDriver"));
                conn = DriverManager.getConnection(prop.getValue("dbUrl"), prop.getValue("dbUser"), prop.getValue("dbPassword"));
                return this.conn;
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Something went wrong: " + e.getMessage());
            return null;
        }
    }
}
