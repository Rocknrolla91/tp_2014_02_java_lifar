package MessageSystem;

import database.AccountService;
import database.AccountSession;

/**
 * Created by Alena on 4/6/14.
 */
public class MessageToAuth extends MessageToAccountService {
    private String sessionId;
    private String login;
    private String password;
    
    public MessageToAuth(Address from, Address to,
                         String sessionId, String login, String password)
    {
        super(from, to);
        this.sessionId = sessionId;
        this.login = login;
        this.password = password;
    }

    @Override
    public void exec(AccountService accountService)
    {
        try
        {
            AccountSession accountSession = accountService.auth(sessionId, login, password);
            Message back = new MessageToSetSession(getTo(), getFrom(), accountSession);
            accountService.getMessageSystem().sendMessage(back);
        }
        catch (Exception e)
        {
            AccountSession accountSession = AccountSession.getInvalidSession(sessionId, e.getMessage());
            Message back = new MessageToSetSession(getTo(), getFrom(), accountSession);
            accountService.getMessageSystem().sendMessage(back);
        }
    }
}
