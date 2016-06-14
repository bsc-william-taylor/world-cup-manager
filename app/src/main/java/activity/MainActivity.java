package activity;

import android.app.Activity;
import android.os.Bundle;

import events.ExitEvent;
import framework.core.*;
import objects.Globals;
import android.view.*;
import events.*;
import scenes.*;

public class MainActivity extends Activity implements IGameActivity {
    public static Boolean TouchSounds = true;
    public static class Scenes {
        public static final int ENTRY_SCENE = 0;
        public static final int MAIN_MENU = 1;
        public static final int CREDITS = 2;
        public static final int ABOUT = 3;
        public static final int EDIT = 4;
        public static final int GROUP = 5;
        public static final int KNOCK_OUT = 6;
        public static final int MATCHES = 7;
    }

    private GameObject game;
    private TouchEvent effect;

    @Override
    public void onResume() {
        super.onResume();
        AudioClip.masterVolume = 1.0F;
    }

    @Override
    public void setupStates(SceneManager states) {
        states.clear();
        states.createScene(new SplashScene());
        states.createScene(new EntryScene());
        states.createScene(new MenuScene());
        states.createScene(new CreditsScene());
        states.createScene(new AboutScene());
        states.createScene(new EditScene());
        states.createScene(new GroupScene());
        states.createScene(new KnockOutScene());
        states.createScene(new MatchesScene());

        states.startFrom(Scenes.ENTRY_SCENE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Globals.get().ReleaseGlobals();
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        if(game.hasInitialised()) {
            if(e.getAction() == MotionEvent.ACTION_DOWN && TouchSounds) {
                effect.Play();
            }

            game.touchEvent(e);
        }

        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            EventManager.get().triggerEvent(new ExitEvent(), null);
            return true;
        }

        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Globals.get().reload();

        game = (GameObject)getApplicationContext();
        game.setupWindow(this);
        game.start(this);

        effect = new TouchEvent();
    }

    @Override
    protected void onPause() {
        super.onPause();
        AudioClip.masterVolume = 0.0F;
    }
}
