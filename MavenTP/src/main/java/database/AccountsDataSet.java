package database;

/**
 * Created by alena on 10.03.14.
 */
public class AccountsDataSet {
    private long userId;
    private String username;
    private String password;

    public AccountsDataSet(long userId,
                           String username,
                           String password)
    {
        this.userId = userId;
        this.username = username;
        this.password = password;
    }

    public AccountsDataSet(String username,
                           String password)
    {
        this.username = username;
        this.password = password;
    }

    public long getUserId()
    {
        return userId;
    }

    public String getLogin()
    {
        return username;
    }

    public  String getPassword()
    {
        return password;
    }
}
