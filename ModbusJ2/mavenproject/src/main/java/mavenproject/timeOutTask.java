package mavenproject;

import java.util.Timer;
import java.util.TimerTask;

public class timeOutTask extends TimerTask{

	private Thread t;
	private Timer timer;
	private boolean disConnectTF;
	
	timeOutTask(Thread t, Timer timer,boolean disConnectTF){
		this.t = t;
		this.timer = timer;
		this.disConnectTF = disConnectTF;
	}
	@Override
	public void run() {
		if(!disConnectTF) {
			timer.cancel();
		}else {
			System.out.println("aASDASDASDASDASDSADASDASDASDASDSADASD");
		}
		
	}

}
