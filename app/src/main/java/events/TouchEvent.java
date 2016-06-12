package events;

import java.util.Random;
import framework.core.*;


public class TouchEvent {
	private Random randomGenarator;

	private AudioClip kickSoundThree;
	private AudioClip kickSoundTwo;
	private AudioClip kickSoundOne;

	public TouchEvent() {
		initialiseSounds();
	}

	public void Play() {
		AudioClip soundToPlay = null;
		
		randomGenarator = new Random();
		randomGenarator.setSeed(System.currentTimeMillis());

		Integer pick = randomGenarator.nextInt(3);
		
		switch(pick) {
			case 0: soundToPlay = kickSoundOne; break;
			case 1: soundToPlay = kickSoundTwo; break;
			case 2: soundToPlay = kickSoundThree; break;
			
			default: break;
		}

		if(soundToPlay != null) {
			soundToPlay.setVolume(0.05F, 0.05F);			
			soundToPlay.setLoop(false);
			soundToPlay.play();
		}
	}

	private void initialiseSounds() {
		kickSoundThree = new AudioClip(framework.core.R.raw.kickedfootball3);
		kickSoundTwo = new AudioClip(framework.core.R.raw.kickedfootball2);
		kickSoundOne = new AudioClip(framework.core.R.raw.kickedfootball1);

		kickSoundThree.alwaysOn();
		kickSoundTwo.alwaysOn();
		kickSoundOne.alwaysOn();
	}
}
