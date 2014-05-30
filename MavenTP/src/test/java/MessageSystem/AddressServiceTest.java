package MessageSystem;

import messageSystem.Address;
import messageSystem.AddressService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Alena on 5/30/14.
 */
public class AddressServiceTest {
    Address accountServiceAd;
    Address frontendAd;

    @Before
    public void setUp()
    {
        accountServiceAd = new Address();
        frontendAd = new Address();
    }

    @Test
    public void testGetAccountService() throws Exception
    {
        AddressService addressService = new AddressService();
        Assert.assertTrue(addressService.getAccountService() == null);
    }

    @Test
    public void testSetAccountService() throws Exception
    {
        AddressService addressService = new AddressService();
        addressService.setAccountService(accountServiceAd);
        Assert.assertTrue(addressService.getAccountService() == accountServiceAd);
    }

    @Test
    public void testGetFrontend() throws Exception
    {
        AddressService addressService = new AddressService();
        Assert.assertTrue(addressService.getAccountService() == null);
    }

    @Test
    public void testSetFronted() throws Exception
    {
        AddressService addressService = new AddressService();
        addressService.setAccountService(frontendAd);
        Assert.assertTrue(addressService.getAccountService() == frontendAd);
    }
}
