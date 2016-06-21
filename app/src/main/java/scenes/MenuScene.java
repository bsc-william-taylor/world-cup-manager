package scenes;

import drawables.HelpObject;
import scenes.SplashScene.HeaderButtons;
import android.view.MotionEvent;
import activity.MainActivity;
import events.ExitEvent;
import events.StateEvent;
import events.MuteEvent;
import framework.core.*;

public class MenuScene extends Scene {
	private final static Integer BUTTON_X = 250;
	private final static Integer LABEL_X = 465;

	private HeaderButtons headerButtons;
	private ClickEvent updateClick;
	private ClickEvent aboutClick;
	private ClickEvent editClick;
	private HelpObject helpImage;
	private Image headerBackground;
	private Image background;
	private AudioClip music;
	private Button update;
	private Button about;
	private Button edit;

	@Override
	public void onCreate(IFactory factory) {
		Font font = Font.get("Tiny");

		headerButtons = factory.request("HeaderButtons");
		background = factory.request("Background");

		helpImage = new HelpObject();
		
		update = new Button(font);
		update.setSprite("sprites/button2.png", BUTTON_X, 455, 420, 80);
		update.setText("UPDATE RESULTS", LABEL_X, 480, 200, 50);
		update.setTextColour(1f, 1f, 0f, 1f);
		
		about = new Button(font);
		about.setSprite("sprites/button2.png", BUTTON_X, 255, 420, 80);
		about.setText("CREDITS", LABEL_X, 280, 200, 50);
		about.setTextColour(1f, 1f, 0f, 1f);
		
		edit = new Button(font);
		edit.setSprite("sprites/button2.png", BUTTON_X, 355, 420, 80);
		edit.setText("OPTIONS", LABEL_X, 380, 200, 50);
		edit.setTextColour(1f, 1f, 0f, 1f);

		headerBackground = new Image("sprites/main menu.bmp");
		headerBackground.setPosition(0, 750, 1280, 50);

		updateClick = new ClickEvent(update);
		updateClick.eventType(new StateEvent(MainActivity.Scenes.GROUP, update));
		
		aboutClick = new ClickEvent(about);
		aboutClick.eventType(new StateEvent(MainActivity.Scenes.CREDITS, about));
		
		editClick = new ClickEvent(edit);
		editClick.eventType(new StateEvent(MainActivity.Scenes.EDIT, edit));
		
		music = new AudioClip(framework.core.R.raw.music);
		music.setVolume(0.2F, 0.2F);
		music.setLoop(true);

		factory.stack(music, "BackgroundMusic");
	}

	@Override
	public void onUpdate() {
		background.update();
		helpImage.update();
		update.update();
		about.update();
		edit.update();
	}

	@Override
	public void onEnter(Integer e) {
		EventManager.get().addListener(updateClick);
		EventManager.get().addListener(aboutClick);
		EventManager.get().addListener(editClick);
	}

	@Override
	public void onExit(Integer e) {
		EventManager.get().removeListener(updateClick);
		EventManager.get().removeListener(aboutClick);
		EventManager.get().removeListener(editClick);
	}

	@Override
	public void onRender(RenderQueue renderList) {
		renderList.put(background);
		renderList.put(headerBackground);

		if(!helpImage.isActive()) {
			renderList.put(update);
			renderList.put(about);
			renderList.put(edit);
		}
		
		renderList.put(helpImage);
	}
	
	@Override
	public void inBackground() {
		if(music != null) {
			music.play();
		}
	}

	@Override
	public void onTouch(MotionEvent e, int x, int y) {
		headerButtons.onTouch(e, x, y);
		helpImage.onTouch(e, x, y);
		
		if(!helpImage.isActive() && e.getAction() == MotionEvent.ACTION_DOWN) {
			updateClick.OnTouch(e, x, y);
			aboutClick.OnTouch(e, x, y);
			editClick.OnTouch(e, x, y);
		}
	}

	public void onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
		if(!helpImage.isActive()) {
			if(velocityX > 1250) {
				EventManager.get().triggerEvent(new ExitEvent(), null);
			}
			
			if(velocityX < -1250) {
				EventManager.get().triggerEvent(new MuteEvent(), null);
			}
		}
	}
}
