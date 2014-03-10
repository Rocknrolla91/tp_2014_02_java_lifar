package database;

import java.sql.SQLException;
import exception.DataServiceException;
import exception.EmptyDataException;

/**
 * Created by alena on 10.03.14.
 */
public class DataService {
    private AccountsDAO accountsDAO;

    public DataService()
    {
        accountsDAO = new AccountsDAO(DatabaseConnection.getConnection());
    }

    public void regist(String login, String password) throws DataServiceException
    {
        try
        {
            if(accountsDAO.getAccount(login) != null) throw new DataServiceException("User already exist");
            accountsDAO.saveAccount(new AccountsDataSet(login, password));
        }
        catch(SQLException e)
        {
            throw new DataServiceException("Database error");
        }
    }

    public void auth(String login, String password) throws DataServiceException
    {
        try
        {
            AccountsDataSet account = accountsDAO.getAccount(login);
            if(account == null) throw new DataServiceException("User with this login is not found");
            if(!account.getPassword().equals(password)) throw new DataServiceException("Wrong password");
        }
        catch (SQLException e)
        {
            throw new DataServiceException("Database error");
        }
    }

    public void isEmptyCredentials(String login, String password) throws EmptyDataException
    {
        if(login.isEmpty() || password.isEmpty() /*|| "".equals(login) || "".equals(*/)
            throw new EmptyDataException("You must write here anything");
    }
}
