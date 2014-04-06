package MessageSystem;

import frontend.Frontend;
import database.AccountSession;

/**
 * Created by Alena on 4/6/14.
 */
public class MessageToSetSession extends MessageToFrontend {

    private AccountSession session;

    public MessageToSetSession(Address from, Address to, AccountSession accountSession)
    {
        super(from, to);
        this.session = accountSession;
    }

    @Override
    public void exec(Frontend frontend)
    {
        frontend.setSession(this.session);
    }
}
