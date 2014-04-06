package com.example.choonage;

import java.net.Socket;

public class StreamingThread extends Thread {
	public final static int STREAMINGPORT = 3456;
	
	private volatile boolean isOn = true;
	
	public StreamingThread( Socket socket ) {
		//doSomethingWith( socket );
	}
	
	@Override
	public void run() {
		while ( isOn ) {
			try {
				doLoop();
			} catch ( InterruptedException e ) {
			}
		}
	}

	protected void doLoop() throws InterruptedException {
		//MusicBlock musicBlock = fetchMusicBlock();
		//musicBlock = convert( musicBlock );
		//send( musicBlock );
		//sleep( forTimeSent );
		sleep( 2000 );
	}
}
