package framework.core;

import android.app.AlertDialog;
import android.content.DialogInterface;

/**
 * 
 * @author William
 *
 */
public class MessageBox {
	/** **/
	private IUiEvent onAcceptEvent;
	/** **/
	private IUiEvent onCancelEvent;
	/** **/
	private String message;
	/** **/
	private String title;
	/** **/
	private Boolean Yesno;
	/** **/
	private String positiveText;
	/** */
	private String negativeText;
	
	/**
	 * 
	 */
	public MessageBox() {
		positiveText = "Yes";
		negativeText = "No";
		message = "Yes/No ?";
		title = "Title";
		Yesno = false;
	}
	
	/**
	 * 
	 * @param okOnly
	 */
	public void show(Boolean okOnly) {
		if(!okOnly && !Yesno) {
			GameObject.Activity.runOnUiThread(new Runnable() {
		        public void run() {
		        	new AlertDialog.Builder(GameObject.Activity)
				    .setTitle(title)
				    .setMessage(message)
				    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
				      
				    	public void onClick(DialogInterface dialog, int which) { 
				        	if(onAcceptEvent != null) {
				        		onAcceptEvent.onUiEvent();
				        	}
				        	
				        	dialog.dismiss();
				        }
				     })
				     
				    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
				      
				    	public void onClick(DialogInterface dialog, int which) {
				    		if(onCancelEvent != null) {
				    			onCancelEvent.onUiEvent();
				    		}
				    		
				        	dialog.dismiss();
				        }
				    	
				     }).show();
		        }
		    });
		} else if(Yesno) {
			GameObject.Activity.runOnUiThread(new Runnable() {
		        public void run() {
		        	new AlertDialog.Builder(GameObject.Activity)
				    .setTitle(title)
				    .setMessage(message)
				    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
				      
				    	public void onClick(DialogInterface dialog, int which) { 
				        	if(onAcceptEvent != null) {
				        		onAcceptEvent.onUiEvent();
				        	}
				        	
				        	dialog.dismiss();
				        }
				     })
				     
				    .setNegativeButton("No", new DialogInterface.OnClickListener() {
				      
				    	public void onClick(DialogInterface dialog, int which) {
				    		if(onCancelEvent != null) {
				    			onCancelEvent.onUiEvent();
				    		}
				    		
				        	dialog.dismiss();
				        }
				    	
				     }).show();
		        }
		    });
		} else {
			GameObject.Activity.runOnUiThread(new Runnable() {
		        public void run() {
		        	new AlertDialog.Builder(GameObject.Activity)
				    .setTitle(title)
				    .setMessage(message)
				    .setPositiveButton(positiveText, new DialogInterface.OnClickListener() {     
				    	public void onClick(DialogInterface dialog, int which) { 
				        	if(onAcceptEvent != null) {
				        		onAcceptEvent.onUiEvent();
				        	}
				        	
				        	dialog.dismiss();
				        }
				     })
				     
				    .setNegativeButton(negativeText, new DialogInterface.OnClickListener() {
				    	public void onClick(DialogInterface dialog, int which) {
				    		if(onCancelEvent != null) {
				    			onCancelEvent.onUiEvent();
				    		}
				    		
				        	dialog.dismiss();
				        }
				    	
				     }).show();
		        }
		    });
		}
	}
	
	/**
	 * 
	 * @param title
	 * @return
	 */
	public MessageBox setTitle(String title) {
		this.title = title;
		return this;
	}
	
	/**
	 * 
	 * @param e
	 * @return
	 */
	public MessageBox onAccept(IUiEvent e) {
		onAcceptEvent = e;
		return this;
	}
	
	/**
	 * 
	 * @param e
	 * @return
	 */
	public MessageBox onCancel(IUiEvent e) {
		onCancelEvent = e;
		return this;
	}
	
	/**
	 * 
	 * @param message
	 * @return
	 */
	public MessageBox setMessage(String message) {
		this.message = message;
		return this;
	}

	/**
	 * 
	 * @return
	 */
	public MessageBox EnableYesNo() {
		this.Yesno = true;
		return this;
		
	}
}
