package scenes;

import framework.IFactory;
import framework.IRenderable;
import framework.graphics.Font;
import framework.graphics.Image;
import framework.graphics.Label;
import framework.graphics.RenderQueue;
import framework.math.Vector2;
import scenes.SplashScene.HeaderButtons;
import android.view.MotionEvent;
import activity.MainActivity;

import java.util.ArrayList;
import framework.core.*;

public class CreditsScene extends Scene {
    private static final Integer ANIMATION_SPEED = 50;
    private CreditBackground sceneBackground;
    private HeaderButtons headerButtons;
    private SceneAnimation animation;
    private ArrayList<Object> moveables;
    private CreditImage sceneImages;
    private CreditText sceneText;

    @Override
    public void onCreate(IFactory factory) {
        sceneBackground = new CreditBackground(factory);
        sceneImages = new CreditImage(factory);
        sceneText = new CreditText(factory);
        animation = new SceneAnimation();

        headerButtons = factory.request("HeaderButtons");

        moveables = new ArrayList<Object>();
        sceneImages.add(moveables);
        sceneText.add(moveables);
    }

    @Override
    public void onUpdate() {
        sceneBackground.update();
        sceneImages.update();
        animation.update();
        sceneText.update();
    }

    @Override
    public void onRender(RenderQueue renderList) {
        renderList.put(sceneBackground);
        renderList.put(sceneText);
        renderList.put(sceneImages);
    }

    @Override
    public void onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if(velocityX >= 1000) {
            animation.setupAnimation(moveables);
            animation.setVelocity(new Vector2(ANIMATION_SPEED, 0));
            animation.setState(MainActivity.Scenes.MAIN_MENU);
            animation.beginAnimation();
        } else if(velocityX <= -1000) {
            animation.setupAnimation(moveables);
            animation.setVelocity(new Vector2(-ANIMATION_SPEED, 0));
            animation.setState(MainActivity.Scenes.ABOUT);
            animation.beginAnimation();
        }
    }

    @Override
    public void onTouch(MotionEvent e, int x, int y) {
        headerButtons.onTouch(e, x, y);
    }

    private class CreditText implements IRenderable {
        private static final String PROGRAMMING_BY = "Developer William Taylor";
        private static final String DESIGN_BY = "Design Constantin Toader";
        private static final String MUSIC_BY = "Music Scott McLean";
        private static final String ART_BY = "Art Daniel Taylor";

        private Image background;
        private Label musicArtist;
        private Label programmer;
        private Label manager;
        private Label artist;

        public CreditText(IFactory factory) {
            Font font = Font.get("small");

            background = new Image("sprites/gs.PNG");
            background.setPosition(200, 200, 900, 300);
            musicArtist = new Label(font);
            programmer = new Label(font);
            manager = new Label(font);
            artist = new Label(font);

            programmer.text(PROGRAMMING_BY);
            musicArtist.text(MUSIC_BY);
            manager.text(DESIGN_BY);
            artist.text(ART_BY);

            musicArtist.load(0, 0);
            programmer.load(0, 0);
            manager.load(0, 0);
            artist.load(0, 0);

            musicArtist.setInitialPosition(550 - musicArtist.getWidth()/2, 300);
            programmer.setInitialPosition(550 - programmer.getWidth()/2, 350);
            manager.setInitialPosition(550 - manager.getWidth()/2, 400);
            artist.setInitialPosition(550 - artist.getWidth()/2, 250);

            musicArtist.setColour(1f, 1f, 0f, 1f);
            programmer.setColour(1f, 1f, 0f, 1f);
            manager.setColour(1f, 1f, 0f, 1f);
            artist.setColour(1f, 1f, 0f, 1f);
        }

        public void add(ArrayList<Object> animationObject) {
            animationObject.add(musicArtist);
            animationObject.add(background);
            animationObject.add(programmer);
            animationObject.add(manager);
            animationObject.add(artist);
        }

        public void update() {
            musicArtist.update();
            background.update();
            programmer.update();
            manager.update();
            artist.update();
        }

        @Override
        public void render() {
            background.render();
            musicArtist.render();
            programmer.render();
            manager.render();
            artist.render();

        }
    }

    private class CreditBackground implements IRenderable {
        private Image headerBackground;
        private Image background;

        public CreditBackground(IFactory factory) {
            headerBackground = new Image("sprites/credits.bmp");
            headerBackground.setPosition(0, 750, 1280, 50);
            background = factory.request("SplashBackground");
        }

        public void update() {
            headerBackground.update();
            background.update();
        }

        @Override
        public void render() {
            background.render();
            headerBackground.render();
        }
    }

    private class CreditImage implements IRenderable {
        private Image logoThree;
        private Image logoTwo;
        private Image logoOne;

        public CreditImage(IFactory factory) {
            logoThree = new Image("sprites/bggame.bmp");
            logoTwo = new Image("sprites/littlebox.bmp");
            logoOne = new Image("sprites/ntgame.bmp");

            logoThree.setPosition(800, 225, 150, 80);
            logoTwo.setPosition(800, 310, 150, 80);
            logoOne.setPosition(800, 395, 150, 80);
        }

        public void add(ArrayList<Object> animationObjects) {
            animationObjects.add(logoThree);
            animationObjects.add(logoTwo);
            animationObjects.add(logoOne);
        }

        public void update() {
            logoThree.update();
            logoTwo.update();
            logoOne.update();
        }

        @Override
        public void render() {
            logoThree.render();
            logoTwo.render();
            logoOne.render();
        }
    }
}
