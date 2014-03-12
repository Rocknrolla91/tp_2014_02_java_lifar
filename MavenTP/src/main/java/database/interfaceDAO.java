package database;

import exception.DataServiceException;
import exception.EmptyDataException;

/**
 * Created by Alena on 3/11/14.
 */
public interface interfaceDAO {
    AccountsDataSet getAccount(String login);
    boolean saveAccount(AccountsDataSet dataSet);
}
