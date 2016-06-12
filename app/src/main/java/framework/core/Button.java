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

/**
 * The object represents a button that the user
 * can press. It has both an image component and
 * a label or text component.
 *
 * @author William Taylor
 */
public class Button implements IRenderable {
	/** The position of the button in 2D space **/
	private Vector2 position;
	/** The size of the button in 2D space **/
	private Vector2 size;
	/** The buttons sprite or texture **/
	private OpenglImage sprite;
	/** The text for the button **/
	private OpenglText text;
	/** A boolean to tell if the button has a texture **/
	private Boolean spriteEnabled = false;
	/** A boolean to tell if the button has a label**/
	private Boolean textEnabled = false;
	/** Has the text been loaded **/
	private Boolean loadText = false;
	/** The path to the loaded label font file **/
	private String labelFilename;
	
	/**
	 * Our basic constructor
	 * @param e The font to use in the button
	 */
	public Button(Font e) {
		sprite = new OpenglImage();
		text = new OpenglText(e);
		loadText = false;
	}
	
	/**
	 * Constructor for setting up a button
	 * with a single texture
	 */
	public Button() {
		sprite = new OpenglImage();
	}
	
	/**
	 * This sets the texture for the background
	 * @param str
	 */
	public void setTexture(String str) {
		if(spriteEnabled) {
			sprite.load(str, str);
		}
	}
	
	/**
	 * This function loads the text
	 * 
	 * @param string The text the label should display
	 * @param x the x position
	 * @param y the y position
	 * @param w the width of the label
	 * @param h the height of the label
	 */
	public void setText(String string, float x, float y, float w, float h) {
		// disable the input and start loading the text
		GameObject.disableInput();
		text.load(string, 0, 0);
		
		// then calculate the original position
		Float halfX = (float)(text.GetWidth()/2);
		text.setInitialPosition(x - halfX, y);
		text.update(0);
		
		// if the sprite isnt enabled set its position to the texts
		if(!spriteEnabled) {
			// this is so when we return the position of the button we return the position of the text instead
			sprite.setPosition(x - halfX, y, w + halfX, h);
		}
		
		// re enable the input & enabled the text
		GameObject.enableInput();
		textEnabled = true;
	}
	
	/**
	 * Sets the text for the button and its 
	 * position.
	 * 
	 * @param string The text to display
	 * @param x its x position
	 * @param y its y position
	 */
	public void setText(String string, float x, float y) {
		// load the texture and set the initial position
		text.load(string, 0, 0);	
		text.setInitialPosition(x - text.GetWidth()/2, y);
		text.update(0);	
	
		// then say the text is enabled so its rendered on screen
		textEnabled = true;
	}
	
	/**
	 * Sets the texture to be rendered as the buttons
	 * background or if the button is just a texture
	 * with the text pre applied.
	 * 
	 * @param string The filename for the texture
	 * @param x the x position for the button
	 * @param y the y position for the button
	 * @param w the width of the button in 2D space
	 * @param h the height of the button in 2D space
	 */
	public void setSprite(String string, float x, float y, float w, float h) {
		// we create the position and size objects
		position = new Vector2(x, y);
		size = new Vector2(w, h);
		// then load the sprite and set its position
		sprite.load(string, string);
		sprite.setPosition(x, y, w, h);
		// and enable the sprite so it can be rendered 
		spriteEnabled = true;
	}
	
	/**
	 * The following takes a text element and adds it to 
	 * the button
	 * @param e the text to add to the button
	 */
	public void pushText(OpenglText e) {
		this.text = e;
	}
	
	/**
	 * This function sets the texts colour when its 
	 * rendered on screen
	 * 
	 * @param r 0.0f - 1.0f The intensity of the red colour component
	 * @param g 0.0f - 1.0f The intensity of the green colour component
	 * @param b 0.0f - 1.0f The intensity of the blue colour component
	 * @param a 0.0f - 1.0f The intensity of the alpha colour component
	 */
	public void setTextColour(float r, float g, float b, float a) {
		// first check to make sure we have a text object
		if(text != null) {
			// if so call its set colour method
			text.setColour(r, g, b, a);
		}
	}
	
	/**
	 * @return returns the position of the button in 2D space
	 */
	public Vector2 getPosition() {
		return sprite.getPosition();
	}
	
	/**
	 * The following sets a new label to be loading
	 * on a call to the update function/
	 * 
	 * @param str The word to be set as the buttons text
	 */
	public void addText(String str) {
        labelFilename = str;
		loadText = true;
	}
	
	/**
	 * Resets both the button image and text
	 * if they were transformed in 2D space
	 */
	public void reset() {
		sprite.reset();
		text.reset();
	}
	
	/**
	 * This function translates the button by the given amount
	 * 
	 * @param x the x translate value
	 * @param y the y translate value
	 */
	public void translate(float x, float y) {
		Vector2 vec = text.getTranslate();
		
		if(spriteEnabled) {
			sprite.translate(x, y);
		} 
		
		if(textEnabled) {
			text.translate(vec.getX() + x, vec.getY() + y);
		}
	}
	
	/**
	 * This function hides the text when it is drawn
	 */
	public void hideText() {
		textEnabled = false;
	}

	/**
	 * updates the object in 2D space event if the text for the button 
	 * has been changed
	 */
	public void update() {
		// if we are meant to reload the text
		if(loadText) {
			// then enable the text object
			textEnabled = true;
			loadText = false;
			
			// then load the new label
			text.load(labelFilename, 0, 0);
			text.setColour(0, 0, 0, 1);
			
			// calculate the place in 2D space it should be centered at
			float tx = sprite.getPosition().getX() + sprite.getSize().getX()/2 - text.GetWidth()/2;
			float ty = sprite.getPosition().getY() + sprite.getSize().getY()/2 - text.GetHeight()/2;
			float x = position.getX() + size.getX()/2 - text.GetWidth()/2;
			float y = position.getY() + size.getY()/2 - text.GetHeight()/2;
			
			// then set its initial position & translate it to its actual position
			text.setInitialPosition(x, y);	
			text.translate(tx, ty);	
			
		} 
			
		// then update the sprite if there is one
		if(spriteEnabled) {
			sprite.update(0);
		} 
			
		
		// then do the same for the text
		if(textEnabled) {
			text.update(0);
		}
	}
	
	/**
	 * returns the size of the button in 2D space
	 */
	public Vector2 getSize() {
		return(sprite.getSize());
	}

	/**
	 * Returns the filename for the texture
	 */
	public String getFilename() {
		return sprite.getFilename();
	}

	/**
	 * This function returns a reference to the
	 * BAF_GL_Image object which is used to draw
	 * the buttons background
	 */
	public OpenglImage getImage() {
		// if the sprite hasnt been loaded
		if(!spriteEnabled) {
			// then return null
			return null;
		} else {
			// else return the object
			return sprite;
		}
	}
	
	/**
	 * This function returns a reference to the
	 * BAF_GL_Text object used for drawing the 
	 * buttons text
	 */
	public OpenglText getText() {
		// if the text isnt enabled (meaning it hasnt been loaded)
		if(!textEnabled) {
			// return null to indicate failure
			return null;
		} else {
			// return the text object
			return this.text;
		}
	}

	/**
	 * The following renders the button object by first
	 * rendering the background texture then the text
	 * inside.
	 */
	@Override
	public void render() {
		// if the texture is enabled and visible in the scene
		if(spriteEnabled && sprite.isVisible()) {
			// render the txture
			sprite.render();
		} 
		
		// if the text is enabled and visible
		if(textEnabled && text.isVisible()) {
			// render the text
			text.render();
		}
	}
}
