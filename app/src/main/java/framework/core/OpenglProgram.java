package framework.core;

import static android.opengl.GLES20.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.util.Log;

/**
 * 
 * @author B0023_000
 *
 */
public class OpenglProgram {
	/**	*/
	private Integer VertexShader;
	private Integer FragShader;
	/**	*/
	private Integer Program;
	
	/** */
	private String VertexFilename;
	private String FragFilename;
	
	/**
	 * 
	 */
	public OpenglProgram() {
		VertexShader = glCreateShader(GL_VERTEX_SHADER);
		FragShader = glCreateShader(GL_FRAGMENT_SHADER);
		Program = glCreateProgram();
	}
	
	/**
	 * 
	 * @param VertexID
	 * @param FragID
	 */
	public void PushShaders(String VertexID, String FragID) {	
		VertexFilename = VertexID;
		FragFilename = FragID;
		
		glShaderSource(VertexShader, loadShaderSource(VertexID));
		glShaderSource(FragShader, loadShaderSource(FragID));
		
		glCompileShader(VertexShader);		
		glCompileShader(FragShader);
		
		debugInfo(VertexShader);
		debugInfo(FragShader);
		
		glAttachShader(Program, VertexShader);
		glAttachShader(Program, FragShader);
		glLinkProgram(Program);
	}
	
	/**
	 * 
	 * @param shader
	 */
	private void debugInfo(int shader) {
		int[] compiled = new int[1];
        glGetShaderiv(shader, GL_COMPILE_STATUS, compiled, 0);
 
        if(compiled[0] == 0) {
            Log.e("Shader Error", glGetShaderInfoLog(shader));
        }
	}
	
	/**
	 * 
	 * @param filename
	 * @return
	 */
	private String loadShaderSource(String filename) {
		InputStream inputStream = ResourceManager.get().getResource(filename);
		InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

		StringBuilder stringBuilder = new StringBuilder();
		String string;

		try	{
			while((string = bufferedReader.readLine()) != null)	{
				stringBuilder.append(string);
				stringBuilder.append('\n');
			}
		}
		catch(IOException e) {
			return null;
		}

		return stringBuilder.toString();
	}
	
	/**	*/
	public int getUniform(String name) {
		return glGetUniformLocation(Program, name);
	}
	
	/**	*/
	public int getAttribute(String name) {
		return glGetAttribLocation(Program, name);
	}
	
	/**	*/
	public void startProgram() {
		glUseProgram(Program);
	}
	
	/**	*/
	public void endProgram() {
		glUseProgram(0);
	}
	
	public String getVertexFilename() {
		return VertexFilename;
	}
	

	public String getFragFilename() {
		return FragFilename;
	}
	
}