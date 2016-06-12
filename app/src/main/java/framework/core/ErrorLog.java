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
import android.util.Log;

/**
 * A simple error logger isnt widely used but
 * is used for debugging shaders.
 * 
 * @version 08/02/2014
 * @author William Taylor
 */
public class ErrorLog {
	/** A vector to contain all error logs */
	private ArrayList<String> outputFile;
	/** A static instance as this is a singleton */
	private static ErrorLog Instance;

	/**
	 * Basic Constructor that is used by the get
	 * method when the object is requested.
	 */
	private ErrorLog() {
		outputFile = new ArrayList<String>();
		outputFile.add("- V1.0");
	}
	
	/**
	 * A Simple get function as thi class is a singleton
	 * @return The singletons instance
	 */
	public static ErrorLog get() {
		if(Instance == null) {
			Instance = new ErrorLog();
		} return Instance;
	}
	
	/**
	 * A simple print function that iterates through
	 * the vector and prints each message accordingly.
	 */
	public void print() {
		for(int i = 0; i < outputFile.size(); i++) {
			if(i == 0) {
				Log.e("Error Logger", outputFile.get(i));
			} else {
				Log.e("Error " + i, outputFile.get(i));
			}
		}
	}
	
	/**
	 * A simple write function that pushes 
	 * the string into the vector.
	 * 
	 * @param data The error to be written
	 */
	public void write(String data) {
		outputFile.add(data);
	}
}
