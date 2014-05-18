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

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Random;
import java.util.UUID;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

/**
 * Created by Alena on 5/17/14.
 */

///??? don't care why not work:(

public class FrontendTest {
    private static Frontend frontend;
    private static HttpServletRequest request;
    private static HttpServletResponse response;
    private static HttpSession session;

    private static PrintWriter myPrintWriter;
    private static StringWriter stringWriter;
    private static AccountService accountService;
    private static MessageSystem messageSystem;
    private static AddressService addressService;

    private static ByteArrayOutputStream myOutStream;

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

        myOutStream = new ByteArrayOutputStream();
        stringWriter = new StringWriter();
        myPrintWriter = new PrintWriter(stringWriter);

        when(response.getWriter()).thenReturn(myPrintWriter);
        when(request.getSession()).thenReturn(session);
        when(session.getId()).thenReturn(TEST_SESSION_ID);
    }

    @After
    public void tearDown() throws Exception{}

    @Test
    public void testGetIndexNotAuth() throws Exception
    {
        when(request.getRequestURI()).thenReturn(PagePath.INDEX_P);

        frontend.doGet(request, response);
        verify(response, atLeastOnce()).sendRedirect(PagePath.AUTH_P);
    }

    @Test
    public void testAuthPage() throws Exception
    {
        when(request.getRequestURI()).thenReturn(PagePath.AUTH_P);

        frontend.doGet(request, response);
        assertTrue(stringWriter.toString().contains("<input id=\"password\" type=\"text\" name=\"password\"/>"));
    }
}
