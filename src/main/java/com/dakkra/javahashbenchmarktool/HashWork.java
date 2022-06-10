package com.dakkra.javahashbenchmarktool;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Object that times a threads ability to SHA3 data
 */
public class HashWork {

	private final long TIME_MS = 1000 * 5;

	private Timer timer;

	private HashWorkThread workThread;

	public HashWork( List<Integer> scoreList, String data ) {
		workThread = new HashWorkThread( scoreList, data );
		timer = new Timer();
	}

	public void startWork() {
		workThread.startWork();
		timer.schedule( new StopWorkTask(), TIME_MS );
	}

	public void stopWork() {
		workThread.stopWork();
		timer.cancel();
	}

	public void joinThread() {
		try {
			workThread.join();
		} catch( InterruptedException e ) {
			throw new RuntimeException( e );
		}
	}

	class StopWorkTask extends TimerTask {

		@Override
		public void run() {
			stopWork();
		}
	}
}
