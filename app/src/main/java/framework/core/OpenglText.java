package framework.core;

import static android.opengl.GLES20.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import java.util.ArrayList;


public class OpenglText implements IRenderable {
	private final String vs = "shaders/text_vs.glsl";
	private final String fs = "shaders/text_fs.glsl";
	private Integer TextLength;
	private Integer TextHeight;
	
	private float[] colour = new float[4];
		
	private OpenglBuffer PositionBuffer = new OpenglBuffer();
	private OpenglProgram Shader;
	
	private Vector2 InitialTranslate = new Vector2(0, 0);
	private Vector2 Translate = new Vector2();
	
	private framework.core.Font Font;
	private Matrix matrix = new Matrix();
	private OpenglTextureUnit Sheet;
	private Boolean Loaded = false;
	private Integer Count;
	private String string = "";
	
	public OpenglText(framework.core.Font labelEngine) {
		Shader = new OpenglProgram();
		Shader.PushShaders(vs, fs);
		
		matrix.pushIdentity();
		setColour(1, 1, 1, 1);
		Font = labelEngine;		
	}
	
	public void setInitialPosition(float x, float y) {
		this.InitialTranslate.set(x, y);
		this.Translate.set(x, y);
	}
	
	public String getString() {
		return string;
	}
	
	/**
	 * 
	 * @param string
	 * @param x
	 * @param y
	 */
	public void load(String string, int x, int y) {
		Document document = Font.getDocument();
		XmlReader parser = Font.getParser();
		this.string = string;
	
		NodeList nodeList = document.getElementsByTagName("character");
		char[] letters = string.toCharArray();
		
		Count = letters.length * 6;
		TextHeight = 0;
		TextLength = 0;
		
		float[] data = new float[24 * letters.length];
		
		int tex_x = 0;
		int tex_y = 0;
		int b = 0;
		
		Sheet = OpenglTextureManager.get().getSprite(Font.getTextureFilename());
		  
		for(int i = 0; i < letters.length; i++) {
			int Value = (int)letters[i];
			Element e = (Element)nodeList.item(Value - 32);
			
			int x2 = Integer.parseInt(parser.getValue(e, "x"));
		    int y2 = Integer.parseInt(parser.getValue(e, "y"));
		    int h2 = Integer.parseInt(parser.getValue(e, "height"));
		    int w2 = Integer.parseInt(parser.getValue(e, "width"));
		    
		    float realX = (float)x2/Sheet.width;
		    float realY = (float)y2/Sheet.height;
		    float realW = (float)(x2 + w2)/Sheet.width;
		    float realH = (float)(y2 + h2)/Sheet.height;
		    
		    data[b + 0] = tex_x;	 	data[b + 2] = realX;
		    data[b + 1] = tex_y;	 	data[b + 3] = realH;
		    data[b + 4] = tex_x;	 	data[b + 6] = realX;
		    data[b + 5] = tex_y+h2;		data[b + 7] = realY;
		    data[b + 8] = tex_x+w2;	 	data[b + 10] = realW;
		    data[b + 9] = tex_y;	 	data[b + 11] = realH;	
		    
		    data[b + 12] = tex_x+w2;	data[b + 14] = realW;
		    data[b + 13] = tex_y+h2;	data[b + 15] = realY;
		    data[b + 16] = tex_x+w2;	data[b + 18] = realW;
		    data[b + 17] = tex_y;		data[b + 19] = realH;
		    data[b + 20] = tex_x;		data[b + 22] = realX;
		    data[b + 21] = tex_y+h2;	data[b + 23] = realY;
		    
		    TextLength = (int)data[b + 12];
		    TextHeight = h2;
		    
		    tex_x += w2;		    
		    b += 24;
		}
		
		if(Loaded) {
			PositionBuffer.reinsert(data);
		} else {
			PositionBuffer.insert(data);
			Loaded = true;
		}
		
		InitialTranslate.set(x, y);
	    Translate.set(x, y); 
	}
	
	public void load(String string, int x, int y, ArrayList<Vector2> positions) {
		Document document = Font.getDocument();
		XmlReader parser = Font.getParser();
	
		NodeList nodeList = document.getElementsByTagName("character");
		char[] letters = string.toCharArray();
		
		Count = letters.length * 6;
		TextHeight = 0;
		TextLength = 0;
		
		float[] data = new float[24 * letters.length];
		
		int b = 0;
		
		Sheet = OpenglTextureManager.get().getSprite(Font.getTextureFilename());
		  
		for(int i = 0; i < letters.length; i++) {
			int Value = (int)letters[i];
			Element e = (Element)nodeList.item(Value - 32);
			
			int x2 = Integer.parseInt(parser.getValue(e, "x"));
		    int y2 = Integer.parseInt(parser.getValue(e, "y"));
		    int h2 = Integer.parseInt(parser.getValue(e, "height"));
		    int w2 = Integer.parseInt(parser.getValue(e, "width"));
		    
		    float realX = (float)x2/Sheet.width;
		    float realY = (float)y2/Sheet.height;
		    float realW = (float)(x2 + w2)/Sheet.height;
		    float realH = (float)(y2 + h2)/Sheet.height;
		    
		    Vector2 position = positions.get(i);
		   
		    float tex_x = position.getX() - (w2/2);
		    float tex_y = position.getY();
		    
		    data[b + 0] = tex_x;	 	data[b + 2] = realX;
		    data[b + 1] = tex_y;	 	data[b + 3] = realH;
		    data[b + 4] = tex_x;	 	data[b + 6] = realX;
		    data[b + 5] = tex_y+h2;		data[b + 7] = realY;
		    data[b + 8] = tex_x+w2;	 	data[b + 10] = realW;
		    data[b + 9] = tex_y;	 	data[b + 11] = realH;	
		    
		    data[b + 12] = tex_x+w2;	data[b + 14] = realW;
		    data[b + 13] = tex_y+h2;	data[b + 15] = realY;
		    data[b + 16] = tex_x+w2;	data[b + 18] = realW;
		    data[b + 17] = tex_y;		data[b + 19] = realH;
		    data[b + 20] = tex_x;		data[b + 22] = realX;
		    data[b + 21] = tex_y+h2;	data[b + 23] = realY;
		    
		    TextLength = (int)data[b + 12];
		    TextHeight = h2;
		    		    
		    b += 24;
		}
		
		if(Loaded) {
			PositionBuffer.reinsert(data);
		} else {
			PositionBuffer.insert(data);
			Loaded = true;
		}
		
		InitialTranslate.set(x, y);
	    Translate.set(x, y); 
	}
	
	public boolean isVisible() {
		if(Translate.getY() + TextHeight >= 0 && Translate.getY() <= 800) {
			return true;
		} return false;
	}

	public void setColour(float r, float g, float b, float a) {
		colour[0] = r;
		colour[1] = g;
		colour[2] = b;
		colour[3] = a;
	}
	
	
	public Vector2 getPosition() {
		return Translate;
	}
	
	public void update(int spacing) {
		this.matrix.pushIdentity();
		this.matrix.translate(Translate.getX(), Translate.getY());
	}
	
	static int ProjectionID = 0;
	static int PositionID = 0;
	static int MatrixID = 0;
	static int ColourID = 0;

	/** */
	public void translate(float x, float y) {
		Translate.set(x, y);
	}
	
	public void reset() {
		matrix.pushIdentity();
		matrix.translate(InitialTranslate.getX(), InitialTranslate.getY());
		
		Translate.set(InitialTranslate);
		
	}
	
	public Vector2 getTranslate() {
		return Translate;
	}
	
	public int GetWidth() {
		return TextLength;
	}
	
	public int GetHeight() {
		return TextHeight;
	}
	
	public void beginProgram() {
		Shader.startProgram();
	
		if(ProjectionID == 0 && PositionID == 0 && MatrixID == 0)
		{
			PositionID = Shader.getAttribute("position");		
			ProjectionID = Shader.getUniform("Projection");
			MatrixID = Shader.getUniform("ModelView");
			ColourID = Shader.getUniform("Shade");
		}
		
		glUniformMatrix4fv(ProjectionID, 1, false, matrix.getProjection(), 0);
	}
	
	public void startRender() {		
		glBindTexture(GL_TEXTURE_2D, Sheet.textureGL_ID[0]);
		
		PositionBuffer.bindBuffer();
		glVertexAttribPointer(PositionID, 4,  GL_FLOAT, false, 0, 0);
		glEnableVertexAttribArray(PositionID);
		
		glUniformMatrix4fv(MatrixID, 1, false, matrix.getModelView(), 0);
		glUniform4fv(ColourID, 1, colour, 0);
		glDrawArrays(GL_TRIANGLES, 0, Count);
	}
	
	public void endProgram() {
		glDisableVertexAttribArray(PositionID);
		Shader.endProgram();
	}

	public void load(int x, int y, ArrayList<String> strings, ArrayList<Vector2> positions) {
		Document document = Font.getDocument();
		XmlReader parser = Font.getParser();
	
		NodeList nodeList = document.getElementsByTagName("character");
	
		Integer stringLength = 0;
		for(int i = 0; i < strings.size(); i++) {
			stringLength += strings.get(i).length();
		}
		
		float[] data = new float[24 * stringLength];
		Count = stringLength * 6;

		TextHeight = 0;
		TextLength = 0;
		
		Sheet = OpenglTextureManager.get().getSprite(Font.getTextureFilename());
	    int b = 0;
		for(int i = 0; i < strings.size(); i++) {
			char[] letters = strings.get(i).toCharArray();			
			
			Integer width = 0;
			for(int z = 0; z < letters.length; z++) {
				Element e = (Element)nodeList.item((int)letters[z] - 32);
			    int w2 = Integer.parseInt(parser.getValue(e, "width"));
			    width += w2;			   
			}

			  
		    Vector2 position = positions.get(i);
		
		    float tex_x = position.getX() - (width/2);
		    float tex_y = position.getY();
			    
			for(int z = 0; z < letters.length; z++) {
				Element e = (Element)nodeList.item((int)letters[z] - 32);
				
				int x2 = Integer.parseInt(parser.getValue(e, "x"));
			    int y2 = Integer.parseInt(parser.getValue(e, "y"));
			    int h2 = Integer.parseInt(parser.getValue(e, "height"));
			    int w2 = Integer.parseInt(parser.getValue(e, "width"));
			    		    
			    float realX = (float)x2/Sheet.width;
			    float realY = (float)y2/Sheet.height;
			    float realW = (float)(x2 + w2)/Sheet.width;
			    float realH = (float)(y2 + h2)/Sheet.height;			  
			    
			    data[b + 0] = tex_x;	 	data[b + 2] = realX;
			    data[b + 1] = tex_y;	 	data[b + 3] = realH;
			    data[b + 4] = tex_x;	 	data[b + 6] = realX;
			    data[b + 5] = tex_y+h2;		data[b + 7] = realY;
			    data[b + 8] = tex_x+w2;	 	data[b + 10] = realW;
			    data[b + 9] = tex_y;	 	data[b + 11] = realH;	
			    
			    data[b + 12] = tex_x+w2;	data[b + 14] = realW;
			    data[b + 13] = tex_y+h2;	data[b + 15] = realY;
			    data[b + 16] = tex_x+w2;	data[b + 18] = realW;
			    data[b + 17] = tex_y;		data[b + 19] = realH;
			    data[b + 20] = tex_x;		data[b + 22] = realX;
			    data[b + 21] = tex_y+h2;	data[b + 23] = realY;
			    
			    TextLength = (int)data[b + 12];
			    TextHeight = h2;
			    		
			    tex_x += w2;
			    b += 24;
			}
		}
		
		if(Loaded) {
			PositionBuffer.reinsert(data);
		} else {
			PositionBuffer.insert(data);
			Loaded = true;
		}
		
		InitialTranslate.set(x, y);
	    Translate.set(x, y); 
	}

	@Override
	public void render() {
		if(isVisible()) {
			beginProgram();
			startRender();
			endProgram();
		}		
	}
}
