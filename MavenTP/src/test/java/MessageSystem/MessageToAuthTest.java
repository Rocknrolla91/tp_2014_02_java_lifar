package MessageSystem;

import database.AccountService;
import database.AccountServiceImpl;
import messageSystem.Address;
import messageSystem.MessageSystem;
import messageSystem.MessageToAuth;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

/**
 * Created by Alena on 5/30/14.
 */
public class MessageToAuthTest {
    Address from;
    Address to;

    @Before
    public void setUp() throws Exception
    {
        from = new Address();
        to = new Address();
    }

    @Test
    public void testExec() throws Exception
    {
        String login = "testLogin";
        String password = "testPassword";
        String sessionId = "1534";

        AccountService accountService = mock(AccountServiceImpl.class);
        MessageSystem messageSystem = mock(MessageSystem.class);
        when(accountService.getMessageSystem()).thenReturn(messageSystem);

        MessageToAuth message = new MessageToAuth(from, to,
                sessionId, login, password);
        message.exec(accountService);

        verify(accountService, atLeastOnce()).auth(sessionId, login, password);
    }
}
