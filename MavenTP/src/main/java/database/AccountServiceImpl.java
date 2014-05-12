package database;

import messageSystem.MessageSystem;
import messageSystem.Abonent;
import messageSystem.Address;
import org.hibernate.exception.ConstraintViolationException;

import java.sql.SQLException;

/**
 * Created by Alena on 10.03.14.
 */
public class AccountServiceImpl implements AccountService, Abonent, Runnable {
    private AccountsDAO accountsDAOImpl;
    private MessageSystem messageSystem;
    private Address address;
    public static final String OK_SESSION = "It's ok!";



    public AccountServiceImpl(DatabaseConnector databaseConnector, MessageSystem messageSystem)
    {
        this.accountsDAOImpl = new AccountsDAOImpl(databaseConnector);
        setMessageSystem(messageSystem);
    }

    @Override
    public AccountSession regist(String sessionId, String login, String password) throws SQLException
    {
        try
        {
            if(accountsDAOImpl.getAccount(login) != null)
                return AccountSession.getInvalidSession(sessionId, AccountSessionStatus.EXIST_USER);
            accountsDAOImpl.saveAccount(new AccountsDataSet(login, password));
            return auth(sessionId, login, password);
        }
        catch(ConstraintViolationException ignored)
        {
            return AccountSession.getInvalidSession(sessionId, AccountSessionStatus.DB_ERROR);
        }
    }

    @Override
    public AccountSession auth(String sessionId, String login, String password) throws SQLException
    {
        AccountsDataSet account = accountsDAOImpl.getAccount(login);
        try
        {
            if(account == null)
                return AccountSession.getInvalidSession(sessionId, AccountSessionStatus.INVALID_LOGIN);
            if(!account.getPassword().equals(password))
                return AccountSession.getInvalidSession(sessionId, AccountSessionStatus.INVALID_PASS);
        }
        catch (ConstraintViolationException e)
        {
            return AccountSession.getInvalidSession(sessionId, AccountSessionStatus.DB_ERROR);
        }
        return new AccountSession(sessionId, account.getUserId(), login, OK_SESSION, false);
    }

    @Override
    public void deleteAccount(String login) throws SQLException
    {
        try
        {
            accountsDAOImpl.deleteAccount(login);
        }
        catch (Exception e)
        {
           e.getMessage();
        }
    }

    @Override
    public Address getAddress() {
        return this.address;
    }

    @Override
    public MessageSystem getMessageSystem()
    {
        return this.messageSystem;
    }

    @Override
    public void setMessageSystem(MessageSystem messageSystem)
    {
        this.messageSystem = messageSystem;
        this.address = new Address();
        messageSystem.addService(this);
        messageSystem.getAddressService().setAccountService(this.address);
    }

    @Override
    public void run()
    {
        while (true) {
            this.getMessageSystem().execForAbonent(this);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
