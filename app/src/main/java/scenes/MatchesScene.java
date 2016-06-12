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
import objects.Match;
import objects.MatchResult;
import android.view.MotionEvent;
import activity.MainActivity;
import java.util.ArrayList;

import framework.core.Scene;
import framework.core.*;

import objects.Globals;
import scenes.SplashScene.HeaderButtons;

public class MatchesScene extends Scene {
	private BackgroundImages sceneBackground;
	private HeaderButtons headerButtons;
	private SceneAnimation animation;
	
	private KnockoutMatches roundOfSixteen;
	private KnockoutMatches quaterFinals;
	private KnockoutMatches semiFinals;
	private KnockoutMatches finals;
	
	private MatchDay dayThree;
	private MatchDay dayTwo;
	private MatchDay dayOne;
	
	@Override 
	public void onCreate(IFactory factory) {
		sceneBackground = new BackgroundImages(factory);
		dayThree = new MatchDay(factory, -1250, 3, "sprites/Matchday 3.bmp");
		dayTwo = new MatchDay(factory, -300, 2,  "sprites/Matchday 2.bmp");
		dayOne = new MatchDay(factory, 650, 1, "sprites/Matchday 1.bmp");
		
		animation = new SceneAnimation();
		
		Globals g = Globals.get();
		for(int i = 0; i < 16; i++) {
			dayThree.PushResult(g.getDayThreeMatch(i));
			dayTwo.PushResult(g.getDayTwoMatch(i));
			dayOne.PushResult(g.getDayOneMatch(i));
		}
		
		roundOfSixteen = new KnockoutMatches(8, -2200, "sprites/eigthfinals.bmp");
		quaterFinals = new KnockoutMatches(4, -2700, "sprites/quarterfinals.bmp");
		semiFinals = new KnockoutMatches(2, -2950, "sprites/semifinals.bmp");
		finals = new KnockoutMatches(1, -3125, "sprites/final.bmp");
		
		headerButtons = factory.request("HeaderButtons");
	}
	
	@Override
	public void onUpdate() {
		sceneBackground.Update();
		roundOfSixteen.Update();
		quaterFinals.Update();
		semiFinals.Update();
		animation.update();
		dayThree.Update();
		dayOne.Update();
		dayTwo.Update();
		finals.Update();
	}
	
	@Override
	public void onRender(RenderQueue renderList) {
		renderList.put(sceneBackground);
		renderList.put(roundOfSixteen);
		renderList.put(quaterFinals);
		renderList.put(semiFinals);
		renderList.put(finals);
		
		renderList.put(dayOne);
		renderList.put(dayTwo);
		renderList.put(dayThree);
		renderList.put(sceneBackground);
	}
	
	@Override 
	public void onEnter(Integer previous) {
		roundOfSixteen.Reset();
		quaterFinals.Reset();
		semiFinals.Reset();
		finals.Reset();
		
		dayThree.Reset();
		dayTwo.Reset();
		dayOne.Reset();
		
		roundOfSixteen.onEnter(Globals.get().getRoundOfSixteenResults());
		quaterFinals.onEnter(Globals.get().getQuaterFinalResults());
		semiFinals.onEnter(Globals.get().getSemiFinalResults());
		finals.onEnter(Globals.get().getFinalResults());
		
		dayOne.onEnter();
		dayTwo.onEnter();
		dayThree.onEnter();
	}
	
	public void Reset() {
		roundOfSixteen.ResetScores();
		quaterFinals.ResetScores();
		semiFinals.ResetScores();
		dayThree.ResetScores();
		dayTwo.ResetScores();
		dayOne.ResetScores();
		finals.ResetScores();
	}

	@Override
	public void onTouch(MotionEvent e, int x, int y) {
		if(e.getAction() == MotionEvent.ACTION_DOWN) {
			headerButtons.onTouch(e, x, y);
			roundOfSixteen.y = 0.0F;
			quaterFinals.y = 0.0F;
			semiFinals.y = 0.0F;
			finals.y = 0.0F;
			
			dayThree.y = 0.0F;
			dayOne.y = 0.0F;
			dayTwo.y = 0.0F;
		}
	}
	
	private void SetupAnimation() {
		ArrayList<Object> objects = new ArrayList<Object>();
		
		objects.add(roundOfSixteen.Header);
		objects.add(quaterFinals.Header);
		objects.add(semiFinals.Header);
		objects.add(finals.Header);
		
		objects.add(dayThree.background);
		objects.add(dayTwo.background);
		objects.add(dayOne.background);
		
		for(int i = 0; i < dayOne.entrysID.length; i++) {
			objects.add(dayThree.entryRight[i]);
			objects.add(dayThree.entryLeft[i]);
			objects.add(dayThree.results[i]);
			
			objects.add(dayTwo.entryRight[i]);
			objects.add(dayTwo.entryLeft[i]);
			objects.add(dayTwo.results[i]);
			
			objects.add(dayOne.entryRight[i]);
			objects.add(dayOne.entryLeft[i]);
			objects.add(dayOne.results[i]);			
		}
		
		for(int i = 0; i < roundOfSixteen.EntryRight.length; i++) {
			objects.add(roundOfSixteen.EntryRight[i]);
			objects.add(roundOfSixteen.EntryLeft[i]);
			objects.add(roundOfSixteen.Results[i]);
		}
		
		for(int i = 0; i < finals.EntryRight.length; i++) {
			objects.add(finals.EntryRight[i]);
			objects.add(finals.EntryLeft[i]);
			objects.add(finals.Results[i]);
		}
		
		for(int i = 0; i < semiFinals.EntryRight.length; i++) {
			objects.add(semiFinals.EntryRight[i]);
			objects.add(semiFinals.EntryLeft[i]);
			objects.add(semiFinals.Results[i]);
		}
		
		for(int i = 0; i < quaterFinals.EntryRight.length; i++) {
			objects.add(quaterFinals.EntryRight[i]);
			objects.add(quaterFinals.EntryLeft[i]);
			objects.add(quaterFinals.Results[i]);
		}
		
		animation.setupAnimation(objects);
	}
	
	@Override
	public void onFling(MotionEvent e, MotionEvent e2, float x, float y) {
		if(x >= 1500) {
			SetupAnimation();
			
			animation.setState(MainActivity.Scenes.MAIN_MENU);
			animation.setVelocity(new Vector2(50, 0));
			animation.beginAnimation();
		} else if(x <= -1500) {
			SetupAnimation();
			
			animation.setState(MainActivity.Scenes.GROUP);
			animation.setVelocity(new Vector2(-50, 0));
			animation.beginAnimation();
		}  else {
			Float value = y / 250.0f;
			roundOfSixteen.y -= value;
			quaterFinals.y -= value;
			semiFinals.y -= value;
			dayThree.y -= value;
			finals.y -= value;
			dayTwo.y -= value;
			dayOne.y -= value;
		}
	}
	

	
	/** Knockout matches results are stored and displayed by this class **/
	private class KnockoutMatches implements IRenderable {
		public Label[] EntryRight;
		public Label[] EntryLeft;
		public Label[] Results;
		public Image Header;
		
		public Boolean resetMatchList;
		public String[] EntrysID;
		public float TotalY = 0F;
		public float y = 0F;
		public float start_y;
		
		
		public KnockoutMatches(Integer number, int y, String filename) {
			EntryRight = new Label[number];
			EntryLeft = new Label[number];
			Results = new Label[number];
			EntrysID = new String[number];
					
			resetMatchList = false;
			Font font = Font.get("tiny");
			
			Header = new Image(filename);
			Header.setPosition(350, y-10, 600, 30);
			Header.shade(0.8f, 0.8f, 0.8f, 0.8f);
			
			Integer text_y = y;
			start_y = y;
			
			for(int i = 0; i < number; i++) {
				text_y -= 50;
				
				EntryRight[i] = new Label(font);
				EntryRight[i].text("To be confirmed");
				EntryRight[i].load(0, 0);
				EntryRight[i].setColour(1f, 1f, 0f, 1f);
				EntryRight[i].setInitialPosition(950 - EntryRight[i].getWidth(), text_y);
				
				EntryLeft[i] = new Label(font);
				EntryLeft[i].text("To be confirmed");
				EntryLeft[i].load(0, 0);
				EntryLeft[i].setColour(1f, 1f, 0f, 1f);
				EntryLeft[i].setInitialPosition(350, text_y);	
				
				Results[i] = new Label(font);
				Results[i].text(":");
				Results[i].load(650, text_y);
				Results[i].setColour(1f, 1f, 0f, 1f);
			}
		}
		
		public void ResetScores() {
			resetMatchList = true;
		}
		
		private void ResetTree() {
			resetMatchList = false;
			Float temp_y = start_y;
			for(int i = 0; i < EntryRight.length; i++) {
				temp_y -= 50;
				
				if(EntryRight[i].getText().compareToIgnoreCase("To be confirmed") != 0) {
					EntryRight[i].text("To be confirmed");
					EntryRight[i].load(0, 0);
					EntryRight[i].setColour(1f, 1f, 0f, 1f);
					EntryRight[i].setInitialPosition(950 - EntryRight[i].getWidth(), temp_y);
					
					EntryLeft[i].text("To be confirmed");
					EntryLeft[i].load(0, 0);
					EntryLeft[i].setColour(1f, 1f, 0f, 1f);
					EntryLeft[i].setInitialPosition(350, temp_y);	
					
					Results[i].text(":");
					Results[i].load(650, temp_y.intValue());
					Results[i].setColour(1f, 1f, 0f, 1f);
				}
			}
		}

		public void onEnter(ArrayList<MatchResult> matchResults) {
			if(resetMatchList) {
				ResetTree();
			}
			
			float temp = start_y;
			for(int i = 0; i < matchResults.size(); i++) {
				MatchResult result = matchResults.get(i);
				temp -= 50;
				
				if(EntryRight[i].getText() != result.teamOne) {
					EntryRight[i].text(result.teamOne);
					EntryRight[i].load(0, 0);
					EntryRight[i].setColour(1f, 1f, 0f, 1f);
					EntryRight[i].setInitialPosition(950 - EntryRight[i].getWidth(), temp);
					
					EntryLeft[i].text(result.teamTwo);
					EntryLeft[i].load(0, 0);
					EntryLeft[i].setColour(1f, 1f, 0f, 1f);
					EntryLeft[i].setInitialPosition(350, temp);	
					
					Results[i].text(String.valueOf(result.teamOneScore + " : " + result.teamTwoScore));
					Results[i].load(0, 0);
					Results[i].setInitialPosition(650 - Results[i].getWidth()/2, temp);
					Results[i].setColour(1f, 1f, 0f, 1f);
				}
			}			
		}
		
		public void Update() {	
			TotalY += y;
			
			if(TotalY >= 0.0F && TotalY <= 3300.0F) {				
				for(int i = 0; i < EntrysID.length; i++) {
					Vector2 position = EntryRight[i].getPosition();
					EntryRight[i].translate(position.getX(), position.getY() + y);
					EntryRight[i].update();
					
					position = EntryLeft[i].getPosition();
					EntryLeft[i].translate(position.getX(), position.getY() + y);
					EntryLeft[i].update();
				}
				
				for(Label text : Results) {
					Vector2 position = text.getPosition();
					text.translate(position.getX(), position.getY() + y);
					text.update();
				}
			
				Header.translate(0, y);
				Header.update();
			} else {
				if(TotalY < 100.0F) {
					TotalY = 0.0F;
				} else {
					TotalY = 3300.0F;
				}
			}
		}
		
		public void Reset() {
			Header.reset();
			TotalY = 0.0F;
			y = 0.0f;
			
			for(int i = 0; i < EntrysID.length; i++) {
				EntryRight[i].reset();
				EntryLeft[i].reset();
				Results[i].reset();
			}
		}

		@Override
		public void render() {
			Header.render();
			for(int i = 0; i < Results.length; i++) {
				EntryRight[i].render();
				EntryLeft[i].render();
				Results[i].render();
			}
		}
	}

	/** Graphic elements for the final match */
	private class MatchDay implements IRenderable {
		public Label[] entryRight = new Label[16];
		public Label[] entryLeft = new Label[16];
		public Label[] results = new Label[16];
		public String[] entrysID = new String[16];
		
		public Image background;
		public Integer items = 0;
		public Float totalY = 0.0F;
		public Float y = 0f;
		
		private Integer yStartPosition;
		private Integer yPosition;
		private Boolean resetMatchList = false;
		
		public MatchDay(IFactory factory, int y, int day, String filename) {
			this.background = new Image(filename);
			background.setPosition(350, y-10, 600, 30);
			background.shade(0.8f, 0.8f, 0.8f, 0.8f);
			yStartPosition =  (int)(y - 25.0F);
			yPosition = (int)(y - 25.0F);
		}
		
		public void onEnter() {
			if(resetMatchList) {
				resetMatchList = false;
				Reset();
				
				for(int i = 0; i < results.length; i++) {
					ResetObject(i);
				}
			}
			
			ArrayList<MatchResult> matchResults = Globals.get().getMatchResults();
			if(!matchResults.isEmpty()) {
				for(int count = 0; count < matchResults.size(); count++) {
					for(int i = 0; i < results.length; i++) {
						MatchResult matchResult = matchResults.get(count);
						
						if(matchResult.MatchResultPrimaryID.compareToIgnoreCase(entrysID[i]) == 0 && (results[i].getText().compareToIgnoreCase(":") == 0 || matchResult.Changed)) {
							Float y = results[i].getPosition().getY();
							
							results[i].text(String.valueOf(matchResult.teamOneScore + " : ") + String.valueOf(matchResult.teamTwoScore));
							results[i].load(0, 0);
							results[i].setColour(1f, 1f, 0f, 1f);
							results[i].setInitialPosition(((int)(650 - results[i].getWidth()/2)), y);
							results[i].update();
							
							matchResult.Changed = false;
							break;
						}
						
						if(matchResult.MatchResultSecondaryID.compareToIgnoreCase(entrysID[i]) == 0 && (results[i].getText().compareToIgnoreCase(":") == 0 || matchResult.Changed)){
							Float y = results[i].getPosition().getY();
							
							results[i].text(String.valueOf(matchResult.teamOneScore + " : ") + String.valueOf(matchResult.teamTwoScore));
							results[i].load(0, 0);
							results[i].setColour(1f, 1f, 0f, 1f);
							results[i].setInitialPosition(((int)(650 - results[i].getWidth()/2)), y);
							results[i].update();
							
							matchResult.Changed = false;
							break;
						}
					}
				}
			}
		}
		
		public void Reset() {
			yPosition = yStartPosition;
			background.reset();
			totalY = 0.0F;
			y = 0.0f;
			
			for(int i = 0; i < entrysID.length; i++) {
				entryRight[i].reset();
				entryLeft[i].reset();
				results[i].reset();
			}
		}
		
		public void Update() {
			totalY += y;
			
			if(totalY >= 0.0F && totalY <= 3300.0F) {				
				for(int i = 0; i < entrysID.length; i++) {
					Vector2 position = entryRight[i].getPosition();
					entryRight[i].translate(position.getX(), position.getY() + y);
					entryRight[i].update();
					
					position = entryLeft[i].getPosition();
					entryLeft[i].translate(position.getX(), position.getY() + y);
					entryLeft[i].update();
				}
				
				for(Label text : results) {
					Vector2 position = text.getPosition();
					text.translate(position.getX(), position.getY() + y);
					text.update();
				}
			
				background.translate(0, y);
				background.update();
			} else {
				if(totalY < 100.0F) {
					totalY = 0.0F;
				} else {
					totalY = 3300.0F;
				}
			}
		}
		
		public void PushResult(Match match) {
			if(items < 30) {
				yPosition -= 50;
				
				Label left = new Label(Font.get("tiny"));
				left.text(match.getTeamOne());
				left.load(350, yPosition);
				left.setColour(1f, 1f, 0f, 1f);
				
				Label right = new Label(Font.get("tiny"));
				right.text(match.getTeamTwo());
				right.load(0, 0);
				right.setColour(1f, 1f, 0f, 1f);
				right.setInitialPosition(950 - right.getWidth(), yPosition);
				
				Label score = new Label(Font.get("tiny"));
				score.text(":");
				score.load(650, yPosition);
				score.setColour(1f, 1f, 0f, 1f);
				
				entryRight[items] = right;
				entryLeft[items] = left;
				entrysID[items] = match.getTeamOne() + " vs " + match.getTeamTwo();
				results[items] = score;
				items++;
			}
		}
		
		public void ResetScores() {
			resetMatchList = true;
		}
		
		public void ResetObject(int i) {
			yPosition -= 50;
			
			if(results[i].getText().charAt(0) != ':') {
				results[i].text(":");
				results[i].load(650, yPosition);
				results[i].setColour(1F, 1F, 0F, 1F);
				results[i].update();
			}
		}

		@Override
		public void render() {
			background.render();
			for(int i = 0; i < entrysID.length; i++) {
				entryRight[i].render();
				entryLeft[i].render();
				results[i].render();
			}
		}
	}
	
	/** Background images for the scene */
	private class BackgroundImages implements IRenderable {
		private Image headerBackground;
		private Image background;
		private Integer passCount;
		
		/** The constructor for this inner class */
		public BackgroundImages(IFactory factory) {
			headerBackground = new Image("sprites/fixtures.bmp");
			headerBackground.setPosition(0, 750, 1280, 50);
			background = factory.request("SplashBackground");
			passCount= 0;
		}
		
		/** The update function to update the images */
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
}
