package messageSystem;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by Alena on 4/6/14.
 */
public class MessageSystem {
    private Map<Address, ConcurrentLinkedQueue<Message>> messages = new HashMap<>();
    private AddressService addressService = new AddressService();
    private static MessageSystem messageSystem;

    public static MessageSystem getInstance()
    {
        if(messageSystem == null)
            messageSystem = new MessageSystem();
        return messageSystem;
    }

    private MessageSystem() {}

    public void addService(Abonent abonent)
    {
        messages.put(abonent.getAddress(), new ConcurrentLinkedQueue<Message>());
    }

    public void sendMessage(Message message)
    {
        Queue<Message> messageQueue = messages.get(message.getTo());
        messageQueue.add(message);
    }

    public void execForAbonent(Abonent abonent)
    {
        Queue<Message> messageQueue = messages.get(abonent.getAddress());
        if(messageQueue == null) {return;}
        while(!messageQueue.isEmpty())
        {
            Message message = messageQueue.poll();
            message.exec(abonent);
        }
    }

    public AddressService getAddressService()
    {
        return addressService;
    }
}
