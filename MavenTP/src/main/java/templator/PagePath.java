package templator;

import resourceSystem.Resource;

import java.io.Serializable;

/**
 * Created by Alena on 4/6/14.
 */
public class PagePath implements Resource, Serializable {
    public static final String AUTH_P = "/auth";
    public static final String REGIST_P = "/regist";
    public static final String TIMER_P = "/timer";
    public static final String INDEX_P = "/index";
    public static final String WAIT_P = "/wait";
    public static final String ALL_P = "/*";
}
