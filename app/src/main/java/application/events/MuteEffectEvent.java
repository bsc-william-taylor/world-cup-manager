package application.events;

import application.activity.MainActivity;
import framework.IEvent;
import framework.graphics.Button;

public class MuteEffectEvent implements IEvent {
    private static Button button;

    public MuteEffectEvent(Button button) {
        MuteEffectEvent.button = button;
    }

    @Override
    public void onActivate(Object data) {
        MainActivity.TouchSounds = !MainActivity.TouchSounds;
    }

    @Override
    public void update() {
        if(MainActivity.TouchSounds) {
            if(button != null) {
                button.setTexture("sprites/fill.png");
            }
        } else {
            if(button != null) {
                button.setTexture("sprites/button2.png");
            }
        }
    }
}
