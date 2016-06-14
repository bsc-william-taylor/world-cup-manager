package drawables;

import android.view.MotionEvent;
import framework.core.*;
import java.util.*;
import objects.Globals;

public class HelpObject implements IRenderable {
    private ClickEvent gotchaClick;
    private ClickEvent showClick;
    private Button dontShowButton;
    private Button gotchaButton;
    private Image helpBackground;
    private Boolean enabled;

    public HelpObject() {
        Font font = Font.get("Tiny");
        enabled = Globals.get().ShowHelp();

        dontShowButton = new Button(font);
        dontShowButton.setSprite("sprites/button3.png", 830, 100, 420, 80);
        dontShowButton.setText("GOT IT !", 1030, 125, 300, 50);
        dontShowButton.setTextColour(1, 1, 0, 1);

        helpBackground = new Image("sprites/help.bmp");
        helpBackground.setPosition(0, 0, 1280, 750);

        showClick = new ClickEvent(dontShowButton);
        showClick.eventType(new ContinueEvent(false));

        gotchaButton = new Button(font);

        if(enabled) {
            gotchaButton.setSprite("sprites/fill.png", 830, 10, 420, 80);
        } else {
            gotchaButton.setSprite("sprites/button3.png", 830, 10, 420, 80);
        }

        gotchaButton.setText("SHOW HELP AT LAUNCH", 1030, 35, 300, 50);
        gotchaButton.setTextColour(1, 1, 0, 1);

        gotchaClick = new ClickEvent(gotchaButton);
        gotchaClick.eventType(new ContinueEvent(true));

        EventManager.get().addListener(showClick);
        EventManager.get().addListener(gotchaClick);
    }

    public void onTouch(MotionEvent e, int x, int y) {
        if(enabled && e.getAction() == MotionEvent.ACTION_DOWN) {
            gotchaClick.OnTouch(e, x, y);
            showClick.OnTouch(e, x, y);
        }
    }

    public void update() {
        if(enabled) {
            helpBackground.update();
            dontShowButton.update();
            gotchaButton.update();
        }
    }

    public void enable() {
        enabled = true;
    }

    public void disable() {
        enabled = false;
    }

    public Boolean isActive() {
        return(enabled);
    }

    @Override
    public void render() {
        if(enabled) {
            helpBackground.render();
            dontShowButton.render();
            gotchaButton.render();
        }
    }

    private class ContinueEvent implements IEvent {
        private final Integer TEXTURE_DELAY = 50;
        private Boolean showHelp;

        public ContinueEvent(Boolean first) {
            showHelp = first;
        }

        @Override
        public void onActivate(Object data) {
            if(!showHelp) {
                dontShowButton.setTexture("sprites/fill.png");

                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        dontShowButton.setTexture("sprites/button3.png");
                        enabled = false;
                    }
                }, TEXTURE_DELAY);

            }

            if(showHelp && enabled){
                if(gotchaButton.getFilename().compareToIgnoreCase("sprites/fill.png") == 0) {
                    gotchaButton.setTexture("sprites/button3.png");
                    Globals.get().showHelp(false);
                } else {
                    gotchaButton.setTexture("sprites/fill.png");
                    Globals.get().showHelp(true);
                }
            }
        }

        @Override
        public void update() {
            ;
        }
    }

}
