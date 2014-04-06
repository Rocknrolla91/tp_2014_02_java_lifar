package MessageSystem;

/**
 * Created by Alena on 4/6/14.
 */
public interface Abonent {
    public Address getAddress();
    public MessageSystem getMessageSystem();
    public void setMessageSystem(MessageSystem messageSystem);
}
