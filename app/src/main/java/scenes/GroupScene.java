package scenes;

import scenes.SplashScene.HeaderButtons;
import activity.MainActivity;
import android.view.MotionEvent;
import java.util.ArrayList;
import drawables.GroupObject;
import drawables.MatchesObject;
import framework.core.*;

public class GroupScene extends Scene {
	private SceneAnimation animation;
	private HeaderButtons headerButtons;
	private GroupBackground background;
	private Teamgroups groups;
	private Integer down_y = 0;

	@Override 
	public void onCreate(IFactory factory) {
		background = new GroupBackground(factory);
		animation = new SceneAnimation();
		groups = new Teamgroups();

		headerButtons = factory.request("HeaderButtons");

		factory.stack(groups, "Groups");
	}

	@Override
	public void onUpdate() {
		groups.Update(down_y);
		background.Update();
		animation.update();
	}

	@Override
	public void onRender(RenderQueue renderList) {
		renderList.put(background);	
		renderList.put(groups);	
		renderList.put(background);	
	}

	@Override
	public void onEnter(Integer e) {
		down_y = 0;
		groups.onEnter();
		background.onEnter();
	}

	@Override
	public void onExit(Integer e) {
		groups.onExit();
	}

	public void Reset() {
		groups.ResetToStart();
	}

	@Override
	public void onTouch(MotionEvent e, int x, int y) {
		if(e.getAction() == MotionEvent.ACTION_DOWN) {
			headerButtons.onTouch(e, x, y);
			groups.onTouch(e, x, y); 
			down_y = 0;
		}
	}

	public void onLongPress(MotionEvent e, int x, int y) {
		groups.onLongPress(e, x, y);
	}

	@Override
	public void onFling(MotionEvent e, MotionEvent e2,  float x, float y) {
		if(x >= 1500) {
			animation.setupAnimation(groups.getSprites());
			animation.setVelocity(new Vector2(50, 0));
			animation.setState(MainActivity.Scenes.MAIN_MENU);
			animation.beginAnimation();
			
			down_y = 0;
		} else if(x <= -1500) {
			animation.setupAnimation(groups.getSprites());
			animation.setVelocity(new Vector2(-50, 0));
			animation.setState(MainActivity.Scenes.KNOCK_OUT);
			animation.beginAnimation();
			
			down_y = 0;
		} else {
			down_y  -= (int)(y / 250);
		}
	}

	private class GroupBackground implements IRenderable {
		private Image headerBackground;
		private Image background;
		private Integer passCount;

		public GroupBackground(IFactory factory) {
			headerBackground = new Image("sprites/group stage.bmp");
			headerBackground.setPosition(0, 750, 1280, 50);
			background = factory.request("SplashBackground");
			passCount = 0;
		}

		public void onEnter() {
			background.shade(0.2f, 0.2f, 0.2f, 1.0f);
		}

		public void Update() {
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
