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
 * A class that represents the default layout for 
 * a game scene. It is an abstract class just so 
 * each game scene doesnt need to implement every
 * function as sometimes its not required.
 * 
 * @version 08/02/2014
 * @author William Taylor
 */
public class SceneManager {
	/** The singletons instance  */
	private static SceneManager instance;
	/** A collection to store all scenes */
	private ArrayList<Scene> scenes;
	/** The first scene which is a loading scene */
	private ISceneLoader first;
	/** Game factory which holds each object is maintained by this class */
	private IFactory factory;
	/** Just a integer to the current scene in the vector */
	private Integer current;
	/** A simple boolean to test if the game has loaded */
	private Boolean loaded;
    /** The next scene to switch to */
    private int nextScene;
	
	/**
	 * your typical get function for a singleton.
	 * @return the singletons instance.
	 adt*/
	public static SceneManager get() {
		if(instance == null) {
            instance = new SceneManager();
		} return instance;
	}
	
	/**
	 * private default constructor as this class is a instance.
	 */
	private SceneManager() {
		scenes = new ArrayList<Scene>();
		factory = new Factory();
		loaded = false;

        nextScene = 0;
		current = 0;
	}
	
	/**
	 * This function simple returns the current state
	 * however if the game hasnt loaded it goes straight
	 * to the loading state and draws what the loading state
	 * asks.
	 * 
	 * @return gets the current state.
	 */
	public Scene getCurrent() {
        // First we see if we have an initial loading scene
		if(this.first != null) {
            // if we do and it hasnt been loaded
			if(!first.hasLoaded()) {
                // then we load the assets needed for the first scene
				((Scene)first).onEnter(0);
                // then return it so it can be rendered
				return (Scene)first;

            // once initial assets have been loaded and the animation has been played
			} else if(first.hasLoaded() && !loaded) {
                // load all the other assets the game will use
				((Scene)first).onCreate(factory);
			} 
		} 

        // Then after shared assets have been loaded
		if(!loaded) {
            // go through the array and create all scenes
			for(Scene s : scenes) {
				s.onCreate(factory);
			} loaded = true;
            // then call the onEnter method
            scenes.get(0).onEnter(0);
		}

        // The return the scene specified by the programmer
		if(current < scenes.size()) {
			return scenes.get(current);
		} else {
            // Return null if current is out of bounds
            return null;
        }
	}

    /**
     * This function returns the loading scene
     * @return The first scene / loading scene
     */
	public Scene getFirst() {
		return (Scene)first;
	}
	
	/**
	 * Creates a scene and stores it in the array
	 * @param scene
	 */
	public void createScene(Scene scene) {
		if(scene instanceof ISceneLoader) {
			first = (ISceneLoader)scene;
		} else {
			scenes.add(scene);
		}
	}
	
	/**
	 * Switches the current state to the new one
	 * stored in the vector at point i.
	 * @param i the scene to go to
	 */
	public void switchTo(int i) {
		nextScene = i;
	}

    /**
     * This function sets the starting point for the application.
     *
     * @param i The location to start from in the array
     */
	public void startFrom(int i) {
		nextScene = i;
		current = i;
	}

    /**
     * @return returns the current location in the array
     */
	public Integer getLocation() {
		return current;
	}

    /**
     * @return returns the factory which the scene manager contains
     */
	public IFactory getFactory() {
		return factory;
	}

    /**
     * @return return a boolean to indicate is scenes have been loaded
     */
	public boolean hasLoaded() {
		return loaded;
	}

    /**
     * This function returns a scene for the user
     * @param sceneID The scene to return
     * @return The scene at scenes[sceneID]
     */
	public Scene getScene(Integer sceneID) {
		if(sceneID < scenes.size()) {
			return scenes.get(sceneID);
		}
		// return null if sceneID is out of range
		return null;
	}

    /**
     * changes the current scene to the one specified
     * in nextScene.
     */
	public void change() {
		if(current != nextScene) {
			Integer previous = current;
			scenes.get(current).onExit(nextScene);
			current = nextScene;
			scenes.get(current).onEnter(previous);
		}
	}

    /**
     * resets the singleton to its original state
     */
	public void clear() {
		factory = new Factory();
		scenes.clear();
		loaded = false;
		current = 0;
	}

    /**
     * @return a boolean to state if the array of scenes is empty
     */
	public boolean isEmpty() {
		return scenes.isEmpty();
	}

    /**
     * This function updates and calls all scene methods required.
     *
     * @param renderQueue The renderer queue of renderable objects
     */
	public void update(RenderQueue renderQueue) {
        // Get the current scene
		Scene currentScene = getCurrent();

        // if there is one
		if(currentScene != null) {
            // update it
			currentScene.onUpdate();

            // then do background work
			for(Scene scene : scenes) {
				scene.inBackground();
			}

            // then render the scene
			currentScene.onRender(renderQueue);
		}
	}
}
