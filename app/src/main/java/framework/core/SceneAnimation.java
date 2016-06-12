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

import java.util.*;

/**
 * 
 * @author William
 *
 */
public class SceneAnimation {
	/** **/
	private ArrayList<Button> buttons = new ArrayList<Button>();
	/** **/
	private ArrayList<Image> images = new ArrayList<Image>();
	/** **/
	private ArrayList<Label> labels = new ArrayList<Label>();
	/** **/
	private ArrayList<OpenglLine> lines  = new ArrayList<OpenglLine>();
	/** **/
	private Integer stateID = 0;
	/** **/
	private Vector2 velocity;
	/** **/
	private Boolean start;
	
	/**
	 * 
	 */
	public SceneAnimation() {
		start = false;
	}
	
	/**
	 * 
	 * @param i
	 */
	public SceneAnimation(int i) {
		stateID = i;
		start = false;
	}
	
	/**
	 * 
	 * @return
	 */
	public Boolean isRunning() {
		return start;
	}
	
	/**
	 * 
	 * @param i
	 */
	public void setState(int i) {
		stateID = i;
		start = false;
	}
	
	/**
	 * 
	 * @param sprites
	 */
	public void setupAnimation(ArrayList<Object> sprites) {
		for(Object obj : sprites) {
			if(obj instanceof Image) {
				OpenglImage img = (OpenglImage)((Image)obj).getRawObject();
				
				if(img.isVisible()) {
					images.add((Image)obj);
				}
				
			} else if(obj instanceof Button) {
				OpenglImage img = (OpenglImage)((Button)obj).getImage();
				
				if(img.isVisible()) {
					buttons.add((Button)obj);
				}
				
			} else if(obj instanceof Label) {
				if(((OpenglText)((Label)obj).getRawGL()).isVisible()) {
					labels.add((Label)obj);
				}
			} else if(obj instanceof OpenglLine) {
				lines.add((OpenglLine)obj);
			}
		} 
	}
	
	/**
	 * 
	 * @param vector
	 */
	public void setVelocity(Vector2 vector) {
		this.velocity = vector;
	}
	
	/**
	 * 
	 */
	public void update() {
		if(start) {
			Integer elementsToMove = 0;
			Integer elementsMoved = 0;	
			
			if(velocity.getX() > 0) {
				for(Image sprite : images) {
					sprite.translate(velocity.getX(), velocity.getY());
					if(sprite.getPosition().getX() > 1280) {
						++elementsMoved;
					}
				}
				
				for(OpenglLine line : lines) {
					line.translate(velocity.getX(), velocity.getY());
					++elementsMoved;
				}
		
				for(Button button : buttons) {
					button.translate(velocity.getX(), velocity.getY());
					if(button.getPosition().getX() > 1280) {
						++elementsMoved;
					}
				}
				
				for(Label label : labels) {
					label.translate(label.getPosition().getX() + velocity.getX(), label.getPosition().getY() + velocity.getY());
					if(label.getPosition().getX() > 1280) {
						++elementsMoved;
					}
				}
			} else {
				for(Image sprite : images) {
					sprite.translate(velocity.getX(), velocity.getY());
					if(sprite.getPosition().getX() + sprite.getSize().getX() < -100) {
						++elementsMoved;
					}
				}
				
				for(OpenglLine line : lines) {
					line.translate(velocity.getX(), velocity.getY());
					++elementsMoved;
				}
		
				for(Button button : buttons) {
					button.translate(velocity.getX(), velocity.getY());
					if(button.getPosition().getX() + button.getSize().getX() < -100) {
						++elementsMoved;
					}
				}
				
				for(Label label : labels) {
					label.translate(label.getPosition().getX() + velocity.getX(), label.getPosition().getY() + velocity.getY());
					if(label.getPosition().getX() + label.getWidth() < -100) {
						++elementsMoved;
					}
				}
			}
			
			elementsToMove += lines.size();			
			elementsToMove += buttons.size();
			elementsToMove += images.size();
			elementsToMove += labels.size();

			if(elementsMoved >= elementsToMove) {
				new Timer().schedule(new TimerTask() {
					@Override
					public void run() {
						for(Image sprite : images) {
							sprite.reset();
						}
						
						for(Button button : buttons) {
							button.reset();
						}
					
						for(Label label : labels) {
							label.reset();
						}
						
						for(OpenglLine line : lines) {
							line.reset();
						}
						
						buttons.clear();
						images.clear();
						labels.clear();
						lines.clear();
						
						velocity.set(0f, 0f);
						start = false;
					}
					
				}, 33);
				
				SceneManager.get().switchTo(stateID);
				GameObject.enableInput();
			}
		}
	}
	
	/**
	 * 
	 */
	public void beginAnimation() {
		GameObject.disableInput();
		if(velocity == null) {
			velocity = new Vector2(10, 0);
		} start = true;
	}
}
