package com.dakkra.javahashbenchmarktool;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class HashWorkThread extends Thread {

	private List<Integer> scoreList;

	private String data;

	private int counter;

	private boolean shouldRun;

	public HashWorkThread( List<Integer> scoreList, String data ) {
		this.scoreList = scoreList;
		this.data = data;
		counter = 0;
	}

	@Override
	public void run() {
		try {
			MessageDigest digest = MessageDigest.getInstance( "SHA3-256" );
			while( shouldRun ) {
				digest.digest( data.getBytes() );
				if( shouldRun ) counter++;
			}
		} catch( NoSuchAlgorithmException e ) {
			throw new RuntimeException( e );
		}
		postCount();
	}

	public void startWork() {
		shouldRun = true;
		start();
	}

	public void stopWork() {
		shouldRun = false;
	}

	private void postCount() {
		scoreList.add( counter );
	}

}
