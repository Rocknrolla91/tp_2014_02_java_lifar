package database;

import MessageSystem.Abonent;
import exception.AccountServiceException;

import java.sql.SQLException;

/**
 * Created by Alena on 4/6/14.
 */
public interface AccountService  extends Abonent {
    public AccountSession regist(String sessionId, String login, String password) throws AccountServiceException, SQLException;
    public AccountSession auth(String sessionId, String login, String password) throws AccountServiceException, SQLException;
    public void deleteAccount(String login) throws AccountServiceException, SQLException;
}
