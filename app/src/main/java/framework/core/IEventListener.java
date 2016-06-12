package framework.core;

public interface IEventListener {
	void check(IEventManager manager);
	void eventType(IEvent event);
}
