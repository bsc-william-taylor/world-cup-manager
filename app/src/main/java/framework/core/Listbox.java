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

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface.OnClickListener;

public class Listbox implements Runnable {
	/** */
	private OnClickListener event;
	/** */
	private String[] args;
	/** */
	private String Title;
	/** */
	private Boolean cancel = false;
	
	/**
	 * 
	 * @param event
	 */
	public Listbox(OnClickListener event) {
		this.event = event;
		Title = "ListBox";
		
		args = new String[12];
		args[0] = "Cancel";
		
		for(int i = 0; i < 11; i++) {
			args[i+1] =  String.valueOf(i);
		}
	}
	
	/**
	 * 
	 * @param params
	 */
	public Listbox(String[] params) {
		Title = "ListBox";
		args = params;
	}
	
	/**
	 * 
	 */
	public void Show() {
		GameObject.Activity.runOnUiThread(this);
	}
	
	/**
	 * 
	 * @param title
	 * @return
	 */
	public Listbox setTitle(String title) {
		this.Title = title;
		return this;
	}

	/**
	 * 
	 */
	@Override
	public void run() {
		if(!cancel) {
			AlertDialog.Builder builder = new AlertDialog.Builder(GameObject.Activity);
			
			builder.setCancelable(false);
			builder.setItems(args, event).setTitle(Title);
			
			Dialog box = builder.create();
	
			box.show();
		}
	}

	/**
	 * 
	 */
	public void cancel() {
		cancel = true;
	}
}
