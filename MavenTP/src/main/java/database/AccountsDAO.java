package database;

/**
 * Created by Alena on 3/11/14.
 */
public interface AccountsDAO {
    AccountsDataSet getAccount(String login);
    boolean saveAccount(AccountsDataSet dataSet);
    boolean deleteAccount(String login);
}
