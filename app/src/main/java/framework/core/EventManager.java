/**
 * Copyright (c) 2014 - William Taylor <wi11berto@yahoo.co.uk>
 *
 *	This software is provided 'as-is', without any express or implied warranty. 
 *  In no event will the authors be held liable for any damages arising from 
 *  the use of this software. Permission is granted to anyone to use this 
 *  software for any purpose, including commercial applications, and to 
 *  alter it and redistribute it freely, subject to the following 
 *  restrictions:
 *
 *	1. The origin of this software must not be misrepresented; 
 *     you must not claim that you wrote the original software. 
 *	   If you use this software in a product, an acknowledgment 
 *     in the product documentation would be appreciated 
 *     but is not required.
 *
 *  2. Altered source versions must be plainly marked as such, 
 *     and must not be misrepresented as being the original 
 *     software.
 *  
 *  3. This notice may not be removed or altered 
 *     from any source distribution.
 *  
 **/
package framework.core;

import java.util.*;

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
