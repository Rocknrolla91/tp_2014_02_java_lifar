package resourceSystem;

/**
 * Created by Alena on 5/23/14.
 */
public class PathAndTemplate implements Resource {
    private String AUTH_P;
    private String REGIST_P;
    private String TIMER_P;
    private String INDEX_P;
    private String WAIT_P;

    public String getAUTH_P()
    {
        return AUTH_P;
    }

    public String getREGIST_P()
    {
        return REGIST_P;
    }

    public String getTIMER_P()
    {
        return TIMER_P;
    }

    public String getINDEX_P()
    {
        return INDEX_P;
    }

    public String getWAIT_P()
    {
        return WAIT_P;
    }
}
