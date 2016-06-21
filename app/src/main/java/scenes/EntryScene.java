package scenes;

import android.view.MotionEvent;
import activity.MainActivity;
import framework.core.*;

public class EntryScene extends Scene {
	private static final Integer TEXT_X = 525;
	private static final Integer TEXT_Y = 200;

	private Image Background;
	private Label Version;
	private Label Header;
	private Label Text;

	@Override
	public void onCreate(IFactory factory) {
		Text = new Label(Font.get("small"), "Tap to continue");
		Text.load(TEXT_X, TEXT_Y);
		Text.setColour(1f, 1f, 0f, 1f);
		Text.update();
		
		Background = factory.request("SplashBackground");
		Version = factory.request("Version");
		Header = factory.request("Header");
	}

	@Override
	public void onUpdate() {
		Background.update();
		Version.update();
		Header.update();
		Text.update();
	}

	@Override
	public void onRender(RenderQueue renderList) {
		renderList.put(Background);
		renderList.put(Version);
		renderList.put(Header);
		renderList.put(Text);
	}

	@Override
	public void onTouch(MotionEvent e, int x, int y) {
		// simply go to the main menu when the screen is tapped.
		if(e.getAction() == MotionEvent.ACTION_DOWN) {
			SceneManager.get().switchTo(MainActivity.Scenes.MAIN_MENU);
		}
	}
}
