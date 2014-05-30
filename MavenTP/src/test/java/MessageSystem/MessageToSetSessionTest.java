package MessageSystem;

import database.AccountSession;
import frontend.Frontend;
import messageSystem.Address;
import messageSystem.MessageToSetSession;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by Alena on 5/30/14.
 */
public class MessageToSetSessionTest {
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
        String sessionId = "1234";
        AccountSession accountSession = new AccountSession(sessionId);
        Frontend frontend = mock(Frontend.class);

        MessageToSetSession message = new MessageToSetSession(from, to, accountSession);
        message.exec(frontend);

        verify(frontend, atLeastOnce()).setSession(accountSession);
    }
}
