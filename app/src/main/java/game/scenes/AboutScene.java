package game.scenes;

import framework.IFactory;
import framework.graphics.Font;
import framework.graphics.Image;
import framework.graphics.Paragraph;
import framework.graphics.RenderQueue;
import framework.math.Vector2;
import game.scenes.SplashScene.HeaderButtons;
import android.view.MotionEvent;
import game.activity.MainActivity;
import framework.core.*;
import java.util.*;

public class AboutScene extends Scene {
    private static final Integer ANIMATION_SPEED = 50;
    private static final Integer PLATE_X =  225;
    private static final Integer PLATE_Y = 150;
    private static final Integer INFO_X = 300;
    private static final Integer INFO_Y = 200;

    private HeaderButtons headerButtons;
    private SceneAnimation animation;
    private ArrayList<Object> moveables;
    private Paragraph information;
    private Image headerBackground;
    private Image background;
    private Image backPlate;

    @Override
    public void onCreate(IFactory factory) {
        headerBackground = new Image("sprites/about.bmp");
        headerBackground.setPosition(0, 750, 1280, 50);
        headerButtons = factory.request("HeaderButtons");

        background = factory.request("SplashBackground");
        animation = new SceneAnimation();

        backPlate = new Image("sprites/gs.PNG");
        backPlate.setPosition(PLATE_X, PLATE_Y, 800, 450);

        moveables = new ArrayList<Object>();
        moveables.add(backPlate);

        information = new Paragraph(Font.get("small"), "strings/credits.txt");
        information.load(INFO_X, INFO_Y);
        information.setColour(1, 1, 0, 1);
        information.add(moveables);
        information.update();
    }

    @Override
    public void onUpdate() {
        information.update();
        background.update();
        backPlate.update();
        animation.update();
    }

    @Override
    public void onRender(RenderQueue renderList) {
        renderList.put(background);
        renderList.put(headerBackground);
        renderList.put(backPlate);
        renderList.put(information);
    }

    @Override
    public void onTouch(MotionEvent e, int x, int y) {
        headerButtons.onTouch(e, x, y);
    }

    @Override
    public void onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if(velocityX >= 1000) {
            animation.setupAnimation(moveables);
            animation.setVelocity(new Vector2(ANIMATION_SPEED, 0));
            animation.setState(MainActivity.Scenes.MAIN_MENU);
            animation.beginAnimation();
        }

        if(velocityX <= -1000) {
            animation.setupAnimation(moveables);
            animation.setVelocity(new Vector2(-ANIMATION_SPEED, 0));
            animation.setState(MainActivity.Scenes.CREDITS);
            animation.beginAnimation();
        }
    }

}
