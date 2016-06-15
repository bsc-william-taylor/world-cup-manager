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

import java.util.ArrayList;
import framework.core.*;

/**
 * This is the credits scene which shows
 * the logos and who build the application
 * 
 * @version : final version for release
 * @author : William Taylor
 */
public class CreditsScene extends Scene {
	private static final Integer ANIMATION_SPEED = 50;
	/** The buttons at the top that can be pressed to switch states*/
	private HeaderButtons headerButtons;
	/** Animation class which will play when we swipe**/
	private SceneAnimation animation;
	/** the assets to be moved animation */
	private ArrayList<Object> moveables;
	/** A inner class that holds the logos background etc */
	private CreditImage sceneImages;
	/** A inner class that holds all the text elements */
	private CreditText sceneText;
	/** */
	private CreditBackground sceneBackground;
	
	/**
	 * The on create method which loads all needed assets
	 */
	@Override
	public void onCreate(IFactory factory) {
		// Create the inner classes and pass the factory reference
		sceneBackground = new CreditBackground(factory);
		sceneImages = new CreditImage(factory);
		sceneText = new CreditText(factory);
		animation = new SceneAnimation();
		
		// get the headerButtons from the asset factory
		headerButtons = factory.request("HeaderButtons");
		
		// and setup that objects to be moved by scene animation
		moveables = new ArrayList<Object>();
		sceneImages.add(moveables);
		sceneText.add(moveables);
	}
	
	/**
	 * The update function that updates the positions of all drawables
	 */
	@Override
	public void onUpdate() {
		// update the positions
		sceneBackground.update();
		sceneImages.update();
		animation.update();
		sceneText.update();
	}
	
	/** 
	 * The render function that push assets to the 
	 * render list to be drawn
	 */
	@Override
	public void onRender(RenderQueue renderList) {
		// push renderables to the queue
		renderList.put(sceneBackground);
		renderList.put(sceneText);
		renderList.put(sceneImages);
	}
	
	/**
	 * The handler for when a swipe event is created
	 */
	@Override
	public void onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
		// Play a animation is a swipe event is called
		if(velocityX >= 1000) {
			animation.setupAnimation(moveables);
			animation.setVelocity(new Vector2(ANIMATION_SPEED, 0));
			animation.setState(MainActivity.Scenes.MAIN_MENU);
			animation.beginAnimation();
		} else if(velocityX <= -1000) {			
			animation.setupAnimation(moveables);
			animation.setVelocity(new Vector2(-ANIMATION_SPEED, 0));
			animation.setState(MainActivity.Scenes.ABOUT);
			animation.beginAnimation();
		}
	}

	/**
	 * a handler for when a touch event is fired
	 */
	@Override
	public void onTouch(MotionEvent e, int x, int y) {
		headerButtons.onTouch(e, x, y);
	}
	
	/**
	 * The inner class that holds all the text
	 * for the scene we do this to make code
	 * easier to edit and manage.
	 * 
	 */
	private class CreditText implements IRenderable {
		/** Some settings for the text displayed in the credits scene */
		private static final String PROGRAMMING_BY = "Developer William Taylor";
		private static final String DESIGN_BY = "Design Constantin Toader";
		private static final String MUSIC_BY = "Music Scott McLean";
		private static final String ART_BY = "Art Daniel Taylor";
		
		/** The back plate for the text */
		private Image background;
		
		/** the string that says who did the music */
		private Label musicArtist;
		
		/** the string that says who did the programming */
		private Label programmer;
		
		/** the string that says who managed the project */
		private Label manager;
		
		/** the string that says who did the art */
		private Label artist;
		
		/** Just your basic constructor */
		public CreditText(IFactory factory) {
			Font font = Font.get("small");
			
			// Init all objects used here
			background = new Image("sprites/gs.PNG");
			background.setPosition(200, 200, 900, 300);
			musicArtist = new Label(font);
			programmer = new Label(font);
			manager = new Label(font);
			artist = new Label(font);
		
			// set the text to load
			programmer.text(PROGRAMMING_BY);
			musicArtist.text(MUSIC_BY);
			manager.text(DESIGN_BY);
			artist.text(ART_BY);

			// load all text at the default position
			musicArtist.load(0, 0);
			programmer.load(0, 0);
			manager.load(0, 0);
			artist.load(0, 0);
			
			// & translate them to center at 550px
			musicArtist.setInitialPosition(550 - musicArtist.getWidth()/2, 300);
			programmer.setInitialPosition(550 - programmer.getWidth()/2, 350);
			manager.setInitialPosition(550 - manager.getWidth()/2, 400);
			artist.setInitialPosition(550 - artist.getWidth()/2, 250);
			
			// set the colour of the text to yellow
			musicArtist.setColour(1f, 1f, 0f, 1f);
			programmer.setColour(1f, 1f, 0f, 1f);
			manager.setColour(1f, 1f, 0f, 1f);
			artist.setColour(1f, 1f, 0f, 1f);
		}
		
		/** This function adds all the text objects 
		 * to the vector ready for scene animation
		 *  */
		public void add(ArrayList<Object> animationObject) {
			animationObject.add(musicArtist);
			animationObject.add(background);
			animationObject.add(programmer);
			animationObject.add(manager);
			animationObject.add(artist);
		}

		/** Just your basic update function */
		public void update() {
			// upadte the positions of all objects
			musicArtist.update();
			background.update();
			programmer.update();
			manager.update();
			artist.update();
		}

		@Override
		public void render() {
			background.render();
			musicArtist.render();
			programmer.render();
			manager.render();
			artist.render();
			
		}	
	}
	
	private class CreditBackground implements IRenderable {
		/** The header at the top which shows which state you will go to */
		private Image headerBackground;
		/** The background image for the scene */
		private Image background;
		
		public CreditBackground(IFactory factory) {
			// load and get the background from the factory class
			headerBackground = new Image("sprites/credits.bmp");
			headerBackground.setPosition(0, 750, 1280, 50);
			background = factory.request("SplashBackground");
		}
		
		public void update() {
			headerBackground.update();
			background.update();
		}
		
		@Override
		public void render() {
			background.render();
			headerBackground.render();
		}
	}
	
	/**
	 * The inner class that holds all the images
	 * for the scene we do this to make code
	 * easier to edit and manage.
	 * 
	 */
	private class CreditImage implements IRenderable {
		/** All three logos for the people who built the application */
		private Image logoThree;
		private Image logoTwo;
		private Image logoOne;
		
		/** You basic constructor just sets up the objects */
		public CreditImage(IFactory factory) {
			logoThree = new Image("sprites/bggame.bmp");
			logoTwo = new Image("sprites/littlebox.bmp");
			logoOne = new Image("sprites/ntgame.bmp");
			
			// & set the positions
			logoThree.setPosition(800, 225, 150, 80);
			logoTwo.setPosition(800, 310, 150, 80);
			logoOne.setPosition(800, 395, 150, 80);
		}
		
		/** same as seen in the SceneText add function */
		public void add(ArrayList<Object> animationObjects) {
			animationObjects.add(logoThree);
			animationObjects.add(logoTwo);
			animationObjects.add(logoOne);
		}
		
		/** Just your basic update function */
		public void update() {
			// update the positions of all the objects
			logoThree.update();
			logoTwo.update();
			logoOne.update();
		}
	
		@Override
		public void render() {
			logoThree.render();
			logoTwo.render();
			logoOne.render();		
		}
	}
}