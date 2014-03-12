package database;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Alena on 10.03.14.
 */
public interface ExecHandler<T> {
    T handle(ResultSet resultSet) throws SQLException;
}
