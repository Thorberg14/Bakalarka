package thorberg.util;

public class TPSCounter {
	
	long lastSecond = 0;
	int ticks = 0;
	int currentTPS = 0;
	
	public void tick(){
		if (lastSecond == 0) lastSecond = System.currentTimeMillis();
		
		long elapsedTime = System.currentTimeMillis() - lastSecond;
		
		if (elapsedTime >= 1000) {
			currentTPS = ticks;
			ticks = 0;
			lastSecond = System.currentTimeMillis() - (elapsedTime-1000); //odcitat prekroceny cas
		}
		ticks ++;
	}
	
	public int getCurrentTPS(){
		return currentTPS;
	}
}
