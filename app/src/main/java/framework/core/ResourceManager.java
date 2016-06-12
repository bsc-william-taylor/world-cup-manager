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

import java.io.InputStream;
import java.util.Scanner;

import android.content.res.AssetManager;
import android.content.Context;

/**
 * This is a resource manager that should be used
 * to grab files in the assets folder.
 * 
 * @version 08/02/2014
 * @author William Taylor
 */
public class ResourceManager {
	/** The singleton's instance*/
	private static ResourceManager Instance;
	/** The game context. */
	private static Context GameContext;
	
	/**
	 * Static function for access the singleton
	 * @return the singletons instance.
	 */
	public static ResourceManager Get() {
		if(Instance == null) {
			Instance = new ResourceManager();
		} return Instance;
	}
	
	/**
	 * A function that returns a certain resource.
	 * @param filename the location.
	 * @return the file as a inputstream
	 */
	public InputStream GetResource(String filename) {
		AssetManager manager = GameContext.getAssets();
		InputStream stream = null;
		try {
			stream = manager.open(filename);
		} catch (Exception e) {
			System.out.println(e.toString());
		} return stream;
	}
	
	/**
	 * A function that returns a file that will 
	 * allow the user to grab data line by line.
	 * 
	 * @param filename the location of the file.
	 * @return the file as a scanner.
	 */
	public Scanner GetFile(String filename) {
		Scanner scanner = null;
		try {
			scanner =  new Scanner(GetResource(filename));
		}  catch (Exception e) {
			System.out.println(e.toString());
		} return scanner;
	}
	
	/**
	 * Initialises the resourse manager.
	 * @param c the applications context
	 */
	public void Initialise(Context c) {
		GameContext = c;
	}
	
	/**
	 * a get function to get the android apps context
	 * @return the apps contect.
	 */
	public Context GetContext() {
		return GameContext;
	}
}
