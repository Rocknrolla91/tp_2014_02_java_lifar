package frontend;

import com.gargoylesoftware.htmlunit.Page;
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
import static org.mockito.Mockito.mockingDetails;
import static org.mockito.Mockito.when;

/**
 * Created by Alena on 5/17/14.
 */

///??? don't care why not work:(

public class FrontendTest {
    private static Frontend frontend;
    private static HttpServletRequest request;
    private static HttpServletResponse response;
    private static HttpSession session;
    private static StringWriter stringWriter;
    private static AccountService accountService;
    private static MessageSystem messageSystem;
    private static AddressService addressService;

    private static final String TEST_LOGIN = StringGenerator.getRandomString(6);
    private static final String TEST_PASSWORD = StringGenerator.getRandomString(6);
    private static final String TEST_SESSION_ID = UUID.randomUUID().toString();
    private static final long TEST_ACCOUNT_ID = new Random().nextLong();

    @BeforeClass
    public static void setUp() throws Exception
    {
        accountService = mock(AccountServiceImpl.class);
        messageSystem = mock(MessageSystem.class);
        addressService = mock(AddressService.class);
        when(messageSystem.getAddressService()).thenReturn(addressService);

        frontend = new Frontend(messageSystem);

        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);

        stringWriter = new StringWriter();

        when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));
        when(request.getSession()).thenReturn(session);
        when(session.getId()).thenReturn(TEST_SESSION_ID);
    }

    @After
    public void tearDown() throws Exception{}

    @Test
    public void testGetIndexNotAuth() throws Exception
    {
        //when(request.getPathInfo()).thenReturn(PagePath.INDEX_P);
        when(request.getRequestURI()).thenReturn(PagePath.INDEX_P);
        frontend.doGet(request, response);

        Assert.assertTrue(stringWriter.toString().contains("sign up"));
    }
}
