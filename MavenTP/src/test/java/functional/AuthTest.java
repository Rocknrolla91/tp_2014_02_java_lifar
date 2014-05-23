package functional;

import com.sun.istack.internal.NotNull;
import gameServer.GameServer;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.Timeout;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import database.AccountService;
import database.AccountServiceImpl;
import database.DatabaseConnector;
import resourceSystem.ResourceSystem;


/**
 * Created by Alena on 18.05.2014.
 */
public abstract class AuthTest {
    protected final static int PORT = 8080;
    protected static GameServer gameServer;
    protected static Thread gameThread;
    protected static AccountService accountService;
    protected static ResourceSystem resourceSystem = ResourceSystem.getInstance();

    @Rule
    public Timeout testTimeout = new Timeout(20000);

    @BeforeClass
    public static void setUp() throws Exception {
        DatabaseConnector database = new DatabaseConnector(resourceSystem.getConfigFile("H2"));
        gameServer = new GameServer(PORT, database);
        gameThread = new Thread(() -> {
            try {
                gameServer.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        gameThread.start();

        Thread.sleep(100);

        accountService = new AccountServiceImpl(database, null);
    }

    @AfterClass
    public static void clearUp() throws Exception {
        gameServer.stop();
        gameThread.join();
    }

    protected boolean testAuth(@NotNull String url, @NotNull String username, @NotNull String password) {
        WebDriver driver = new HtmlUnitDriver(true);

        driver.get(url);

        WebElement usernameField = driver.findElement(By.name("login"));
        usernameField.sendKeys(username);
        WebElement passwordField = driver.findElement(By.name("password"));
        passwordField.sendKeys(password);
        WebElement submitButton = driver.findElement(By.name("submit"));
        submitButton.submit();

        boolean result;
        try {
            result = new WebDriverWait(driver, 10).until(new ExpectedCondition<Boolean>() {
                @Override
                @NotNull
                public Boolean apply(@NotNull WebDriver d) {
                    WebElement el = d.findElement(By.id("sessionId"));
                    return !el.getText().equals("");
                }
            });
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            result = false;
        }
        driver.quit();
        return result;
    }
}
