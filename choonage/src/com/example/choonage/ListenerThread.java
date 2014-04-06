package com.example.choonage;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ListenerThread extends Thread {
	public final static int LISTENINGPORT = 3456;
	
	private volatile boolean isOn = true;
	protected ServerSocket serverSocket;
	
	@Override
	public void run() {
		try {
			serverSocket = new ServerSocket( LISTENINGPORT );
			while ( isOn ) {
				try {
					doLoop();
				} catch ( InterruptedException e ) {
//				} catch ( Dispatcher.AbortException e ) {
//					kill();
//					if ( e.getErrorCode() != Integer.MAX_VALUE )
//						System.out.print( "!=!=! ABORT EXCEPTION: " + e.toString() + "\n" );
				}
			}
		} catch ( IOException e ) {
			if ( serverSocket != null ) {
				try {
					serverSocket.close();
				} catch ( IOException e1 ) {
				}
			}
		}
	}

	protected void doLoop() throws InterruptedException, IOException {
		Socket socket = serverSocket.accept();
		StreamingThread streamingThread = new StreamingThread( socket );
	}
}
