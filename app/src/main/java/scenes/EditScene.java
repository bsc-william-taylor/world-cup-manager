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

import scenes.SplashScene.HeaderButtons;
import android.view.MotionEvent;
import activity.MainActivity;
import java.util.*;

import drawables.HelpObject;
import framework.core.*;
import events.*;

/**
 * This is the edit scene which allows the user
 * to change certain settings of the app
 * as well as reset progress
 * 
 * @version : final version for release
 * @author : William Taylor
 */
public class EditScene extends Scene implements IEvent {
	/** Some final varaibles for settings in the scene */
	private static final Integer ANIMATION_SPEED = 50;
	private static final Integer PRESS_DELAY = 50;
	private static final Integer BUTTON_X = 250;
	private static final Integer LABEL_X = 470;
	
	/** This is the class that manages the animation for the scene */
	private SceneAnimation animation;
	
	/** A vector containing all the movemable objects */
	private ArrayList<Object> animatedObjects;
	
	/** All the buttons that the user can press to change settings */
	private Button resetKoButton;
	private Button resetButton;
	private Button touchButton;
	private Button helpButton;
	private Button muteButton;
	
	/** the background images for the scene including the header */
	private Image headerBackground;
	private Image background;
	
	/** the header buttons */
	private HeaderButtons headerButtons;
	
	/** the help object which is defined in the objects package */
	private HelpObject helpObject;
	
	/** a reference to the background music*/
	private AudioClip backMusic;
	
	/** A the click listeners for the settings buttons */
	private ClickEvent resetKoClick;
	private ClickEvent touchClick;
	private ClickEvent resetClick;
	private ClickEvent muteClick;
	private ClickEvent helpClick;
	
	/**
	 * The on create method that initialises all 
	 * the objects that will be needed in the
	 * scene.
	 */
	@Override 
	public void onCreate(IFactory factory) {
		// get the font for the buttons
		Font font = Font.get("tiny");

		// setup and grab the background object
		headerBackground = new Image("sprites/options.bmp");
		headerBackground.setPosition(0, 750, 1280, 50);
		headerButtons = factory.request("HeaderButtons");
		background = factory.request("Background");
		
		// setup all the buttons for this scene
		resetButton = new Button(font);
		resetButton.setSprite("sprites/button2.png", BUTTON_X, 455, 420, 80);
		resetButton.setText("RESET TOURNAMENT", LABEL_X, 475, 320, 100);
		resetButton.setTextColour(1, 1, 0, 1);

		helpButton = new Button(font);
		helpButton.setSprite("sprites/button2.png", BUTTON_X, 355, 420, 80);
		helpButton.setText("HELP", LABEL_X, 375, 300, 100);
		helpButton.setTextColour(1, 1, 0, 1);
		
		muteButton = new Button(font);
		muteButton.setSprite("sprites/button2.png", BUTTON_X, 255, 420, 80);
		muteButton.setText("MUSIC", LABEL_X, 275, 300, 100);
		muteButton.setTextColour(1, 1, 0, 1);
		
		touchButton = new Button(font);
		touchButton.setSprite("sprites/button2.png", BUTTON_X, 155, 420, 80);
		touchButton.setText("TOUCH SOUND", LABEL_X, 175, 300, 100);
		touchButton.setTextColour(1, 1, 0, 1);
		
		resetKoButton = new Button(font);
		resetKoButton.setSprite("sprites/button2.png", BUTTON_X, 555, 420, 80);
		resetKoButton.setText("RESET KNOCKOUT STAGE", LABEL_X, 575, 300, 100);
		resetKoButton.setTextColour(1, 1, 0, 1);
	
		// then setup the events to track if they have been pressed
		resetKoClick = new ClickEvent(resetKoButton);
		touchClick = new ClickEvent(touchButton);
		resetClick = new ClickEvent(resetButton);
		helpClick = new ClickEvent(helpButton);
		muteClick = new ClickEvent(muteButton);
		
		// set the event type
		resetKoClick.eventType(new ResetKoEvent(resetKoButton));
		touchClick.eventType(new MuteEffectEvent(touchButton));
		resetClick.eventType(new ResetEvent(resetButton));
		muteClick.eventType(new MuteEvent(muteButton));
		helpClick.eventType(this);

		// get the background music and setup scene animation
		backMusic = factory.request("BackgroundMusic");
		
		animation = new SceneAnimation();
		animatedObjects = new ArrayList<Object>();
		animatedObjects.add(resetKoButton);
		animatedObjects.add(resetButton);
		animatedObjects.add(touchButton);
		animatedObjects.add(helpButton);
		animatedObjects.add(muteButton);
		
		// and init the helpObject
		helpObject = new HelpObject();
		helpObject.disable();
	}
	
	/**
	 * 
	 */
	@Override
	public void onEnter(Integer e) {
		// get the event manager
		EventManager events = EventManager.get();
		
		// and add the event listeners when the scene is entered
		events.addListener(resetKoClick);
		events.addListener(resetClick);
		events.addListener(helpClick);
		events.addListener(muteClick);
		events.addListener(touchClick);
	}
	
	/**
	 * 
	 */
	@Override
	public void onExit(Integer e) {
		// get the event manager & remove all listeners
		EventManager events = EventManager.get();
		
		// when the scene is exited
		events.removeListener(resetKoClick);
		events.removeListener(resetClick);
		events.removeListener(helpClick);
		events.removeListener(muteClick);
		events.removeListener(touchClick);
	}
	
	/**
	 * update function that does what it says it does
	 */
	@Override
	public void onUpdate() {
		// update all elements
		headerBackground.update();
		resetKoButton.update();
		touchButton.update();
		resetButton.update();
		background.update();
		helpButton.update();
		muteButton.update();
		helpObject.update();
		animation.update();
	}
	
	/** 
	 * The render function that pushes objects to the render
	 * queue to be rendered
	 */
	@Override
	public void onRender(RenderQueue renderList) {
		// push all renderable elements
		renderList.put(background);
		
		if(!helpObject.isActive()) {
			renderList.put(resetKoButton);
			renderList.put(resetButton);
			renderList.put(helpButton);
			renderList.put(muteButton);
			renderList.put(touchButton);
		}
	
		renderList.put(headerBackground);
		renderList.put(helpObject);
		
		backMusic.play();
	}
	
	/**
	 * The on fling event handler
	 */
	@Override
	public void onFling(MotionEvent e, MotionEvent e2, float x, float y) {	
		if(x >= 1500 && !helpObject.isActive()) {
			animation.setupAnimation(animatedObjects);			
			animation.setVelocity(new Vector2(-ANIMATION_SPEED, 0));
			animation.setState(MainActivity.Scenes.MAIN_MENU);
			animation.beginAnimation();
		} 
	}
	
	/**
	 * The onTouch event handler which passes touch events to 
	 * the objects that detect weather a button is pressed
	 */
	@Override
	public void onTouch(MotionEvent e, int x, int y) {
		helpObject.onTouch(e, x, y);
		
		// if help isnt been show pass data to the buttons on screen
		if(e.getAction() == MotionEvent.ACTION_DOWN && !helpObject.isActive()) {
			headerButtons.onTouch(e, x, y);
			resetKoClick.OnTouch(e, x, y);
			resetClick.OnTouch(e, x, y);
			touchClick.OnTouch(e, x, y);
			helpClick.OnTouch(e, x, y);
			muteClick.OnTouch(e, x, y);	
		} 	
	}

	/** The scene handles when the show help event is called */
	@Override
	public void onActivate(Object data) {
		// switch the texture
		helpButton.setTexture("sprites/fill.png");
		
		// schedule for it to reverse in a amount of time
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				helpObject.enable();
				helpButton.setTexture("sprites/button2.png");
				cancel();
			}
		}, PRESS_DELAY);
	}
	

	/** Not needed for this type of event */
	@Override
	public void update() {
		;
	}
}
