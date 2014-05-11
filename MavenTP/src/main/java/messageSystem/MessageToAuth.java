package messageSystem;

import database.AccountService;
import database.AccountSession;

import java.sql.SQLException;

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
    public void exec(AccountService accountService) throws SQLException {
            AccountSession accountSession = accountService.auth(sessionId, login, password);
            Message back = new MessageToSetSession(getTo(), getFrom(), accountSession);
            accountService.getMessageSystem().sendMessage(back);
    }
}
