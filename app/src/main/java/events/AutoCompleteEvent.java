package events;

import framework.IEvent;
import framework.IUiEvent;
import framework.dialogs.MessageBox;

public class AutoCompleteEvent implements IEvent {
    private MatchEvent[] matchEvents;

    public AutoCompleteEvent(MatchEvent[] events) {
        matchEvents = events;
    }

    @Override
    public void onActivate(Object data) {
        MessageBox messageBox = new MessageBox();

        messageBox.setTitle("Quit ?");
        messageBox.setMessage("Press yes to fill with random results or no to fill with the actual results");
        messageBox.onAccept(new IUiEvent() {
            @Override
            public void onUiEvent() {
                for(MatchEvent e : matchEvents) {
                    e.randomise();
                }
            }
        });

        messageBox.onCancel(new IUiEvent() {
            @Override
            public void onUiEvent() {
                for(MatchEvent e : matchEvents) {
                    e.grabRealResult();
                }
            }
        });

        messageBox.EnableYesNo();
        messageBox.show(false);
    }

    @Override
    public void update() {
        ;
    }
}
