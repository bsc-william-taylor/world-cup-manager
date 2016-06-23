package scenes;

import framework.IEvent;
import framework.IFactory;
import framework.audio.AudioClip;
import framework.graphics.Button;
import framework.graphics.Font;
import framework.graphics.Image;
import framework.graphics.RenderQueue;
import framework.math.Vector2;
import scenes.SplashScene.HeaderButtons;
import android.view.MotionEvent;
import activity.MainActivity;
import java.util.*;

import drawables.HelpObject;
import framework.core.*;
import events.*;

public class EditScene extends Scene implements IEvent {
    private static final Integer ANIMATION_SPEED = 50;
    private static final Integer PRESS_DELAY = 50;
    private static final Integer BUTTON_X = 250;
    private static final Integer LABEL_X = 470;

    private ArrayList<Object> animatedObjects;
    private SceneAnimation animation;
    private Button resetKoButton;
    private Button resetButton;
    private Button touchButton;
    private Button helpButton;
    private Button muteButton;

    private Image headerBackground;
    private Image background;

    private HeaderButtons headerButtons;
    private HelpObject helpObject;
    private AudioClip backMusic;

    private ClickEvent resetKoClick;
    private ClickEvent touchClick;
    private ClickEvent resetClick;
    private ClickEvent muteClick;
    private ClickEvent helpClick;

    @Override
    public void onCreate(IFactory factory) {
        Font font = Font.get("tiny");

        // setup and grab the background object
        headerBackground = new Image("sprites/options.bmp");
        headerBackground.setPosition(0, 750, 1280, 50);
        headerButtons = factory.request("HeaderButtons");
        background = factory.request("Background");

        // setup all the buttons for this scene
        resetButton = new Button(font);
        resetButton.setSprite("sprites/button2.png", BUTTON_X, 455, 420, 80);
        resetButton.setText("RESET TOURNAMENT", LABEL_X, 475, 320, 100);
        resetButton.setTextColour(1, 1, 0, 1);

        helpButton = new Button(font);
        helpButton.setSprite("sprites/button2.png", BUTTON_X, 355, 420, 80);
        helpButton.setText("HELP", LABEL_X, 375, 300, 100);
        helpButton.setTextColour(1, 1, 0, 1);

        muteButton = new Button(font);
        muteButton.setSprite("sprites/button2.png", BUTTON_X, 255, 420, 80);
        muteButton.setText("MUSIC", LABEL_X, 275, 300, 100);
        muteButton.setTextColour(1, 1, 0, 1);

        touchButton = new Button(font);
        touchButton.setSprite("sprites/button2.png", BUTTON_X, 155, 420, 80);
        touchButton.setText("TOUCH SOUND", LABEL_X, 175, 300, 100);
        touchButton.setTextColour(1, 1, 0, 1);

        resetKoButton = new Button(font);
        resetKoButton.setSprite("sprites/button2.png", BUTTON_X, 555, 420, 80);
        resetKoButton.setText("RESET KNOCKOUT STAGE", LABEL_X, 575, 300, 100);
        resetKoButton.setTextColour(1, 1, 0, 1);

        resetKoClick = new ClickEvent(resetKoButton);
        touchClick = new ClickEvent(touchButton);
        resetClick = new ClickEvent(resetButton);
        helpClick = new ClickEvent(helpButton);
        muteClick = new ClickEvent(muteButton);

        resetKoClick.eventType(new ResetKoEvent(resetKoButton));
        touchClick.eventType(new MuteEffectEvent(touchButton));
        resetClick.eventType(new ResetEvent(resetButton));
        muteClick.eventType(new MuteEvent(muteButton));
        helpClick.eventType(this);

        backMusic = factory.request("BackgroundMusic");

        animation = new SceneAnimation();
        animatedObjects = new ArrayList<Object>();
        animatedObjects.add(resetKoButton);
        animatedObjects.add(resetButton);
        animatedObjects.add(touchButton);
        animatedObjects.add(helpButton);
        animatedObjects.add(muteButton);

        helpObject = new HelpObject();
        helpObject.disable();
    }

    @Override
    public void onEnter(Integer e) {
        EventManager events = EventManager.get();
        events.addListener(resetKoClick);
        events.addListener(resetClick);
        events.addListener(helpClick);
        events.addListener(muteClick);
        events.addListener(touchClick);
    }


    @Override
    public void onExit(Integer e) {
        EventManager events = EventManager.get();
        events.removeListener(resetKoClick);
        events.removeListener(resetClick);
        events.removeListener(helpClick);
        events.removeListener(muteClick);
        events.removeListener(touchClick);
    }

    @Override
    public void onUpdate() {
        headerBackground.update();
        resetKoButton.update();
        touchButton.update();
        resetButton.update();
        background.update();
        helpButton.update();
        muteButton.update();
        helpObject.update();
        animation.update();
    }

    @Override
    public void onRender(RenderQueue renderList) {
        renderList.put(background);

        if(!helpObject.isActive()) {
            renderList.put(resetKoButton);
            renderList.put(resetButton);
            renderList.put(helpButton);
            renderList.put(muteButton);
            renderList.put(touchButton);
        }

        renderList.put(headerBackground);
        renderList.put(helpObject);

        backMusic.play();
    }

    @Override
    public void onFling(MotionEvent e, MotionEvent e2, float x, float y) {
        if(x >= 1500 && !helpObject.isActive()) {
            animation.setupAnimation(animatedObjects);
            animation.setVelocity(new Vector2(-ANIMATION_SPEED, 0));
            animation.setState(MainActivity.Scenes.MAIN_MENU);
            animation.beginAnimation();
        }
    }

    @Override
    public void onTouch(MotionEvent e, int x, int y) {
        helpObject.onTouch(e, x, y);

        if(e.getAction() == MotionEvent.ACTION_DOWN && !helpObject.isActive()) {
            headerButtons.onTouch(e, x, y);
            resetKoClick.OnTouch(e, x, y);
            resetClick.OnTouch(e, x, y);
            touchClick.OnTouch(e, x, y);
            helpClick.OnTouch(e, x, y);
            muteClick.OnTouch(e, x, y);
        }
    }

    @Override
    public void onActivate(Object data) {
        helpButton.setTexture("sprites/fill.png");

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                helpObject.enable();
                helpButton.setTexture("sprites/button2.png");
                cancel();
            }
        }, PRESS_DELAY);
    }

    @Override
    public void update() {
        ;
    }
}
