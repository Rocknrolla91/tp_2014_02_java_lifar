package messageSystem;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Alena on 4/5/14.
 */
public class Address {
    static private AtomicInteger abonentIdCreator = new AtomicInteger();
    final private int abonentId;

    public Address()
    {
        this.abonentId = abonentIdCreator.incrementAndGet();
    }

    public int hashCode()
    {
        return abonentId;
    }
}
