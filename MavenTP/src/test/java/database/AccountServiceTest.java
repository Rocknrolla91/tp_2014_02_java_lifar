package database;

import junit.framework.Assert;
import messageSystem.AddressService;
import messageSystem.MessageSystem;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import resourceSystem.ResourceSystem;
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

    private static final String myTestString = "test";

    private static AccountService accountService;

    private boolean deleteTestAccount()
    {
        boolean deleteSuccess = true;
        try {
            accountService.deleteAccount(TEST_LOGIN);
        }
        catch (Exception ignored) {
            deleteSuccess = false;
        }
        return deleteSuccess;
    }

    @BeforeClass
    public static void setUp() throws Exception
    {
        ResourceSystem resourceSystem = ResourceSystem.getInstance();
        when(messageSystem.getAddressService()).thenReturn(addressService);
        accountService = new AccountServiceImpl(new DatabaseConnector(resourceSystem.getConfigFile("H2")), messageSystem);
    }

    @After
    public void tearDown() throws Exception
    {
        deleteTestAccount();
    }

    @Test
    public void testRegistSuccess() throws Exception
    {
        AccountSession accountSession = accountService.regist(TEST_SESSION_ID, TEST_LOGIN, TEST_PASSWORD);
        Assert.assertFalse(accountSession.getError());
        accountService.deleteAccount(TEST_LOGIN);
    }

    @Test
    public void testRegistExistAccount() throws Exception
    {
        accountService.regist(TEST_SESSION_ID, TEST_LOGIN, TEST_PASSWORD);
        Assert.assertTrue(accountService.regist(TEST_SESSION_ID, TEST_LOGIN, TEST_PASSWORD).getSessionStatus().equals(AccountSessionStatus.EXIST_USER));
        accountService.deleteAccount(TEST_LOGIN);
    }

    @Test
    public void testAuthSuccess() throws Exception
    {
        accountService.regist(TEST_SESSION_ID, TEST_LOGIN, TEST_PASSWORD);
        AccountSession accountSession = accountService.auth(TEST_SESSION_ID, TEST_LOGIN, TEST_PASSWORD);
        Assert.assertFalse(accountSession.getError());
        accountService.deleteAccount(TEST_LOGIN);
    }

    @Test
    public void testAuthInvalidLogin() throws Exception
    {
        accountService.regist(TEST_SESSION_ID, TEST_LOGIN, TEST_PASSWORD);
        AccountSession accountSession = accountService.auth(TEST_SESSION_ID, TEST_LOGIN.concat(myTestString), TEST_PASSWORD);
        Assert.assertTrue(accountSession.getSessionStatus().equals(AccountSessionStatus.INVALID_LOGIN));
        accountService.deleteAccount(TEST_LOGIN);
    }

    @Test
    public void testAuthInvalidPassword() throws Exception
    {
        accountService.regist(TEST_SESSION_ID, TEST_LOGIN, TEST_PASSWORD);
        AccountSession accountSession = accountService.auth(TEST_SESSION_ID, TEST_LOGIN, TEST_PASSWORD.concat(myTestString));
        Assert.assertTrue(accountSession.getSessionStatus().equals(AccountSessionStatus.INVALID_PASS));
        accountService.deleteAccount(TEST_LOGIN);
    }

    @Test
    public void testDeleteAccountSuccess() throws Exception
    {
        accountService.regist(TEST_SESSION_ID, TEST_LOGIN, TEST_PASSWORD);
        accountService.deleteAccount(TEST_LOGIN);
        Assert.assertTrue(accountService.auth(TEST_SESSION_ID, TEST_LOGIN, TEST_PASSWORD).getError());
    }

    @Test
    public void testDeleteAccountWrong() throws Exception
    {
        accountService.regist(TEST_SESSION_ID, TEST_LOGIN, TEST_PASSWORD);
        Assert.assertFalse(accountService.deleteAccount(TEST_LOGIN.concat(myTestString)));
        accountService.deleteAccount(TEST_LOGIN);
    }
}
