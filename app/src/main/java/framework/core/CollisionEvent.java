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

public class CollisionEvent implements IEventListener {
	public interface CollisionArray {
		public void collisionID(Integer num);
	}
	
	private OpenglImage Object;
	private OpenglImage Obj;
	private IEvent event;
	private int Number;
	
	public CollisionEvent() {
		Number = -1;
	}
	
	public void surfaces(OpenglImage i, OpenglImage b, int num) {
		Number = num;
		Object = i;
		Obj = b;
	}
	
	public void surfaces(OpenglImage i, OpenglImage b) {
		Object = i;
		Obj = b;
	}
	
	@Override
	public void check(IEventManager manager) {
		Vector2 posTwo = Object.getPosition();
		Vector2 posOne = Obj.getPosition();
		Vector2 szTwo = Object.getSize();
		Vector2 szOne = Obj.getSize();
		
		float x2 = posTwo.getX();
		float x1 = posOne.getX();
		float w2 = (x2 + szTwo.getX());
		float w1 = (x1 + szOne.getX());
		
		if(x1 <= w2 && w1 >= x2) {
			
			float y2 = posTwo.getY();
			float h2 = (y2 + szTwo.getY());
			float y1 = posOne.getY();
			float h1 = (y1 + szOne.getY());
			
			if(y1 <= h2 && h1 >= y2) {
				if(this.event instanceof CollisionArray) {
					((CollisionArray)event).collisionID(Number);
				} 
				
				manager.triggerEvent(event, null);
			}
		} 
	}

	@Override
	public void eventType(IEvent event) {
		this.event = event;
	}
}
