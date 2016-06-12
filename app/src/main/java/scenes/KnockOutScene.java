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
import java.util.*;
import framework.core.*;
import drawables.TournementObject;
import activity.MainActivity;
import android.view.MotionEvent;
import scenes.SplashScene.HeaderButtons;

/**
 * This is a scene where the knockout stage 
 * of the tournement is shown.
 * 
 * @version : final version for release
 * @author : William Taylor
 */
public class KnockOutScene extends Scene {
	private TournementObject matchGraphic;
	private HeaderButtons headerButtons;
	
	private SceneAnimation animation;
	private Image backgroundHeader;
	private Image background;

	@Override 
	public void onCreate(IFactory factory) {
		backgroundHeader = new Image("sprites/knockout stage.bmp");
		backgroundHeader.setPosition(0, 750, 1280, 50);
		
		background = new Image("sprites/finals.bmp");
		background.setPosition(-8, 0, 1286, 800);
		
		matchGraphic = new TournementObject();
		matchGraphic.initialise(factory);
		
		headerButtons = factory.request("HeaderButtons");
		animation = new SceneAnimation();
	}
	
	@Override
	public void onUpdate() {	
		backgroundHeader.update();
		matchGraphic.update();
		background.update();
		animation.update();
	}
	
	@Override
	public void onRender(RenderQueue renderList) {
		renderList.put(background);
		renderList.put(backgroundHeader);
		renderList.put(matchGraphic);
	}
	
	
	@Override
	public void onEnter(Integer previous) {
		matchGraphic.onEnter();
	}
	
	@Override
	public void onExit(Integer previous) {
		matchGraphic.onExit();
		onUpdate();
	}
	
	@Override
	public void onTouch(MotionEvent e, int x, int y) {
		headerButtons.onTouch(e, x, y);
		matchGraphic.onTouchEvent(e, x, y);
	}
	
	public void Reset() {
		matchGraphic.reset();
	}
	
	@Override
	public void onFling(MotionEvent e, MotionEvent e2,  float x, float y) {
		if(x >= 2500) {
			ArrayList<Object> objects = new ArrayList<Object>();
			
			matchGraphic.add(objects);
		
			animation.setupAnimation(objects);
			animation.setVelocity(new Vector2(50, 0));
			animation.setState(MainActivity.Scenes.MAIN_MENU);
			animation.beginAnimation();
		} else if(x <= -2500) {
			ArrayList<Object> objects = new ArrayList<Object>();
			
			matchGraphic.add(objects);
			
			animation.setupAnimation(objects);
			animation.setVelocity(new Vector2(-50, 0));
			animation.setState(MainActivity.Scenes.MATCHES);
			animation.beginAnimation();
		}
	}
}
