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
import drawables.HelpObject;
import scenes.SplashScene.HeaderButtons;
import android.view.MotionEvent;
import activity.MainActivity;
import events.ExitEvent;
import events.StateEvent;
import events.MuteEvent;
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
public class MenuScene extends Scene {
	/** Some final settings that can be changed quickly */
	private final static Integer BUTTON_X = 250;
	private final static Integer LABEL_X = 465;
	
	/** The buttons that switch the games state for the user  */
	private Button update;
	private Button about;
	private Button edit;
	
	/** Event listeners for the buttons above */
	private ClickEvent updateClick;
	private ClickEvent aboutClick;
	private ClickEvent editClick;
	
	/** The help image that is displayed for the user */
	private HelpObject helpImage;
	
	/** The header & the background for the menu */
	private Image headerBackground;
	private Image background;
	
	/** the background music which starts being played here */
	private AudioClip music;
	
	/** The header buttons which rest at the top of the screen */
	private HeaderButtons headerButtons;
	
	/**
	 * on create method which is used to setup all
	 * objcts used by the scene.
	 */
	@Override
	public void onCreate(IFactory factory) {
		/** get the font needed for the buttons */
		Font font = Font.get("Tiny");
		
		// and get the headerButtons & background from the asset factory
		headerButtons = factory.request("HeaderButtons");
		background = factory.request("Background");
		
		// setup the help image & buttons
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
		
		// setup the background
		headerBackground = new Image("sprites/main menu.bmp");
		headerBackground.setPosition(0, 750, 1280, 50);
		
		// then sutup button listeners and music
		updateClick = new ClickEvent(update);
		updateClick.eventType(new StateEvent(MainActivity.Scenes.GROUP, update));
		
		aboutClick = new ClickEvent(about);
		aboutClick.eventType(new StateEvent(MainActivity.Scenes.CREDITS, about));
		
		editClick = new ClickEvent(edit);
		editClick.eventType(new StateEvent(MainActivity.Scenes.EDIT, edit));
		
		music = new AudioClip(framework.core.R.raw.music);
		music.setVolume(0.2F, 0.2F);
		music.setLoop(true);
		
		// push music to the asset factory
		factory.stack(music, "BackgroundMusic");
	}
	
	/**
	 * update function that updates all objects
	 * in the scene
	 */
	@Override
	public void onUpdate() {
		background.update();
		helpImage.update();
		update.update();
		about.update();
		edit.update();
	}
	
	/**
	 * onEnter handler which adds the button listeners
	 * to the event manager so we know if they are 
	 * pressed
	 */
	@Override
	public void onEnter(Integer e) {
		EventManager.get().addListener(updateClick);
		EventManager.get().addListener(aboutClick);
		EventManager.get().addListener(editClick);
	}
	
	/**
	 * onEnter handler which adds the button listeners
	 * to the event manager so we know if they are 
	 * pressed
	 */
	@Override
	public void onExit(Integer e) {
		EventManager.get().removeListener(updateClick);
		EventManager.get().removeListener(aboutClick);
		EventManager.get().removeListener(editClick);
	}
	
	/**
	 * onRender function that adds all objects that need
	 * to be drawn to the render queue
	 */
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
	
	
	/**
	 * onTouch handler for the motion event
	 */
	@Override
	public void onTouch(MotionEvent e, int x, int y) {
		// send all event data to the listeners
		headerButtons.onTouch(e, x, y);
		helpImage.onTouch(e, x, y);
		
		if(!helpImage.isActive() && e.getAction() == MotionEvent.ACTION_DOWN) {
			updateClick.OnTouch(e, x, y);
			aboutClick.OnTouch(e, x, y);
			editClick.OnTouch(e, x, y);
		}
	}
	
	/**
	 * onFling event handler
	 */
	public void onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
		// only continue if the help image is not active 
		if(!helpImage.isActive()) {
			if(velocityX > 1250) {
				// trigger an exit event
				EventManager.get().triggerEvent(new ExitEvent(), null);
			}
			
			if(velocityX < -1250) {
				// or trigger a mute event
				EventManager.get().triggerEvent(new MuteEvent(), null);
			}
		}
	}
}
