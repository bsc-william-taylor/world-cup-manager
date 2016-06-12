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
package framework.core;


import android.view.MotionEvent;

/**
 * A abstract class that sets out a layout for a collection 
 * of scene objects to make a scenes code simpler and
 * interaction with a scenes objects.
 * 
 * @version 08/02/2014
 * @author William Taylor
 */
public abstract class SceneList {
	/**
	 * A simple onTouch function that will send
	 * the relevant data to the scenelist.
	 * 
	 * @param e The motion event class provided by android
	 * @param x The x position of the touch (Relative to the screen size)
	 * @param y The y position of the touch (Relative to the screen size)
	 */
	public void onTouch(MotionEvent e, int x, int y) {}	
	
	/**
	 * A function that should be called to initialise the scene object
	 * @param factory Represents a factory that holds all game objects.
	 */
	public abstract void initialise(IFactory factory);
	
	/** a function that should be called by the scene when its entered */
	public void onEnter() {}
	
	/** a function that should be called by the scene when its exited */
	public void onExit() {}
	
	/**	a function that should be called when the scene is updated	*/
	public abstract void update();
}
