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

public class Label implements IRenderable {
	private OpenglText label;
	private Integer spacing;
	private String line;
	
	public Label(Font engine, String text) {
		label = new OpenglText(engine);
		line = text;
		
		spacing = 2;
	}
	
	public Label(Font engine) {
		label = new OpenglText(engine);
	}
	
	public String getText() {
		return line;
	}
	
	public OpenglText getRawGL() {
		return label;
	}
	
	public void reset() {
		label.reset();
	}
	
	public Vector2 getPosition() {
		return label.getPosition();
	}
	
	public void scale(float x, float y) {
		spacing = (int)(spacing * x);
	}

	public void setInitialPosition(float x, float y) {
		label.setInitialPosition(x, y);
	}
	
	public int getWidth() {
		return label.GetWidth();
	}
	
	public void setColour(float r, float g, float b, float a) {
		label.setColour(r, g, b, a);
	}
	
	public void translate(float x, float y) {
		label.translate(x, y);
	}
	
	public void text(String str) {
		line = str;
	}
	
	public void load(int x, int y) {
		label.load(line, x, y);
	}
	
	public void update() {
		label.update(5);
	}
	
	public void update(int spacing) {
		label.update(spacing);
	}

	public void load(int x, int y, ArrayList<Vector2> positions) {
		label.load(line, x, y, positions);
	}

	public void Load(int x, int y, ArrayList<String> strings, ArrayList<Vector2> positions) {
		label.load(x, y, strings, positions);
	}

	@Override
	public void render() {
		if(label.isVisible()) {
			label.render();
		}
	}
}
