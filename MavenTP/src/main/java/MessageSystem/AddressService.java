package messageSystem;

/**
 * Created by Alena on 4/6/14.
 */
public class AddressService {
    private Address accountService;
    private Address frontend;

    public Address getAccountService()
    {
        return accountService;
    }

    public void setAccountService(Address accountService)
    {
        this.accountService = accountService;
    }

    public void setFrontend(Address frontend)
    {
        this.frontend = frontend;
    }
}
