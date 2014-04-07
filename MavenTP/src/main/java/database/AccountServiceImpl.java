package database;

import messageSystem.MessageSystem;
import messageSystem.Abonent;
import messageSystem.Address;
import exception.AccountServiceException;
import exception.ExceptionMessageClass;
import org.hibernate.exception.ConstraintViolationException;

import java.sql.SQLException;

/**
 * Created by Alena on 10.03.14.
 */
public class AccountServiceImpl implements AccountService, Abonent, Runnable {
    private AccountsDAOImpl accountsDAOImpl;
    private MessageSystem messageSystem;
    private Address address;
    public static final String OK_SESSION = "It's ok!";

    public AccountServiceImpl(AccountsDAOImpl dao, MessageSystem messageSystem)
    {
        this.accountsDAOImpl = dao;
        setMessageSystem(messageSystem);
    }

    @Override
    public AccountSession regist(String sessionId, String login, String password) throws AccountServiceException
    {
        try
        {
            if(accountsDAOImpl.getAccount(login) != null) throw new AccountServiceException(ExceptionMessageClass.EXIST_USER);
            accountsDAOImpl.saveAccount(new AccountsDataSet(login, password));
            return auth(sessionId, login, password);
        }
        catch(ConstraintViolationException e)
        {
            throw new AccountServiceException(e.getMessage());
        }
    }

    @Override
    public AccountSession auth(String sessionId, String login, String password) throws AccountServiceException
    {
        AccountsDataSet account = accountsDAOImpl.getAccount(login);
        try
        {
            if(account == null) throw new AccountServiceException(ExceptionMessageClass.INVALID_LOGIN);
            if(!account.getPassword().equals(password)) throw new AccountServiceException(ExceptionMessageClass.INVALID_PASS);
        }
        catch (ConstraintViolationException e)
        {
            throw new AccountServiceException(e.getMessage());
        }
        return new AccountSession(sessionId, account.getUserId(), login, OK_SESSION, false);
    }

    @Override
    public void deleteAccount(String login) throws AccountServiceException, SQLException
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
