package MessageSystem;

/**
 * Created by Alena on 4/6/14.
 */
public abstract class Message {
    final private Address from;
    final private Address to;

    public Message(Address from, Address to)
    {
        this.from = from;
        this.to = to;
    }

    protected Address getFrom()
    {
        return from;
    }

    protected Address getTo()
    {
        return to;
    }

    public abstract void exec(Abonent abonent);
}
