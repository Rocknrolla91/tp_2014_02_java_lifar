package database;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


/**
 * Created by alena on 10.03.14.
 */
public class Executor {
    public static <T> T execQuery(Connection connection,
                           ExecHandler<T> handler,
                           String query,
                           Object... objects) throws SQLException
    {
        PreparedStatement stmt = connection.prepareStatement(query);
        for(int i = 0; i < objects.length; i++)
        {
            stmt.setObject(i + 1, objects[i]);
        }
        ResultSet resultSet = stmt.executeQuery();
        T value = handler.handle(resultSet);
        resultSet.close();
        stmt.close();
        return value;
    }

    public static boolean execUpdate(Connection connection,
                           String query,
                           Object... objects) throws SQLException
    {
        PreparedStatement stmt = connection.prepareStatement(query);

        for(int i = 0; i < objects.length; i++)
        {
            stmt.setObject(i + 1, objects[i]);
        }
        boolean sum = stmt.execute();
        stmt.close();
        return sum;
    }
}
