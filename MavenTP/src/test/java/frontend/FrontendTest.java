package frontend;

import database.AccountService;
import database.AccountServiceImpl;
import database.AccountSession;
import database.AccountSessionStatus;
import junit.framework.Assert;
import messageSystem.AddressService;
import messageSystem.MessageSystem;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import templator.PagePath;
import util.StringGenerator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Random;
import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Alena on 5/17/14.
 */

///??? don't care why not work:(

public class FrontendTest {
    private StringWriter stringWriter = new StringWriter();
    private static final MessageSystem messageSystem = mock(MessageSystem.class);
    private static final AddressService addressService = mock(AddressService.class);

    private static final HttpSession session = mock(HttpSession.class);
    private static final HttpServletRequest request = mock(HttpServletRequest.class);
    private static final HttpServletResponse response = mock(HttpServletResponse.class);
    private static final AccountService accountService = mock(AccountServiceImpl.class);

    private static final String TEST_LOGIN = StringGenerator.getRandomString(6);
    private static final String TEST_PASSWORD = StringGenerator.getRandomString(6);
    private static final String TEST_SESSION_ID = UUID.randomUUID().toString();
    private static final long TEST_ACCOUNT_ID = new Random().nextLong();

    private Frontend frontend = new Frontend(messageSystem);

    public FrontendTest()
    {
        try
        {
            when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void authorized(boolean isAuth, String path)
    {
        when(request.getRequestURI()).thenReturn(path);
        frontend.setSession(new AccountSession(TEST_SESSION_ID, TEST_ACCOUNT_ID, TEST_LOGIN, isAuth ? "It's ok" : "error", isAuth));
    }


    @BeforeClass
    public static void setUp() throws Exception
    {
        when(messageSystem.getAddressService()).thenReturn(addressService);
        when(request.getSession()).thenReturn(session);
        when(session.getId()).thenReturn(TEST_SESSION_ID);
    }

    @After
    public void tearDown() throws Exception {}

    @Test
    public void getIndexPageNotAuth() throws Exception
    {
        authorized(false, PagePath.INDEX_P);
        //when(response.sendRedirect(PagePath.AUTH_P)).thenReturn(PagePath.AUTH_P);
        frontend.doGet(request, response);
        Assert.assertTrue(stringWriter.toString().contains("auth page"));
    }

}
