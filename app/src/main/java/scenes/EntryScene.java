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
import framework.core.*;

/**
 * This is the entry scene which just
 * posts Tap to continue
 * then continues to the next scene
 * 
 * @version : final version for release
 * @author : William Taylor
 */
public class EntryScene extends Scene {
	/** Some settings for the entry scene */
	private static final Integer TEXT_X = 525;
	private static final Integer TEXT_Y = 200;
	
	/** The elements that needed to be drawn */
	private Image Background;
	private Label Version;
	private Label Header;
	private Label Text;
	
	/**
	 * The onCreate function simply setups the text elements
	 * and gets the other elements from the asset factory
	 */
	@Override
	public void onCreate(IFactory factory) {
		/** set up the Tap to continue text element */
		Text = new Label(Font.get("small"), "Tap to continue");
		Text.load(TEXT_X, TEXT_Y);
		Text.setColour(1f, 1f, 0f, 1f);
		Text.update();
		
		Background = factory.request("SplashBackground");
		Version = factory.request("Version");
		Header = factory.request("Header");
	}
	
	/** Your average update function */
	@Override
	public void onUpdate() {
		Background.update();
		Version.update();
		Header.update();
		Text.update();
	}
	
	/** Your average render function that pushes all renderable
	 * objects to the renderList
	 */
	@Override
	public void onRender(RenderQueue renderList) {
		renderList.put(Background);
		renderList.put(Version);
		renderList.put(Header);
		renderList.put(Text);
	}
	
	/** 
	 * The touch event handler
	 */
	@Override
	public void onTouch(MotionEvent e, int x, int y) {
		// simply go to the main menu when the screen is tapped.
		if(e.getAction() == MotionEvent.ACTION_DOWN) {
			SceneManager.get().switchTo(MainActivity.Scenes.MAIN_MENU);
		}
	}
}
