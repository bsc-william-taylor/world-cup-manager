package framework.core;

import android.util.Log;
import android.view.MotionEvent;

import framework.IEvent;
import framework.IEventListener;
import framework.IEventManager;
import framework.math.Vector2;
import framework.opengl.OpenglImage;

public class ClickEvent implements IEventListener {
	private MotionEvent HoldEvent;
	private MotionEvent Motion;
	private framework.graphics.Button Button;
	private IEvent Event;
	private float x = 0;
	private float y = 0;
	
	public ClickEvent(framework.graphics.Button button) {
		Button = button;
	}
	
	public void OnTouch(MotionEvent e, float x, float y) {
		this.Motion = e;
		this.x = x;
		this.y = y;
	}

	@Override
	public void check(IEventManager manager) {
		Event.update();
		
		OpenglImage sprite = (OpenglImage)Button.getImage();
		
		Vector2 Position = sprite.getPosition();
		Vector2 Size = sprite.getSize();
		
		if(Motion != null && Motion.getAction() == MotionEvent.ACTION_DOWN) {
			if(x >= Position.getX() && x <= Position.getX() + Size.getX()) {
				if(y >= Position.getY() && y <= Position.getY() + Size.getY()) {
					manager.triggerEvent(Event, false);
					OnTouch(null, -1.0f, -1.0f);
				}
			}
		}
		
		if(HoldEvent != null && HoldEvent.getAction() == MotionEvent.ACTION_DOWN) {
			if(x >= Position.getX() && x <= Position.getX() + Size.getX()) {
				if(y >= Position.getY() && y <= Position.getY() + Size.getY()) {
					manager.triggerEvent(Event, true);
					onLongPress(null, -1, -1);
				}
			}
		}
	}

	@Override
	public void eventType(IEvent event) {
		if(event == null) {
			Log.e("Error", "null event");
		} else {
			Event = event;
		}
	}

	public void onLongPress(MotionEvent e, int x2, int y2) {
		this.HoldEvent = e;
		this.x = x2;
		this.y = y2;
	}
}
