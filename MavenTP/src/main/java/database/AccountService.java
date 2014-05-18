package database;

import messageSystem.Abonent;

import java.sql.SQLException;

/**
 * Created by Alena on 4/6/14.
 */
public interface AccountService  extends Abonent, Runnable {
    public AccountSession regist(String sessionId, String login, String password) throws SQLException;
    public AccountSession auth(String sessionId, String login, String password) throws SQLException;
    public boolean deleteAccount(String login) throws SQLException;
}
