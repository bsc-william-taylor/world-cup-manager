package framework.core;

import java.util.*;

/**
 * 
 * @author William
 *
 */
public class Paragraph implements IRenderable {
	private static final int RESERVED_ARRAY_MEMORY = 15;
	/**	 */
	private ArrayList<Label> Labels = new ArrayList<Label>(RESERVED_ARRAY_MEMORY);
	/**	 */
	private ArrayList<String> Lines = new ArrayList<String>(RESERVED_ARRAY_MEMORY);
	/** */
	private Font Engine;
	/** */
	private TextFile File;
	
	/**
	 * 
	 * @param e
	 * @param filename
	 */
	public Paragraph(Font e, String filename) {
		File = new TextFile(filename);
		Lines = File.GetLines();
		Engine = e;
	}
	
	/**
	 * 
	 * @param objects
	 */
	public void add(ArrayList<Object> objects) {
		for(Label l : Labels) {
			objects.add(l);
		}
	}
	
	/**
	 * 
	 * @param r
	 * @param g
	 * @param b
	 * @param a
	 */
	public void setColour(float r, float g, float b, float a) {
		for(Label l : Labels) {
			l.setColour(r, g, b, a);
		}
	}
	 
	/**
	 * 
	 * @param x
	 * @param y
	 */
	public void load(int x, int y) {
		int NewY = y;
		for(String line : Lines) {
			Label label = new Label(Engine, line);
			label.load(x, NewY);
			NewY += label.getRawGL().GetHeight();
			Labels.add(label);
		}
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 */
	public void scale(float x, float y) {
		for(framework.core.Label Label : Labels) {
			Label.scale(x, y);
		}
	}
	
	/**
	 * 
	 */
	public void update() {
		for(framework.core.Label Label : Labels) {
			Label.update();
		}
	}

	/**
	 * 
	 * @return
	 */
	public int getLineNumber() {
		return Labels.size();
	}
	
	/**
	 * 
	 * @param i
	 * @return
	 */
	public Label getLine(int i) {
		return Labels.get(i);
	}

	/**
	 * 
	 */
	@Override
	public void render() {
		for(framework.core.Label Label : Labels) {
			Label.render();
		}
	}
}
