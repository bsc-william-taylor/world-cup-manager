package framework;

public interface IEventListener {
    void check(IEventManager manager);
    void eventType(IEvent event);
}
