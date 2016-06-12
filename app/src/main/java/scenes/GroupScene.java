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
import activity.MainActivity;
import android.view.MotionEvent;
import java.util.ArrayList;
import drawables.GroupObject;
import drawables.MatchesObject;
import framework.core.*;

/**
 * This is the group scene where all the
 * groups are displayed as well 
 * as a lot of buttons to enter scores
 * into it is the main state of the app.
 * 
 * @version : final version for release
 * @author : William Taylor
 */
public class GroupScene extends Scene {
	/** The animation class which setups and runs the animation when moving to a new state */
	private SceneAnimation animation;
	/** The header buttons that control the app state */
	private HeaderButtons headerButtons;
	/** The background elemenets for the scene (inner class) */
	private GroupBackground background;
	/** A reference to the team groups object (inner class) it manages allt he group graphics. Buttons/images etc */
	private Teamgroups groups;
	/** The y translate value for the scene */
	private Integer down_y = 0;
	
	/**
	 * The on create function that simple inits all objects
	 */
	@Override 
	public void onCreate(IFactory factory) {
		// setup the inner classes
		background = new GroupBackground(factory);
		animation = new SceneAnimation();
		groups = new Teamgroups();
	
		// get the header buttons from the factory
		headerButtons = factory.request("HeaderButtons");
		
		// and push the group inner class
		factory.stack(groups, "Groups");
	}
	
	/**
	 * update the positions of the objects
	 */
	@Override
	public void onUpdate() {
		// we pass through the down_y which translates 
		// the objects down.
		groups.Update(down_y);
		background.Update();
		animation.update();
	}
	
	/** it does what it says */
	@Override
	public void onRender(RenderQueue renderList) {
		renderList.put(background);	
		renderList.put(groups);	
		renderList.put(background);	
	}
	
	/**
	 * on Enter handler 
	 */
	@Override
	public void onEnter(Integer e) {
		// we just call the respective onEnter function of the objects
		down_y = 0;
		groups.onEnter();
		background.onEnter();
	}
	
	/**
	 * on Exit handler
	 */
	@Override
	public void onExit(Integer e) {
		// we just call the groups onExit handler to handle the event
		groups.onExit();
	}
	
	/**
	 * Simple reset function that calls the reset function of the group
	 */
	public void Reset() {
		groups.ResetToStart();
	}
	
	/**
	 * onTouch handler which calls the respective 
	 * onTouch handler for all the scenes objects
	 */
	@Override
	public void onTouch(MotionEvent e, int x, int y) {
		if(e.getAction() == MotionEvent.ACTION_DOWN) {
			headerButtons.onTouch(e, x, y);
			groups.onTouch(e, x, y); 
			down_y = 0;
		}
	}
	
	/**
	 * onLongPress handler just calls the groups
	 * onLongPress handler.
	 */
	public void onLongPress(MotionEvent e, int x, int y) {
		groups.onLongPress(e, x, y);
	}
	
	/**
	 * onFling handler function
	 */
	@Override
	public void onFling(MotionEvent e, MotionEvent e2,  float x, float y) {		
		// if the user swipes to the right
		if(x >= 1500) {
			// setup the animation and start it
			animation.setupAnimation(groups.getSprites());
			animation.setVelocity(new Vector2(50, 0));
			animation.setState(MainActivity.Scenes.MAIN_MENU);
			animation.beginAnimation();
			
			down_y = 0;
		} else if(x <= -1500) {
			// if the user swipes to the left again, begin the animation
			animation.setupAnimation(groups.getSprites());
			animation.setVelocity(new Vector2(-50, 0));
			animation.setState(MainActivity.Scenes.KNOCK_OUT);
			animation.beginAnimation();
			
			down_y = 0;
		} else {
			down_y  -= (int)(y / 250);
		}
	}

	/** group background items */
	private class GroupBackground implements IRenderable {
		/** The header for the state */
		private Image headerBackground;
		/** The background for the state */
		private Image background;
		/** */
		private Integer passCount;
		
		/** Basic constructor */
		public GroupBackground(IFactory factory) {
			// just setup the objects
			headerBackground = new Image("sprites/group stage.bmp");
			headerBackground.setPosition(0, 750, 1280, 50);
			background = factory.request("SplashBackground");
			passCount = 0;
		}

		/** onEnter event handler */
		public void onEnter() {
			// make the background transparent
			background.shade(0.2f, 0.2f, 0.2f, 1.0f);
		}
		
		/** Your update function */
		public void Update() {
			// just update the positions
			headerBackground.update();
			background.update();
		}

		@Override
		public void render() {
			if(passCount <= 0) {
				background.render();
				++passCount;
			} else {
				headerBackground.render();
				passCount = 0;
			}			
		}
	}

	/** Football groups */
	public class Teamgroups implements IRenderable {
		private MatchesObject[] TeamMatches;
		private GroupObject[] TeamGroups;
		private Boolean ReInitialise;
		private Float TotalY = 0F;
		
		public Teamgroups() {
			TeamMatches = new MatchesObject[8];
			TeamGroups = new GroupObject[8];
			ReInitialise = false;
			Integer y = 400;
			
			String[] names = new String[] {
				"Group A",	"Group B",
				"Group C",	"Group D",
				"Group E",	"Group F",
				"Group G",	"Group H",
				"Group I",	"Group J",
				"Group K",	"Group L"
			};
			
			for(int i = 0 ; i < 8; i++) {
				TeamGroups[i] = new GroupObject(names[i], 10, y, i); 
				TeamMatches[i] = new MatchesObject(TeamGroups[i]);
				y -= 300;
			}
		}
		
		public void onLongPress(MotionEvent e, int x, int y) {
			if(e.getAction() == MotionEvent.ACTION_DOWN) {
				for(int i = 0; i < TeamMatches.length; i++) {
					TeamMatches[i].onLongPress(e, x, y);
				}
			}
		}

		public void ResetToStart() {
			ReInitialise = true;
		}
		
		public ArrayList<Object> getSprites() {
			ArrayList<Object> sprites = new ArrayList<Object>();	
			
			for(int b = 0; b < 8; b++) {
				sprites.add(TeamGroups[b].getBackground());
				
				Button[] Buttons = TeamMatches[b].getButtons();
				for(int i = 0; i < Buttons.length; i++) {
					sprites.add(Buttons[i]);
				}
					
				Image[] Flags = TeamMatches[b].getFlags();
				for(int i = 0; i < Buttons.length; i++) {
					sprites.add(Flags[i]);
				}
				
				sprites.add(TeamMatches[b].getCompleteButton());
			}
			
			for(int b = 0; b < 8; b++) {
				ArrayList<Object> Lines = TeamGroups[b].getLabels();
				sprites.add(TeamGroups[b].getTitle());
				sprites.add(TeamGroups[b].getScore());
				for(int i = 0; i < Lines.size(); i++) {
					sprites.add(Lines.get(i));
				}
			}
			
			return sprites;
		}
		
		public void onEnter() {
			for(int i = 0; i < TeamMatches.length; i++) {
				TeamMatches[i].reset();
				TeamMatches[i].onEnter();
				
				TeamGroups[i].reset();
			}
			
			
			if(ReInitialise) {		
				ReInitialise = false;
				for(int i = 0; i < TeamMatches.length; i++) {
					TeamMatches[i].restart();
					TeamGroups[i].restart();
				}
			}
			
			TotalY = 0.0F;
		}
		
		public void onExit() {
			for(int i = 0; i < TeamMatches.length; i++) {
				TeamMatches[i].onExit();
			}
		}
		
		public void onTouch(MotionEvent e, float x, float y) {
			for(int i = 0; i < TeamMatches.length; i++) {
				TeamMatches[i].onTouch(e, x, y);
			}
		}
		
		public void Update(Integer y) {
			TotalY += y;
			
			if(TotalY <= 1800.0F && TotalY >= 0.0F) {
				for(int i = 0; i < TeamMatches.length; i++) {
					TeamMatches[i].update(y);
					TeamGroups[i].update(y, animation.isRunning());
				}
			} else {
				TotalY -= y;
			}
		}
		
		@Override
		public void render() {
			for(int i = 0; i < TeamMatches.length; i++) {
				TeamMatches[i].render();
				TeamGroups[i].render();
			}
		}
	}
}
