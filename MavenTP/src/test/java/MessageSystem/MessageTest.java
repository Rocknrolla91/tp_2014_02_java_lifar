package MessageSystem;

import messageSystem.Abonent;
import messageSystem.Address;
import messageSystem.Message;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Alena on 5/30/14.
 */
public class MessageTest {
    Address from;
    Address to;
    Message message;

    @Before
    public void setUp() throws Exception
    {
        from = new Address();
        to = new Address();

        message = new Message(from, to) {
            @Override
            public void exec(Abonent abonent) {

            }
        };
    }

    @Test
    public void testGetFrom() throws Exception
    {
        Assert.assertTrue(message.getFrom() == from);
    }

    @Test
    public void testGetTo() throws Exception
    {
        Assert.assertTrue(message.getTo() == to);
    }
}
