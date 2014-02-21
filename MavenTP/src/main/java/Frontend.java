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
    private static Map<String, String> USERS_= new HashMap<>();
    static {
        USERS_.put("alena", "password");
        USERS_.put("ivan", "pupkin");
    }
    private AtomicLong userIdGen = new AtomicLong(0);

    public static String getTime() {
        Date date = new Date();
        date.getTime();
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

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws IOException, ServletException
    {
        String currentlyPage;
        Map<String, Object> pageVariables = new HashMap<>();

        switch(request.getPathInfo())
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
            default:
                response.sendRedirect("/index");
                break;
        }
    }
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response)
            throws IOException, ServletException
    {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        Map<String, Object> pageVariables = new HashMap<>();
        String currentlyPage = "authorize.tml";
        if(USERS_.containsKey(login)) {
            if (USERS_.get(login).equals(password)) {

                HttpSession session = request.getSession();
                Long userId = userIdGen.getAndIncrement();
                session.setAttribute("userId", userId);
                response.sendRedirect("/timer");
                return;
            }
        }
        pageVariables.put("ErrorMessage", "You are not valid, guy!");
        okResponse(response, pageVariables, currentlyPage);
    }
}
