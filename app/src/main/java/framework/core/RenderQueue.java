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

import java.util.ArrayList;

/**
 * The following object handles the list of objects
 * the framework needs to render. This following is
 * a simple implementation and should be updated
 * later.
 * 
 * @author William Taylor
 */
public class RenderQueue {
	/** The amount of elements to reserve in the array list */
	private static final int RESERVE_SIZE = 100;
	/** The array of all the objects to render */
	private ArrayList<IRenderable> renderables;
	
	/**
	 * Basic Constructor sets up the renderables array
	 */
	public RenderQueue() {
		renderables = new ArrayList<IRenderable>(RESERVE_SIZE);
	}
	
	/**
	 * This function adds a valid object to the renderables 
	 * array
	 * 
	 * @param renderable The object to add to the array
	 */
	public void put(IRenderable renderable) {
		// make sure the object isnt null then add
		if(renderable != null) {
			renderables.add(renderable);
		}
	}
	
	/**
	 * This following renders all the objects
	 * in the array then resets
	 */
	public void renderObjects() {
		for(IRenderable object : renderables) {
			object.render();
		}
		
		renderables.clear();
	}
}