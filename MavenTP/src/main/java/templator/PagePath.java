package templator;

import resourceSystem.PathAndTemplate;
import resourceSystem.Resource;
import resourceSystem.ResourceSystem;

import java.io.Serializable;

/**
 * Created by Alena on 4/6/14.
 */
public class PagePath implements Resource, Serializable {

    private static final String resource = "PagePath";
    private static final PathAndTemplate PAT = (PathAndTemplate) ResourceSystem.getInstance().getResource(resource);

    private PagePath() {}

    public static final String AUTH_P = PAT.getAUTH_P();
    public static final String REGIST_P = PAT.getREGIST_P();
    public static final String TIMER_P = PAT.getTIMER_P();
    public static final String INDEX_P = PAT.getINDEX_P();
    public static final String WAIT_P = PAT.getWAIT_P();
    //public String ALL_P = "/*";
}
