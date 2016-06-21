package scenes;

import java.util.*;
import framework.core.*;
import drawables.TournamentObject;
import activity.MainActivity;
import android.view.MotionEvent;
import scenes.SplashScene.HeaderButtons;

public class KnockOutScene extends Scene {
	private TournamentObject matchGraphic;
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
		
		matchGraphic = new TournamentObject();
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
