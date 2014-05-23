package functional;

import org.junit.Assert;
import org.junit.Test;
import util.StringGenerator;

import java.util.UUID;

/**
 * Created by Alena on 18.05.2014.
 */
public class RegisterTest extends AuthTest {
    private static final String TEST_LOGIN = StringGenerator.getRandomString(6);
    private static final String TEST_PASSWORD = StringGenerator.getRandomString(6);
    private static final String TEST_SESSION_ID = UUID.randomUUID().toString();
    private final String targetUrl = "http://localhost:" + PORT + "/register";

    @Test
    public void testBadRegister() throws Exception {

        accountService.regist(targetUrl, TEST_LOGIN, TEST_PASSWORD);
        accountService.regist(targetUrl, TEST_LOGIN, TEST_PASSWORD);

        boolean result = testAuth(targetUrl, TEST_LOGIN, TEST_PASSWORD);
        Assert.assertFalse(result);

        accountService.deleteAccount(TEST_LOGIN);
    }
}