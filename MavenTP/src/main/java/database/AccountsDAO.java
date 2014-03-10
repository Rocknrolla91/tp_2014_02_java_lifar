package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by alena on 10.03.14.
 */
public class AccountsDAO {
    private Connection connection;
    private static final String getAccountQuery = "SELECT * FROM accounts WHERE login = ?";
    private static final String addAccountQuery = "INSERT INTO accounts(login, password) VALUES(?,?)";

    public AccountsDAO(Connection connection)
    {
        this.connection = connection;
    }

    public AccountsDataSet getAccount(String login) throws SQLException
    {
        return Executor.execQuery(connection, new ExecHandler<AccountsDataSet>()
        {
            @Override
            public AccountsDataSet handle(ResultSet result) throws SQLException
            {
                if(result.next())
                {
                    return new AccountsDataSet(result.getInt("id"),
                                                result.getString("login"),
                                                result.getString("password"));
                }
                else
                {
                    return null;
                }
            }
        }, getAccountQuery, login);
    }

    public boolean saveAccount(AccountsDataSet account) throws SQLException
    {
        return Executor.execUpdate(connection, addAccountQuery, account.getLogin(), account.getPassword());
    }
}
