package MessageSystem;


import frontend.Frontend;

/**
 * Created by Alena on 4/6/14.
 */
public abstract class MessageToFrontend extends Message {

    public MessageToFrontend(Address from, Address to)
    {
        super(from, to);
    }

    @Override
    public void exec(Abonent abonent)
    {
        if(abonent instanceof Frontend)
            exec((Frontend) abonent);
    }

    public abstract void exec(Frontend frontend);
}
