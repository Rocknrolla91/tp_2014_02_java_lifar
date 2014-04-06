package MessageSystem;

import database.AccountService;
import exception.AccountServiceException;

import java.sql.SQLException;

/**
 * Created by Alena on 4/6/14.
 */
public abstract class MessageToAccountService extends Message{

    public MessageToAccountService(Address from, Address to)
    {
        super(from, to);
    }

    @Override
    public void exec(Abonent abonent)
    {
        if(abonent instanceof AccountService)
            try {
                exec((AccountService) abonent);
            } catch (AccountServiceException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }

    public abstract void exec(AccountService accountService) throws AccountServiceException, SQLException;
}
