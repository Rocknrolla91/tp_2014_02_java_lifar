import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import frontend.FrontendTest;
import functional.*;

/**
 * Created by Alena on 5/12/14.
 */
public class Testing {
    public static void main(String[] args)
    {
        Class<?> clazzes[] = {/*AccountServiceTest.class*/ FrontendTest.class};
        Result result = JUnitCore.runClasses(clazzes);
        if(result.wasSuccessful())
        {
            System.out.println("All tests passed successful");
        }
        else
        {
            for(Failure failure: result.getFailures())
            {
                System.out.println("Faild test:" + failure.toString());
            }
        }
    }
}
