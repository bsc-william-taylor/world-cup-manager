package events;

import activity.MainActivity;
import scenes.KnockOutScene;
import scenes.MatchesScene;
import java.util.TimerTask;
import scenes.GroupScene;
import framework.core.*;
import objects.Globals;
import java.util.Timer;

public class ResetEvent implements IEvent, IUiEvent {
	private Button button;

	public ResetEvent(Button button) {
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
		messageBox.setMessage("Would you like to reset your progress ?");
		messageBox.setTitle("Reset Application");
		messageBox.EnableYesNo();
		messageBox.show(false);
	}

	@Override
	public void onUiEvent() {
		SceneManager scenes = SceneManager.get();

		KnockOutScene knockoutScene = (KnockOutScene)scenes.getScene(MainActivity.Scenes.KNOCK_OUT);
		MatchesScene matchesScene = (MatchesScene)scenes.getScene(MainActivity.Scenes.MATCHES);
		GroupScene groupScene = (GroupScene)scenes.getScene(MainActivity.Scenes.GROUP);

		Globals.get().Reset();
		
		knockoutScene.Reset();
		matchesScene.Reset();
		groupScene.Reset();
		
		PostMessage();
	}

	private void PostMessage() {
		MessageBox messageBox = new MessageBox();
		
		messageBox.setMessage("All done, everything back to 0");
		messageBox.setTitle("Got it!");
		messageBox.show(true);
	}

	@Override
	public void update() {
		;
	}
}
