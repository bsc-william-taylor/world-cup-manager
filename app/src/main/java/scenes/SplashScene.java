package scenes;

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
 */
import android.view.MotionEvent;
import activity.MainActivity;
import events.ExitEvent;
import events.MuteEvent;
import objects.Globals;
import framework.core.*;

/**
 * The splash screen for the application 
 * which loads all the assets for the 
 * app as well as display a image
 * while the user waits
 * 
 * @version : final version for release
 * @author : William Taylor
 */
public class SplashScene extends Scene implements ISceneLoader {
	/** Some settings for the scene */
	private static final Float ALPHA_INCREMENT = 0.2F;
	private static final Float ALPHA_END = 1.13F;
	/** The alpha value for the background */
	private Float alpha = 0.0f;
	/** The text label that shows the version number of the app */
	private Label version;
	/** The hepder that shows the apps names*/
	private Label header;
	/** the background for the scene */
	private Image background;

	/**
	 * onCreate function that is called on the hasLoaded function returns true.
	 */
	@Override
	public void onCreate(IFactory factory) {
		Globals.get();
		
		// Initialise the header buttons 
		Button LevelButton1 = new Button();
		Button LevelButton2 = new Button();
		
		LevelButton1.setSprite("sprites/fill.png", 0, 750, 200, 50);	
		LevelButton2.setSprite("sprites/fill.png", 1080, 750, 200, 50);
		
		// & the handlers
		ClickEvent RightButton = new ClickEvent(LevelButton2);
		ClickEvent LeftButton = new ClickEvent(LevelButton1);
		
		RightButton.eventType(new NextStateEvent(true));
		LeftButton.eventType(new NextStateEvent(false));
		
		// load the fonts for the application
		new Font("fonts/MediumText.xml", "fonts/MediumText.png", "medium");
		new Font("fonts/SmallText.xml", "fonts/smallText.png", "small");
		new Font("fonts/text.xml", "fonts/tinyText.png", "tiny");
 
		// then setup the headerButtons object and pass it to the asset factory
		HeaderButtons headerButton = new HeaderButtons();
		headerButton.Initialise(RightButton, LeftButton);
	
		// Load the menu  background
		Image normalBackground = new Image("sprites/menu.bmp");
		normalBackground.setPosition(-5, -5, 1290, 805);
		
		Image filled = new Image("sprites/fill.png");
	
		
		// Stack all assets that will be used by multiple scene
		factory.stack(headerButton, "HeaderButtons");
		factory.stack(RightButton, "RightButton");
		factory.stack(LeftButton, "LeftButton");
		factory.stack(background, "SplashBackground");
		factory.stack(normalBackground, "Background");
		factory.stack(version, "Version");
		factory.stack(header, "Header");
		factory.stack(filled, "fill");
		
		// Add the event listeners for the header buttons
		// as they are always there
		EventManager.get().addListener(RightButton);
		EventManager.get().addListener(LeftButton);
	}
	
	/** Update all objects in the sdcene */
	@Override
	public void onUpdate() {
		// set the colour of all the objects in the scene
		version.setColour(1F, 1F, 0F, alpha);
		version.update();
		
		background.shade(1F, 1F, 1F, alpha);
		background.update();
			
		header.setColour(1F, 1f, 0F, alpha);
		header.update();
	
		// increment the alpha for a fade in effect
		alpha += ALPHA_INCREMENT;
	}
	
	/** Push all renderables to the renderList to be
	 *  drawn */
	@Override
	public void onRender(RenderQueue renderList) {
		renderList.put(background);
		renderList.put(header);
		renderList.put(version);
	}
	
	/** When the state implements ISceneLoader the onEnter function is called constantly
	 *  and so does hasLoaded once the hasLoaded function returns true it does to the
	 *  starting state */
	@Override
	public void onEnter(Integer next) {
		// we dont want to init the objects multiple times !!!
		if(background == null && header == null) {
			// init the fonts needed for the splash screen
			Font l = new Font("fonts/largeText.xml", "fonts/largeText.png", "large");
            Font v = new Font("fonts/version.xml", "fonts/version.png", "version");
			
			// and load the objects
			background = new Image("sprites/intro.bmp");
			background.setPosition(0, 0, 1280, 800);
			
			version = new Label(v, "Version 1.21");
			version.load(1215, 20);
			
			header = new Label(l, "World Cup Manager");
			header.load(200, 400);			
		}
	}

	/** has the scene finished loading yet */
	@Override
	public boolean hasLoaded() {
		return(alpha >= ALPHA_END);
	}
	
	/** Inner class for the header buttons this is passed to every state */
	public class HeaderButtons {
		private ClickEvent rightButton;
		private ClickEvent leftButton;
		
		/** on touch handler which passes information to the listeners */
		public void onTouch(MotionEvent e, int x, int y) {
			if(e.getAction() == MotionEvent.ACTION_DOWN) {
				rightButton.OnTouch(e, x, y);
				leftButton.OnTouch(e, x, y);
			}
		}

		/** Init function that does what it says it does */
		public void Initialise(ClickEvent rightButton2, ClickEvent leftButton2) {
			rightButton = rightButton2;
			leftButton = leftButton2;
		}
	}
	
	/** Another inner class that manages when the buttons have been pressed */
	private class NextStateEvent implements IEvent {
		private Boolean locateRight;
		
		public NextStateEvent(Boolean right) {
			locateRight = right;
		}

		@Override
		public void onActivate(Object data) {
			// Get the scene manager and event manager
			SceneManager scenes = SceneManager.get();
			EventManager events = EventManager.get();
			
			Integer ID = scenes.getLocation();
			
			// if the right button is pressed
			if(locateRight) {
				// go to these states or exit
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
				// for any other event go back to the main menu or exit
				switch(ID) {
					case MainActivity.Scenes.MAIN_MENU: events.triggerEvent(new ExitEvent(), null); break;
					
					default: scenes.switchTo(MainActivity.Scenes.MAIN_MENU); break;
				}
			}

		}

		/** Not needed for this type of event */
		@Override
		public void update() {
			;
		}		 
	}
}
