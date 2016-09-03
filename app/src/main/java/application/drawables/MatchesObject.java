package application.drawables;

import android.view.MotionEvent;

import framework.IRenderable;
import framework.core.*;
import application.events.*;
import framework.graphics.Button;
import framework.graphics.Font;
import framework.graphics.Image;

public class MatchesObject implements IRenderable {
    private final static Integer NUMBER_OF_TEAMS = 12;
    private final static Integer EVENT_COUNT = 6;
    private final static Integer Y_MARGIN = 130;
    private final static Integer X_MARGIN = 30;

    private ClickEvent[] events = new ClickEvent[NUMBER_OF_TEAMS];
    private MatchEvent[] event = new MatchEvent[EVENT_COUNT];
    private Button[] buttons = new Button[NUMBER_OF_TEAMS];
    private Image[] flags = new Image[NUMBER_OF_TEAMS];
    private String[] names;

    //private ClickEvent completedEvent;
    private Button completeButton;

    public MatchesObject(GroupObject group) {
        Font font = Font.get("tiny");

        Integer x = group.getX() + group.getWidth() + X_MARGIN;
        Integer y = (int)(group.getY()) + Y_MARGIN;

        names = group.getTeamNames();
        for(int i = 0; i < 6; i++) {
            int number = i;

            switch(number){
                case 0: number = 1; break;
                case 1: number = 3; break;
                case 2: number = 2; break;
                case 3: number = 1; break;
                case 5: number = 2; break;
                default: number = 0; break;
            }

            // load the flag of the team
            flags[i] = new Image("sprites/" + names[number] + ".bmp");
            flags[i].setPosition((int)(x + 70), (int) (y), 50, 50);

            // load the button that stores the score
            buttons[i] = new Button(font);
            buttons[i].setSprite("sprites/button.png", x+15, y, 50, 50);

            // at certain points increment either x or y
            if(i == 1 || i == 3 || i == 5) {
                y = (int) (group.getY()) + 130;
                x += 240;
            } else {
                y -= 60;
            }
        }

        // reset position to the second load of matches
        x = group.getX() + group.getWidth() - 70;
        y = (int) (group.getY() - 70.0F);

        completeButton = new Button(font);
        completeButton.setSprite("sprites/button2.png", 780, group.getY() - 25, 220, 60);
        completeButton.setText("Auto-Complete", 890, group.getY() - 10, 200, 50);
        completeButton.setTextColour(1f, 1f, 0f, 1f);

        //completedEvent = new ClickEvent(completeButton);
        //completedEvent.eventType(new AutoCompleteEvent(event));

        // finish loading all the flags and button
        for(int i = 6; i < 12; i++) {
            int number = i - 6;

            switch(number){
                case 1: number = 2; break;
                case 3: number = 3; break;
                case 4: number = 3; break;
                case 5: number = 1; break;
                default: number = 0; break;
            }

            flags[i] = new Image("sprites/" + names[number] + ".bmp");
            flags[i].setPosition(x+5, (int) (y + 200), 50, 50);

            buttons[i] = new Button(font);
            buttons[i].setSprite("sprites/button.png", (int)(x + 60), y + 200, 50, 50);

            if(i == 7 || i == 9 || i == 11) {
                y = (int) (group.getY() - 70.0F);
                x += 240;
            } else {
                y -= 60;
            }
        }

        for(int i = 0; i < 6; i++) {
            int opponent = i;
            int team = i;

            switch(opponent){
                case 1: opponent = 2; break;
                case 3: opponent = 3; break;
                case 4: opponent = 3; break;
                case 5: opponent = 1; break;
                default: opponent = 0; break;
            }

            switch(team){
                case 0: team = 1; break;
                case 1: team = 3; break;
                case 2: team = 2; break;
                case 3: team = 1; break;
                case 5: team = 2; break;
                default: team = 0; break;
            }

            Integer day = 3;

            switch(i) {
                case 0: day = 1; break;
                case 1: day = 1; break;
                case 2: day = 2; break;
                case 3: day = 2; break;

                default: break;
            }

            event[i] = new MatchEvent(buttons[i], buttons[i+6], group.getTeam(team), group.getTeam(opponent), day);
            events[i+6] = new ClickEvent(buttons[i+6]);
            events[i+6].eventType(event[i]);
            events[i] = new ClickEvent(buttons[i]);
            events[i].eventType(event[i]);
        }
    }

    public void restart() {
        for(int i = 0; i < 6; i++) {
            event[i].Reset();
        }

        for(int i = 0; i < 12; i++) {
            buttons[i].hideText();
        }
    }

    public void reset() {
        completeButton.reset();
        for(int i = 0; i < 12; i++) {
            buttons[i].reset();
            flags[i].reset();
        }
    }

    public void onTouch(MotionEvent e, float x, float y) {
        if(e.getAction() == MotionEvent.ACTION_DOWN) {
            //completedEvent.OnTouch(e, x, y);
            for(int i = 0; i < events.length; i++) {
                events[i].OnTouch(e, x, y);
            }
        }
    }

    public void update(Integer y) {
        for(int i = 0; i < 12; i++) {
            buttons[i].translate(0, y);
            buttons[i].update();

            flags[i].translate(0, y);
            flags[i].update();
        }

        //completeButton.translate(0, y);
        //completeButton.update();
    }


    public void onLongPress(MotionEvent e, int x, int y) {
        if(e.getAction() == MotionEvent.ACTION_DOWN) {
            //completedEvent.onLongPress(e, x, y);
            for(int i = 0; i < events.length; i++) {
                events[i].onLongPress(e, x, y);
            }
        }
    }

    public void onEnter() {
        EventManager.get().addListeners(events);
        //EventManager.get().addListener(completedEvent);
    }

    public void onExit() {
        EventManager eventsMgr = EventManager.get();
        //eventsMgr.removeListener(completedEvent);
        for(int i = 0; i < events.length; i++) {
            eventsMgr.removeListener(events[i]);
        }
    }

    public Button[] getButtons() {
        return this.buttons;
    }

    public Image[] getFlags() {
        return this.flags;
    }

    @Override
    public void render() {
        for(int i = 0; i < 12; i++) {
            buttons[i].render();
            flags[i].render();
        }

        //completeButton.render();
    }

    public Object getCompleteButton() {
        return completeButton;
    }
}
