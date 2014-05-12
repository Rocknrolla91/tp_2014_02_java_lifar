package database;

import junit.framework.Assert;
import messageSystem.AddressService;
import messageSystem.MessageSystem;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import util.StringGenerator;

import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Alena on 5/12/14.
 */
public class AccountServiceTest {
    private static MessageSystem messageSystem = mock(MessageSystem.class);
    private static AddressService addressService = mock(AddressService.class);

    private static final String TEST_LOGIN = StringGenerator.getRandomString(6);
    private static final String TEST_PASSWORD = StringGenerator.getRandomString(6);
    private static final String TEST_SESSION_ID = UUID.randomUUID().toString();

   // private static DatabaseConnector databaseConnector;/* = new DatabaseConnectorH2();*/
    private static AccountService accountService;

    private void deleteTestAccount()
    {
        try
        {
            accountService.deleteAccount(TEST_LOGIN);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    @BeforeClass
    public static void setUp() throws Exception
    {
        when(messageSystem.getAddressService()).thenReturn(addressService);
    }

    @After
    public void tearDown() throws Exception
    {
        deleteTestAccount();
    }

    @Test
    public void testRegistSuccess() throws Exception
    {
        accountService = new AccountServiceImpl(new DatabaseConnectorH2(), messageSystem);
        AccountSession accountSession = accountService.regist(TEST_SESSION_ID, TEST_LOGIN, TEST_PASSWORD);
        Assert.assertTrue(!accountSession.getError());
    }
}
