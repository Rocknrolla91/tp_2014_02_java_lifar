package functional;

import org.junit.Assert;
import org.junit.Test;
import util.StringGenerator;

import java.util.UUID;


/**
 * Created by tonick on 18.05.2014.
 */
public class LoginTest extends AuthTest {
    private final String targetUrl = "http://localhost:" + PORT;
    private static final String TEST_LOGIN = StringGenerator.getRandomString(6);
    private static final String TEST_PASSWORD = StringGenerator.getRandomString(6);
    private static final String TEST_SESSION_ID = UUID.randomUUID().toString();

    @Test
    public void testLogin() throws Exception {

        accountService.regist(TEST_SESSION_ID, TEST_LOGIN, TEST_PASSWORD);

        boolean result = testAuth(targetUrl, TEST_LOGIN, TEST_PASSWORD);
        Assert.assertTrue(result);

        accountService.deleteAccount(TEST_LOGIN);
    }

    @Test
    public void testBadLogin() throws Exception {

        boolean result = testAuth(targetUrl, TEST_LOGIN, TEST_PASSWORD);
        Assert.assertFalse(result);
    }
}
