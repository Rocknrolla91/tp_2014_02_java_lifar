package database;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by alena on 10.03.14.
 */
public interface ExecHandler<T> {
    T handle(ResultSet resultSet) throws SQLException;
}
