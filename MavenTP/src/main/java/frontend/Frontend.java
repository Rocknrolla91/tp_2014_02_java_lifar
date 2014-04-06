package frontend;

import database.AccountServiceImpl;
import database.AccountSession;
import exception.AccountServiceException;
import exception.EmptyDataException;
import templator.PageGenerator;

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
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Alena on 2/20/14.
 */
public class Frontend extends HttpServlet{
    private AtomicLong userIdGen = new AtomicLong(0);
    public AccountServiceImpl accountServiceImpl;
    private Map<String, AccountSession> sessions;

    public void setSession(AccountSession session)
    {
        AccountSession accountSession = sessions.get(session.getSessionId());
        if(accountSession != null) {accountSession.updateSession(session);}
    }

    public Frontend(AccountServiceImpl accountServiceImpl)
    {
        this.accountServiceImpl = accountServiceImpl;
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

        switch(request.getRequestURI())
        {
            case "/index":
            {
                HttpSession session = request.getSession();
                Long userId = (Long) session.getAttribute("userId");
                if(userId != null) {
                    currentlyPage = "index.tml";
                    pageVariables.put("userId", userId);
                    okResponse(response, pageVariables, currentlyPage);
                } else {
                    response.sendRedirect("/auth");
                }
                break;
            }
            case "/auth":
            {
                currentlyPage = "authorize.tml";
                okResponse(response, pageVariables, currentlyPage);
                break;
            }
            case "/timer":
            {
                HttpSession session = request.getSession();
                Long userId = (Long) session.getAttribute("userId");
                if(userId != null) {
                    currentlyPage = "timer.tml";
                    pageVariables.put("userId", userId);
                    pageVariables.put("refreshPeriod", "1000");
                    pageVariables.put("serverTime", getTime());
                    okResponse(response, pageVariables, currentlyPage);
                } else {
                    response.sendRedirect("/auth");
                }
                    break;
            }
            case "/regist":
            {
                currentlyPage = "registration.tml";
                okResponse(response, pageVariables, currentlyPage);
                break;
            }
            default:
                response.sendRedirect("/index");
                break;
        }
    }
    private void getUserId(HttpServletResponse response,
                           HttpServletRequest request)
            throws IOException, ServletException
    {
        HttpSession session = request.getSession();
        Long userId = userIdGen.getAndIncrement();
        session.setAttribute("userId", userId);
        response.sendRedirect("/timer");
        return;
    }

    @Override
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response)
            throws IOException, ServletException
    {
        switch(request.getRequestURI())
        {
            case "/auth":
            {
                String login = request.getParameter("login");
                String password = request.getParameter("password");
                try
                {
                    accountServiceImpl.isEmptyCredentials(login, password);
                    accountServiceImpl.auth(login, password);
                    getUserId(response, request);
                }
                catch (AccountServiceException | EmptyDataException e)
                {
                    Map<String, Object> pageVariables = new HashMap<>();
                    String currentlyPage = "authorize.tml";
                    pageVariables.put("ErrorMessage", "You are not valid, guy!");
                    okResponse(response, pageVariables, currentlyPage);
                }
                break;
            }
            case "/regist":
            {
                String login = request.getParameter("login");
                String password = request.getParameter("password");
                try
                {
                    accountServiceImpl.isEmptyCredentials(login, password);
                    accountServiceImpl.regist(login, password);
                    getUserId(response, request);
                }
                catch (AccountServiceException | EmptyDataException e)
                {
                    Map<String, Object> pageVariables = new HashMap<>();
                    String currentlyPage = "registration.tml";
                    pageVariables.put("ErrorMessage", e.getMessage());
                    okResponse(response, pageVariables, currentlyPage);
                }
                break;

            }

        }
    }
}
