package database;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by alena on 10.03.14.
 */
public class DatabaseConnection {
    public static Connection getConnection()
    {
        try {
            Driver driver = (Driver)Class.forName("com.mysql.jdbc.Driver").newInstance();
            DriverManager.registerDriver(driver);

            StringBuilder url = new StringBuilder();
            url.append("jdbc:mysql://").append("localhost:").append("3306/")
                    .append("mydb?").append("user=root&").append("password=JgcYJbgO");

            return(DriverManager.getConnection(url.toString()));
        } catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }
}
