package MessageSystem;

import messageSystem.Address;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Alena on 5/30/14.
 */
public class AddressTest {
    @Test
    public void testHashCode() throws Exception
    {
        Address first = new Address();
        Address second = new Address();
        Assert.assertTrue(second.hashCode() - first.hashCode() == 1);
    }
}
