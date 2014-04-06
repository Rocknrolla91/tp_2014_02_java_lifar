package database;

/**
 * Created by Alena on 4/6/14.
 */
public class AccountSession {
    private String sessionId;
    private Long accountId;
    private String login;
    private String sessionStatus;
    private boolean error;

    public AccountSession(String sessionId, Long accountId, String login, String sessionStatus, boolean error)
    {
        this.sessionId = sessionId;
        this.accountId = accountId;
        this.login = login;
        this.sessionStatus = sessionStatus;
        this.error = error;
    }

    public AccountSession(String sessionId)
    {
        this(sessionId, null, null, "wait", false);
    }

    public static AccountSession getInvalidSession(String sessionId, String sessionStatus)
    {
        return new AccountSession(sessionId, null, null, sessionStatus, true);
    }

    public String getSessionId()
    {
        return this.sessionId;
    }
    public Long getAccountId()
    {
        return this.accountId;
    }

    public String getLogin()
    {
        return this.login;
    }

    public String getSessionStatus()
    {
        return this.sessionStatus;
    }

    public boolean getError() { return this.error; }

    public void updateSession(AccountSession accountSession)
    {
        this.sessionId = accountSession.getSessionId();
        this.accountId = accountSession.getAccountId();
        this.login = accountSession.getLogin();
        this.sessionStatus = accountSession.getSessionStatus();
        this.error = accountSession.getError();
    }
}
