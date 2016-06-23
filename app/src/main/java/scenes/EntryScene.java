package scenes;

import android.view.MotionEvent;
import activity.MainActivity;
import framework.IFactory;
import framework.core.*;
import framework.graphics.Font;
import framework.graphics.Image;
import framework.graphics.Label;
import framework.graphics.RenderQueue;

public class EntryScene extends Scene {
    private static final Integer TEXT_X = 525;
    private static final Integer TEXT_Y = 200;

    private Image background;
    private Label version;
    private Label header;
    private Label text;

    @Override
    public void onCreate(IFactory factory) {
        text = new Label(Font.get("small"), "Tap to continue");
        text.load(TEXT_X, TEXT_Y);
        text.setColour(1f, 1f, 0f, 1f);
        text.update();

        background = factory.request("SplashBackground");
        version = factory.request("version");
        header = factory.request("header");
    }

    @Override
    public void onUpdate() {
        background.update();
        version.update();
        header.update();
        text.update();
    }

    @Override
    public void onRender(RenderQueue renderList) {
        renderList.put(background);
        renderList.put(version);
        renderList.put(header);
        renderList.put(text);
    }

    @Override
    public void onTouch(MotionEvent e, int x, int y) {
        if(e.getAction() == MotionEvent.ACTION_DOWN) {
            SceneManager.get().switchTo(MainActivity.Scenes.MAIN_MENU);
        }
    }
}
