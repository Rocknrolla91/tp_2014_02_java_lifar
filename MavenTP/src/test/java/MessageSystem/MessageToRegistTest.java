package MessageSystem;

import database.AccountService;
import database.AccountServiceImpl;
import messageSystem.Address;
import messageSystem.MessageSystem;
import messageSystem.MessageToRegist;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

/**
 * Created by Alena on 5/30/14.
 */
public class MessageToRegistTest {
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

        MessageToRegist message = new MessageToRegist(from, to,
                sessionId, login, password);
        message.exec(accountService);

        verify(accountService, atLeastOnce()).regist(sessionId, login, password);
    }
}
