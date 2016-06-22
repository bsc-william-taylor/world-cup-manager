package events;

import activity.MainActivity;
import scenes.KnockOutScene;
import java.util.TimerTask;
import scenes.MatchesScene;
import java.util.Timer;

import framework.core.*;
import objects.Globals;

public class ResetKoEvent implements IEvent, IUiEvent {
    private Button button;

    public ResetKoEvent(Button button) {
        this.button = button;
    }

    @Override
    public void onActivate(Object data) {
        button.setTexture("sprites/fill.png");

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                button.setTexture("sprites/button2.png");
            }
        }, 250);

        MessageBox messageBox = new MessageBox();
        messageBox.onAccept(this);
        messageBox.setMessage("Would you like to reset the KO stage ?");
        messageBox.setTitle("reset Knockout Stage");
        messageBox.EnableYesNo();
        messageBox.show(false);
    }

    @Override
    public void update() {
        ;
    }

    @Override
    public void onUiEvent() {
        if(Globals.get().resetKoStage()) {
            // reset states
            KnockOutScene knockoutScene = (KnockOutScene) SceneManager.get().getScene(MainActivity.Scenes.KNOCK_OUT);
            MatchesScene matchesScene = (MatchesScene) SceneManager.get().getScene(MainActivity.Scenes.MATCHES);

            knockoutScene.Reset();
            matchesScene.Reset();
        }

        MessageBox messageBox = new MessageBox();

        messageBox.setMessage("All done, everything back to 0");
        messageBox.setTitle("Got it!");
        messageBox.show(true);
    }
}
