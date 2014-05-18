package templator;

import resourceSystem.Resource;

import java.io.Serializable;

/**
 * Created by Alena on 5/18/14.
 */
public class Templates implements Resource, Serializable {
    public static String INDEX = "index.tml";
    public static String TIMER = "timer.tml";
    public static String REGIST = "registration.tml";
    public static String AUTH = "authorize.tml";
    public static String WAIT = "waiting.tml";
}
