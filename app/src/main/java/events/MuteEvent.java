package events;

import framework.core.*;

public class MuteEvent implements IEvent {
	private static Button button;

	public MuteEvent(Button button) {
		MuteEvent.button = button;
	}

	public MuteEvent() {
		this(null);
	}

	@Override
	public void onActivate(Object data) {
		if(AudioClip.masterVolume > 0.0f) {
			AudioClip.masterVolume = 0.0f;
		} else {
			AudioClip.masterVolume = 1.0f;
		}
	}

	@Override
	public void update() {
		if(AudioClip.masterVolume > 0.0F && button != null) {
			button.setTexture("sprites/fill.png");
		} else {
			if(button != null) {
				button.setTexture("sprites/button2.png");
			}
		}
	}
}
