package database;

import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicLong;

import exception.DataServiceException;
import exception.EmptyDataException;
import org.hibernate.exception.ConstraintViolationException;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Alena on 10.03.14.
 */
public class DataService {
    private AtomicLong userIdGenerator = new AtomicLong();
    private AccountsDAO accountsDAO;

    public DataService(AccountsDAO dao)
    {
        this.accountsDAO = dao;
    }

    public void regist(String login, String password) throws DataServiceException
    {
        try
        {
            if(accountsDAO.getAccount(login) != null) throw new DataServiceException("User already exist");
            accountsDAO.saveAccount(new AccountsDataSet(login, password));
        }
        catch(ConstraintViolationException e)
        {
            throw new DataServiceException("Something wrong with database");
        }
    }

    public void auth(HttpServletRequest request, String login, String password) throws DataServiceException
    {
        try
        {
            AccountsDataSet account = accountsDAO.getAccount(login);
            long userId = userIdGenerator.getAndIncrement();
            request.getSession().setAttribute("userId", userId);
            if(account == null) throw new DataServiceException("User with this login is not found");
            if(!account.getPassword().equals(password)) throw new DataServiceException("Wrong password");
        }
        catch (ConstraintViolationException e)
        {
            throw new DataServiceException("Something wrong with database");
        }
    }

    public void isEmptyCredentials(String login, String password) throws EmptyDataException
    {
        if(login.isEmpty() || password.isEmpty())
            throw new EmptyDataException("You must write here anything!");
    }
}
