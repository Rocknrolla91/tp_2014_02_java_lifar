package database;

/**
 * Created by Alena on 3/11/14.
 */
public interface interfaceDAO {
    AccountsDataSet getAccount(String login);
    boolean saveAccount(AccountsDataSet dataSet);
    void deleteAccount(String login);
}
