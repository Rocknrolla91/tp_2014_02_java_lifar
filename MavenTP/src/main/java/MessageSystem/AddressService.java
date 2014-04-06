package MessageSystem;

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

    public Address getFrontend()
    {
        return frontend;
    }

    public void setFrontend()
    {
        this.frontend = frontend;
    }
}
