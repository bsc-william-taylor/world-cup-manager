package scenes;

import android.view.MotionEvent;
import activity.MainActivity;
import events.ExitEvent;
import events.MuteEvent;
import objects.Globals;
import framework.core.*;

public class SplashScene extends Scene implements ISceneLoader {
	private static final Float ALPHA_INCREMENT = 0.2F;
	private static final Float ALPHA_END = 1.13F;
	private Float alpha = 0.0f;
	private Label version;
	private Label header;
	private Image background;

	@Override
	public void onCreate(IFactory factory) {
		Globals.get();

		Button levelButtonOne = new Button();
		Button levelButtonTwo = new Button();
		
		levelButtonOne.setSprite("sprites/fill.png", 0, 750, 200, 50);
		levelButtonTwo.setSprite("sprites/fill.png", 1080, 750, 200, 50);

		ClickEvent rightButton = new ClickEvent(levelButtonTwo);
		ClickEvent leftButton = new ClickEvent(levelButtonOne);
		
		rightButton.eventType(new NextStateEvent(true));
		leftButton.eventType(new NextStateEvent(false));

		new Font("fonts/MediumText.xml", "fonts/MediumText.png", "medium");
		new Font("fonts/SmallText.xml", "fonts/smallText.png", "small");
		new Font("fonts/text.xml", "fonts/tinyText.png", "tiny");

		HeaderButtons headerButton = new HeaderButtons();
		headerButton.initialise(rightButton, leftButton);
	
		// Load the menu  background
		Image normalBackground = new Image("sprites/menu.bmp");
        Image filled = new Image("sprites/fill.png");
		normalBackground.setPosition(-5, -5, 1290, 805);

		factory.stack(headerButton, "HeaderButtons");
		factory.stack(rightButton, "RightButton");
		factory.stack(leftButton, "LeftButton");
		factory.stack(background, "SplashBackground");
		factory.stack(normalBackground, "Background");
		factory.stack(version, "Version");
		factory.stack(header, "Header");
		factory.stack(filled, "fill");

		EventManager.get().addListener(rightButton);
		EventManager.get().addListener(leftButton);
	}

	@Override
	public void onUpdate() {
		version.setColour(1F, 1F, 0F, alpha);
		version.update();
		
		background.shade(1F, 1F, 1F, alpha);
		background.update();
			
		header.setColour(1F, 1f, 0F, alpha);
		header.update();

		alpha += ALPHA_INCREMENT;
	}

	@Override
	public void onRender(RenderQueue renderList) {
		renderList.put(background);
		renderList.put(header);
		renderList.put(version);
	}

	@Override
	public void onEnter(Integer next) {
		if(background == null && header == null) {
			Font l = new Font("fonts/largeText.xml", "fonts/largeText.png", "large");
            Font v = new Font("fonts/version.xml", "fonts/version.png", "version");

			background = new Image("sprites/intro.bmp");
			background.setPosition(0, 0, 1280, 800);
			
			version = new Label(v, "Version 1.21");
			version.load(1215, 20);
			
			header = new Label(l, "World Cup Manager");
			header.load(200, 400);			
		}
	}

	@Override
	public boolean hasLoaded() {
		return(alpha >= ALPHA_END);
	}

	public class HeaderButtons {
		private ClickEvent rightButton;
		private ClickEvent leftButton;

		public void onTouch(MotionEvent e, int x, int y) {
			if(e.getAction() == MotionEvent.ACTION_DOWN) {
				rightButton.OnTouch(e, x, y);
				leftButton.OnTouch(e, x, y);
			}
		}

		public void initialise(ClickEvent rightButton2, ClickEvent leftButton2) {
			rightButton = rightButton2;
			leftButton = leftButton2;
		}
	}

	private class NextStateEvent implements IEvent {
		private Boolean locateRight;
		
		public NextStateEvent(Boolean right) {
			locateRight = right;
		}

		@Override
		public void onActivate(Object data) {
			SceneManager scenes = SceneManager.get();
			EventManager events = EventManager.get();
			
			Integer ID = scenes.getLocation();

			if(locateRight) {
				switch(ID) {
					case MainActivity.Scenes.MAIN_MENU: events.triggerEvent(new MuteEvent(), null); break;
					case MainActivity.Scenes.KNOCK_OUT: scenes.switchTo(MainActivity.Scenes.MATCHES); break;
					case MainActivity.Scenes.MATCHES: scenes.switchTo(MainActivity.Scenes.GROUP); break;
					case MainActivity.Scenes.CREDITS: scenes.switchTo(MainActivity.Scenes.ABOUT); break;
					case MainActivity.Scenes.GROUP: scenes.switchTo(MainActivity.Scenes.KNOCK_OUT); break;
					case MainActivity.Scenes.ABOUT: scenes.switchTo(MainActivity.Scenes.CREDITS); break;
					
					default: break;
				}
			} else {
				switch(ID) {
					case MainActivity.Scenes.MAIN_MENU:
                        events.triggerEvent(new ExitEvent(), null); break;
					
					default: scenes.switchTo(MainActivity.Scenes.MAIN_MENU); break;
				}
			}

		}

		@Override
		public void update() {
			;
		}		 
	}
}
