package frontend;

import exception.EmptyDataException;
import exception.ExceptionMessageClass;
import messageSystem.*;
import database.AccountSession;

import resourceSystem.ResourceSystem;
import templator.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Alena on 2/20/14.
 */
public class Frontend extends HttpServlet implements Abonent, Runnable{
    private MessageSystem messageSystem;
    private Address address;
    private Map<String, AccountSession> sessions = new ConcurrentHashMap<>();
    ResourceSystem resourceSystem;

    public void setSession(AccountSession session)
    {
        AccountSession accountSession = sessions.get(session.getSessionId());
        if(accountSession != null)
        {
            accountSession.updateSession(session);
        }
    }

    public Frontend(MessageSystem messageSystem)
    {
        resourceSystem = ResourceSystem.getInstance();
        this.setMessageSystem(messageSystem);
    }

    public static String getTime() {
        Date date = new Date();
        DateFormat formatter = new SimpleDateFormat("HH.mm.ss");
        return formatter.format(date);
    }

    private void okResponse(HttpServletResponse response,
                            Map<String, Object> variables,
                            String currentlyPage)
        throws ServletException, IOException
    {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println(PageGenerator.getPage(currentlyPage, variables));
    }

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws IOException, ServletException
    {
        String currentlyPage;
        Map<String, Object> pageVariables = new HashMap<>();
        HttpSession session = request.getSession();
        AccountSession accountSession = sessions.get(session.getId());
        getPageVariablesFromSessionStatus(accountSession,pageVariables);
        Long userId = (accountSession != null) ? accountSession.getAccountId() : null;

        String requested = request.getPathInfo();

        if(requested.equals(PagePath.INDEX_P))
        {
            if(userId == null || accountSession.getError()) {
                response.sendRedirect(PagePath.AUTH_P);
            } else {
                currentlyPage = "index.tml";
                pageVariables.put("userId", userId);
                okResponse(response, pageVariables, currentlyPage);
            }
        }
        else if(requested.equals(PagePath.AUTH_P))
        {
            currentlyPage = "authorize.tml";
            okResponse(response, pageVariables, currentlyPage);
        }
        else if(requested.equals(PagePath.TIMER_P))
        {
            if(userId == null || accountSession.getError()) {
                response.sendRedirect(PagePath.AUTH_P);
            } else {
                currentlyPage = "timer.tml";
                pageVariables.put("userId", userId);
                pageVariables.put("refreshPeriod", "1000");
                pageVariables.put("serverTime", getTime());
                okResponse(response, pageVariables, currentlyPage);
            }
        }
        else if(requested.equals(PagePath.REGIST_P))
        {
            currentlyPage = "registration.tml";
            okResponse(response, pageVariables, currentlyPage);
        }
        else if(requested.equals(PagePath.WAIT_P))
        {
            currentlyPage = "waiting.tml";
            pageVariables.put("refreshPeriod", "3000");
            okResponse(response, pageVariables, currentlyPage);
        }
        else
        {
            response.sendRedirect(PagePath.INDEX_P);
        }
    }

    private void getPageVariablesFromSessionStatus(AccountSession accountSession,  Map<String, Object> pageVariables)
    {
        if(accountSession == null)
            return;
        if(accountSession.getError())
            pageVariables.put("ErrorMessage", accountSession.getSessionStatus());
        else
        {
            pageVariables.put("InformationMessage", accountSession.getSessionStatus());
        }
    }

    @Override
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response)
            throws IOException, ServletException
    {
        Map<String, Object> pageVariables = new HashMap<>();
        String requested = request.getRequestURI();
        if(requested.equals(PagePath.AUTH_P))
        {
            try
            {
                doAuth(request, response); //return boolean
            }
            catch (EmptyDataException e)
            {
                pageVariables.put("ErrorMessage", e.getMessage());
                okResponse(response, pageVariables, "authorize.tml");
            }
        }
        else if(requested.equals(PagePath.REGIST_P))
        {
            try
            {
                doRegist(request, response);
            }
            catch (EmptyDataException e)
            {
                pageVariables.put("ErrorMessage", e.getMessage());
                okResponse(response, pageVariables, "authorize.tml");
            }
        }
        else
        {
            response.sendRedirect(PagePath.AUTH_P);
        }
    }

    public void isEmptyCredentials(String login, String password) throws EmptyDataException
    {
        if(login.isEmpty() || password.isEmpty())
            throw new EmptyDataException(ExceptionMessageClass.EMPTY);
    }

    private void doAuth(HttpServletRequest request, HttpServletResponse response)
            throws IOException, EmptyDataException
    {
        String sessionId = request.getSession().getId();
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        isEmptyCredentials(login, password);
        this.sessions.put(sessionId, new AccountSession(sessionId));

        Address thisAddress = this.getAddress();
        Address accountServiceAddress = this.getMessageSystem().getAddressService().getAccountService();
        this.getMessageSystem().sendMessage(new MessageToAuth(thisAddress, accountServiceAddress,
                sessionId, login, password));
        response.sendRedirect(PagePath.WAIT_P);
    }

    private void doRegist(HttpServletRequest request, HttpServletResponse response)
            throws IOException, EmptyDataException
    {
        String sessionId = request.getSession().getId();
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        this.sessions.put(sessionId, new AccountSession(sessionId));

        Address thisAddress = this.getAddress();
        Address accountServiceAddress = this.getMessageSystem().getAddressService().getAccountService();
        this.getMessageSystem().sendMessage(new MessageToRegist(thisAddress, accountServiceAddress,
                sessionId, login, password));
        response.sendRedirect(PagePath.WAIT_P);
    }

    @Override
    public Address getAddress()
    {
        return this.address;
    }

    @Override
    public MessageSystem getMessageSystem()
    {
        return this.messageSystem;
    }

    @Override
    public void setMessageSystem(MessageSystem messageSystem)
    {
        this.messageSystem = messageSystem;
        this.address = new Address();
        messageSystem.addService(this);
        messageSystem.getAddressService().setFrontend(address);
    }

    @Override
    public void run()
    {
        while (true) {
            this.getMessageSystem().execForAbonent(this);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
