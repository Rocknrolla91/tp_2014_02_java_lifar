package frontend;

import database.AccountService;
import database.AccountServiceImpl;
import database.AccountSession;
import database.AccountSessionStatus;
import junit.framework.Assert;
import messageSystem.Address;
import messageSystem.AddressService;
import messageSystem.MessageSystem;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import resourceSystem.ResourceSystem;
import templator.PagePath;
import util.StringGenerator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Random;
import java.util.UUID;

import static org.mockito.Mockito.*;

/**
 * Created by Alena on 5/17/14.
 */

public class FrontendTest {
    private static Frontend frontend;
    private static ResourceSystem resourceSystem;
    private static HttpServletRequest request;
    private static HttpServletResponse response;
    private static HttpSession session;

    private static PrintWriter myPrintWriter;
    private static StringWriter stringWriter;
    private static AccountService accountService;
    private static MessageSystem messageSystem;
    private static AddressService addressService;
    private static Address address;

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
        address = mock(Address.class);
        when(messageSystem.getAddressService()).thenReturn(addressService);
        when(addressService.getAccountService()).thenReturn(address);

        frontend = new Frontend(messageSystem);
        resourceSystem = ResourceSystem.getInstance();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);

        stringWriter = new StringWriter();
        myPrintWriter = new PrintWriter(stringWriter);

        when(response.getWriter()).thenReturn(myPrintWriter);
        when(request.getSession()).thenReturn(session);
        when(session.getId()).thenReturn(TEST_SESSION_ID);
    }

    @After
    public void tearDown() throws Exception
    {
        frontend.getSessions().clear();
    }

    private void doAuthorization(boolean isAuth, AccountSession accountSession)
    {
        frontend.setSession(isAuth ? accountSession
        : AccountSession.getInvalidSession(TEST_SESSION_ID, AccountSessionStatus.TEST_ERROR));
        //frontend.getSessions().put(session.getId(), accountSession);
    }

    @Test
    public void testGetIndexPageNotAuth() throws Exception
    {
        when(request.getPathInfo()).thenReturn(PagePath.INDEX_P);

        frontend.doGet(request, response);
        verify(response, atLeastOnce()).sendRedirect(PagePath.AUTH_P);
    }

    @Test
    public void testGetIndexPageAuth() throws Exception
    {
        AccountSession accountSession = new AccountSession(TEST_SESSION_ID, TEST_ACCOUNT_ID, TEST_LOGIN, AccountSessionStatus.OK_SESSION, false);
        doAuthorization(true, accountSession);
        frontend.getSessions().put(TEST_SESSION_ID, accountSession);
        when(request.getPathInfo()).thenReturn(PagePath.INDEX_P);

        frontend.doGet(request, response);
        Assert.assertTrue(stringWriter.toString().contains("Welcome"));
    }

    @Test
    public void testGetAuthPage() throws Exception
    {
        when(request.getPathInfo()).thenReturn(PagePath.AUTH_P);

        frontend.doGet(request, response);
        Assert.assertTrue(stringWriter.toString().contains("Log in, please!"));
    }

    @Test
    public void testGetRegistPage() throws Exception
    {
        when(request.getPathInfo()).thenReturn(PagePath.REGIST_P);

        frontend.doGet(request, response);
        Assert.assertTrue(stringWriter.toString().contains("Sign up, if you want!"));
    }

    @Test
    public void testGetTimerPageNoAuth() throws Exception
    {
        when(request.getPathInfo()).thenReturn(PagePath.TIMER_P);

        frontend.doGet(request, response);
        verify(response, atLeastOnce()).sendRedirect(PagePath.AUTH_P);
    }

    @Test
    public void testGetTimerPageAuth() throws Exception
    {
        AccountSession accountSession = new AccountSession(TEST_SESSION_ID, TEST_ACCOUNT_ID, TEST_LOGIN, AccountSessionStatus.OK_SESSION, false);
        doAuthorization(true, accountSession);
        frontend.getSessions().put(TEST_SESSION_ID, accountSession);
        when(request.getPathInfo()).thenReturn(PagePath.TIMER_P);

        frontend.doGet(request, response);
        Assert.assertTrue(stringWriter.toString().contains("Timer"));
    }

   @Test
    public void testPostAuthorizationFailed() throws Exception
   {
       when(request.getParameter("login")).thenReturn(StringGenerator.getRandomString(10));
       when(request.getParameter("password")).thenReturn(StringGenerator.getRandomString(10));
       when(request.getPathInfo()).thenReturn(PagePath.AUTH_P);
       frontend.doPost(request, response);
       verify(response, atLeastOnce()).sendRedirect(PagePath.WAIT_P);
       doAuthorization(false, null);
       frontend.doGet(request, response);
       Assert.assertTrue(stringWriter.toString().contains(AccountSessionStatus.TEST_ERROR));
       Assert.assertTrue(stringWriter.toString().contains("Log in, please!"));
   }

    @Test
    public void testPostAuthorizationOk() throws Exception
    {
        when(request.getParameter("login")).thenReturn(TEST_LOGIN);
        when(request.getParameter("password")).thenReturn(TEST_PASSWORD);
        when(request.getPathInfo()).thenReturn(PagePath.AUTH_P);
        frontend.doPost(request, response);
        verify(response, atLeastOnce()).sendRedirect(PagePath.WAIT_P);
        AccountSession accountSession = new AccountSession(TEST_SESSION_ID, TEST_ACCOUNT_ID, TEST_LOGIN, AccountSessionStatus.OK_SESSION, false);
        doAuthorization(true, accountSession);
        frontend.doGet(request, response);
        Assert.assertTrue(stringWriter.toString().contains(AccountSessionStatus.OK_SESSION));
        Assert.assertTrue(stringWriter.toString().contains("Log in, please!"));
    }

    @Test
    public void testPostRegistrationFailed() throws Exception
    {
        when(request.getParameter("login")).thenReturn(TEST_LOGIN);
        when(request.getParameter("password")).thenReturn(TEST_PASSWORD);
        when(request.getPathInfo()).thenReturn(PagePath.REGIST_P);
        frontend.doPost(request, response);
        verify(response, atLeastOnce()).sendRedirect(PagePath.WAIT_P);
        doAuthorization(false, null);
        frontend.doGet(request, response);
        Assert.assertTrue(stringWriter.toString().contains(AccountSessionStatus.TEST_ERROR));
        Assert.assertTrue(stringWriter.toString().contains("Sign up, if you want!"));
    }

    @Test
    public void testPostRegistrationOk() throws Exception
    {
        when(request.getParameter("login")).thenReturn(TEST_LOGIN);
        when(request.getParameter("password")).thenReturn(TEST_PASSWORD);
        when(request.getPathInfo()).thenReturn(PagePath.REGIST_P);
        frontend.doPost(request, response);
        verify(response, atLeastOnce()).sendRedirect(PagePath.WAIT_P);
        AccountSession accountSession = new AccountSession(TEST_SESSION_ID, TEST_ACCOUNT_ID, TEST_LOGIN, AccountSessionStatus.OK_SESSION, false);
        doAuthorization(true, accountSession);
        frontend.doGet(request, response);
        Assert.assertTrue(stringWriter.toString().contains(AccountSessionStatus.OK_SESSION));
        Assert.assertTrue(stringWriter.toString().contains("Sign up, if you want!"));
    }
}
