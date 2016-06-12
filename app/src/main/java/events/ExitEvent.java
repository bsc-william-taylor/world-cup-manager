package events;

import framework.core.*;
import objects.Globals;

public class ExitEvent implements IEvent, IUiEvent {
	@Override
	public void onActivate(Object data) {
		MessageBox messageBox = new MessageBox();
		
		messageBox.setTitle("Quit ?");
		messageBox.setMessage("Are you sure ?");
		messageBox.onAccept(this);
		messageBox.EnableYesNo();
		messageBox.show(false);
	}

	@Override
	public void onUiEvent() {
		Globals.get().ReleaseGlobals();
		System.exit(0);
	}

	@Override
	public void update() {
		;
	}
}
