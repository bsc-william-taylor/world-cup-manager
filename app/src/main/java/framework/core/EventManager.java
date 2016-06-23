package framework.core;

import java.util.*;

import framework.IEvent;
import framework.IEventListener;
import framework.IEventManager;

public class EventManager implements IEventManager {
	public class QueueEvent {
		IEvent Event;
		Object Data;
		Timer Timer;
	}
	
	private ArrayList<IEventListener> Listeners;
	private static EventManager Instance;
	
	public EventManager() {
		Listeners = new ArrayList<IEventListener>();
	}
	
	public static EventManager get() {
		if(Instance == null) {
			Instance = new EventManager();
		} return Instance;
	}

	@Override
	public void addListener(IEventListener event) {
		Listeners.add(event);
	}

	@Override
	public void removeListener(IEventListener event) {
		for(int i = 0; i < Listeners.size(); i++) {
			IEventListener observer = Listeners.get(i);
			if(event.equals(observer)) {
				Listeners.remove(i);
			}
		}
	}
	
	@Override
	public void triggerEvent(IEvent event, Object data) {
		if(event != null) {
			event.onActivate(data);
		}
	}

	@Override
	public void update() {
		for(int i = 0; i < Listeners.size(); i++) {
			Listeners.get(i).check(this);
		}		
	}

	@Override
	public void addListeners(IEventListener[] event) {
		for(IEventListener e : event) {
			addListener(e);
		}
	}

	@Override
	public void queueEvent(IEvent event, int ms, Object data) {
		new Timer().schedule(new TimerTask() {
			private IEvent e;
			private Object d;
			
			public TimerTask e(IEvent e, Object data) {
				this.e = e;
				this.d = data;
				return this;
			}
			
			@Override
			public void run() {
				e.onActivate(d);
			}
			
		}.e(event, data), ms);
	}
}
