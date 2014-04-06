package com.example.choonage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import android.media.MediaPlayer;

public class PlayerThread extends Thread {
	private volatile boolean isOn = true;
	protected volatile File file;
	protected File tmpFile;
	
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
		if ( tmpFile == null ) {
			try {
				//tmpFile = Utilities.startPlayback( null, 0 );
				FileInputStream fIS = new FileInputStream( tmpFile );
				MediaPlayer mp = new MediaPlayer();
				mp.setDataSource( fIS.getFD() );
				fIS.close();
			} catch ( IOException e ) {
				return;
			}
		}
		// If nothing else, fill buffer
		sleep( 2000 );
	}
	
	public void setFile( File file ) {
		this.file = file;
	}
}
