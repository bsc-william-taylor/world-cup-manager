package framework.core;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView.Renderer;

/**
 * Simple renderer class that handles all graphic
 * events and clears the screen when needed and
 * calls scene function when certain messages 
 * are recieved.
 * 
 * @version 28/01/2014
 * @author William Taylor
 */
public class OpenglRenderer implements Renderer {
	private EventManager eventManager;
	private SceneManager sceneManager;
	private RenderQueue renderQueue;
	
	/**
	 * Basic Constructor
	 */
	public OpenglRenderer() {
		eventManager = EventManager.get();
		sceneManager = SceneManager.get();
		renderQueue = new RenderQueue();
	}
	
	/**
	 *	Starts the game and get all info from the android
	 *	activity.
	 *
	 * @param gl Deprecated part of opengl es 1
	 */
	@Override
	public void onDrawFrame(GL10 arg0) {		
		GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
		
		eventManager.update();
		
		sceneManager.change();
		sceneManager.update(renderQueue);		
		
		renderQueue.renderObjects();
	}
	
	/**
	 *	Initialises gl features that will be needed activity.
	 *
	 * @param config relevant surface info not needed.
	 * @param gl Deprecated part of opengl es 1
	 */
	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {			
		GLES20.glEnable(GLES20.GL_TEXTURE_2D);	
		GLES20.glEnable(GLES20.GL_BLEND);
		GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
		GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		
		OpenglTextureManager.get().clear();
		OpenglShaderManager.get().clear();
	}
	
	/**
	 *	Handles if the surface changes in anyway.
	 *
	 * @param gl Deprecated part of opengl es 1
	 * @param height New surface heght
	 * @param width New surface width
	 */
	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		GLES20.glViewport(0, 0, width, height);
	}
}
