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
 * This is hardly a class that does much. It just hides
 * the GL_Image functionality. This class also forms
 * the base to a greater sprite library that will be devoloped
 * when needed.
 * 
 * @version 08/02/2014
 * @author William Taylor
 */
public class Image implements IRenderable {
	/**	The sprite image current implementation used opengl es 2.0 */
	private OpenglImage Sprite;
	private String filename;
	
	/**
	 * A simple Constructor that loads the image.
	 * @param ID Filename location.
	 */
	public Image(String ID) {
		this.filename = ID;
		Sprite = new OpenglImage();
		Sprite.load(ID, ID);
	}
	
	/**
	 * A method that allows people that know how the GL_Image
	 * class words the opertunity to access it directly.
	 * 
	 * @return This function returns the raw class 
	 * 		   that is used for operations.
	 */
	public Object getRawObject() {
		return this.Sprite;
	}
	
	/**	Basic set position functioin */
	public void setPosition(int x, int y, int w, int h) {
		Sprite.setPosition(x, y, w, h);
	}
	
	public String getFilename() {
		return filename;
	}
	
	public Vector2 getPosition() {
		return(Sprite.getPosition());
	}
	
	public Vector2 getSize() {
		return(Sprite.getSize());
	}

	/** Basic Update Function	*/
	public void update() {
		Sprite.update(0);
	}
	
	public void setTexture(String str) {
		Sprite.load(str, str);
		this.filename = str;
	}
	
	public void reset() {
		Sprite.reset();
	}
	
	/**	Set the textures shade */
	public void shade(float r, float g, float b, float a) {
		Sprite.setShade(r, g, b, a);
	}
	
	/**							*/
	public void translate(float x, float y) {
		Sprite.translate(x, y);
	}

	/** Basic Draw function	*/
	public void draw() {
		if(Sprite.isVisible()) {
			Sprite.render();
		}
	}

	public void reload() {
		
	}

	@Override
	public void render() {
		Sprite.render();
	}
}
