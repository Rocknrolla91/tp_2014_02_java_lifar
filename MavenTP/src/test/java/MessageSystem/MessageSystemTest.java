package MessageSystem;

import antlr.debug.MessageAdapter;
import messageSystem.Abonent;
import messageSystem.Address;
import messageSystem.Message;
import messageSystem.MessageSystem;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

/**
 * Created by Alena on 5/30/14.
 */
public class MessageSystemTest {
    MessageSystem messageSystem;

    @Before
    public void setUp() throws Exception
    {
        messageSystem = new MessageSystem();
    }

    @Test
    public void testSendMessage() throws Exception
    {
        Abonent abonent = mock(Abonent.class);
        Address address = mock(Address.class);
        when(abonent.getAddress()).thenReturn(address);

        Message message = mock(Message.class);
        when(message.getTo()).thenReturn(address);
        messageSystem.addService(abonent);
        messageSystem.sendMessage(message);
    }

    @Test
    public void testExecFroAbonentSuccess() throws Exception
    {
        Abonent abonent = mock(Abonent.class);
        Address address = mock(Address.class);
        when(abonent.getAddress()).thenReturn(address);

        Message message = mock(Message.class);
        when(message.getTo()).thenReturn(address);

        messageSystem.addService(abonent);
        messageSystem.sendMessage(message);

        messageSystem.execForAbonent(abonent);
        verify(message, atLeastOnce()).exec(abonent);
    }

    @Test
    public void testExecForAbonentFail() throws Exception
    {
        Abonent abonent = mock(Abonent.class);
        Address address = mock(Address.class);
        when(abonent.getAddress()).thenReturn(address);

        Message message = mock(Message.class);
        when(message.getTo()).thenReturn(address);

        try
        {
            messageSystem.sendMessage(message);
        }
        catch (NullPointerException e) {}

        messageSystem.execForAbonent(abonent);
        verify(message, never()).exec(abonent);
    }
}
