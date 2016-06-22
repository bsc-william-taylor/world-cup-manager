package events;

import java.util.TimerTask;
import java.util.Timer;

import framework.core.*;


public class StateEvent implements IEvent {
    private Boolean goToNextScene;
    private Button button;
    private Integer stateID;

    private Timer timerTwo;
    private Timer timerOne;

    private class TextureChange extends TimerTask {
        private final String filename = "sprites/button2.png";

        @Override
        public void run() {
            button.setTexture(filename);
            timerOne.cancel();
        }
    }

    private class TimedStateChange extends TimerTask {
        @Override
        public void run() {
            goToNextScene = true;
            timerTwo.cancel();
        }
    }

    public StateEvent(Integer state, Button sprite) {
        goToNextScene = false;
        button = sprite;
        stateID = state;
    }

    @Override
    public void update() {
        if(goToNextScene) {
            SceneManager.get().switchTo(stateID);
            goToNextScene = false;
        }
    }

    @Override
    public void onActivate(Object data) {
        button.setTexture("sprites/fill.png");

        timerOne = new Timer();
        timerTwo = new Timer();

        timerOne.schedule(new TextureChange(), 100);
        timerTwo.schedule(new TimedStateChange(), 350);
    }
}
