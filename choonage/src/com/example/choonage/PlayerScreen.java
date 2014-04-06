package com.example.choonage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.TimeUnit;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.widget.SeekBar;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class PlayerScreen extends ActionBarActivity {
	public TextView songName, startTimeField, endTimeField;
	private MediaPlayer player;
	private long startTime = 0;
	private long finalTime = 0;
	private Handler myHandler = new Handler();
	private int forwardTime = 5000;
	private int backwardTime = 5000;
	private SeekBar seekbar;
	private ImageButton playButton, pauseButton;
	public static int oneTimeOnly = 0;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_player_screen);
	
		songName = (TextView)findViewById(R.id.textView4);
		startTimeField =(TextView)findViewById(R.id.textView1);
		endTimeField =(TextView)findViewById(R.id.textView2);
		seekbar = (SeekBar)findViewById(R.id.seekBar1);
		playButton = (ImageButton)findViewById(R.id.imageButton1);
		pauseButton = (ImageButton)findViewById(R.id.imageButton2);
		songName.setText("Sorrow - Shadowed Doubt.mp3");
		try {
			InputStream iS = getAssets().open( "Sorrow - Shadowed Doubt.wav" );
			FileInputStream fIS = startPlayback( iS, 0 );
			player = new MediaPlayer();
			player.setAudioStreamType( AudioManager.STREAM_MUSIC );
			player.setDataSource( fIS.getFD() );
			fIS.close();
			player.prepare();
			player.start();
			Toast.makeText( this, "Started Song", Toast.LENGTH_SHORT ).show();
			new Thread( new Runnable() {
				@Override
				public void run() {
					try {
						Utilities.postSong( "Sorrow - Shadowed Doubt" );
					} catch ( IOException e ) {
					}
				}
			}).start();
		} catch ( IOException e ) {
			e.printStackTrace();
			Toast.makeText( this, "FAILED", Toast.LENGTH_SHORT ).show();
			player = null;
		}
		//player = MediaPlayer.create(this, R.raw.song);
		seekbar.setClickable(false);
		pauseButton.setEnabled(false);
	}
	
	@Override
	protected void onStop() {
		if ( player != null ) {
			player.release();
			player = null;
		}
	}
	
	private Runnable UpdateSongTime = new Runnable() {
		public void run() {
			startTime = player.getCurrentPosition();
			seekbar.setProgress((int)startTime);
			myHandler.postDelayed(this, 100);
		}
	};	
	
	public void play(View view) {
		Toast.makeText(getApplicationContext(), "Playing sound", 
				Toast.LENGTH_SHORT).show();
		player.start();
		finalTime = player.getDuration();
		startTime = player.getCurrentPosition();
		if(oneTimeOnly == 0){
			seekbar.setMax((int) finalTime);
			oneTimeOnly = 1;
		} 

		
		seekbar.setProgress((int)startTime);
		myHandler.postDelayed(UpdateSongTime,100);
		pauseButton.setEnabled(true);
		playButton.setEnabled(false);
	}

	public void pause(View view) {
		Toast.makeText(getApplicationContext(), "Pausing sound", 
				Toast.LENGTH_SHORT).show();
		
		player.pause();
		pauseButton.setEnabled(false);
		playButton.setEnabled(true);
	}


	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.player_screen, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public FileInputStream startPlayback( InputStream is, int pos ) throws IOException {
		deleteFile( "temp.dat" );
		FileOutputStream out = openFileOutput( "temp.dat", MODE_PRIVATE );
		
		// Get header
		byte[] buffer = new byte[ 44 ];
        int length;
        length = is.read( buffer );
        long byteRate = ( (long) buffer[ 28 ] ) & 255;
        byteRate = byteRate << 8 | ( (long) buffer[ 29 ] ) & 255;
        byteRate = byteRate << 8 | ( (long) buffer[ 30 ] ) & 255;
        byteRate = byteRate << 8 | ( (long) buffer[ 31 ] ) & 255;
        if ( pos > 0 ) {
        	is.skip( byteRate*pos );
            long chunkSize = ( (long) buffer[ 4 ] ) & 255;
            chunkSize = byteRate << 8 | ( ( (long) buffer[ 5 ] ) & 255 );
            chunkSize = byteRate << 8 | ( ( (long) buffer[ 6 ] ) & 255 );
            chunkSize = byteRate << 8 | ( ( (long) buffer[ 7 ] ) & 255 );
            Toast.makeText( this, chunkSize + "initial", Toast.LENGTH_LONG ).show();
            chunkSize -= byteRate*pos*8;
            Toast.makeText( this, chunkSize + "first", Toast.LENGTH_LONG ).show();
            buffer[ 4 ] = (byte) ( chunkSize >> 24 & 255 ); buffer[ 5 ] = (byte) ( chunkSize >> 16 & 255 );
            buffer[ 6 ] = (byte) ( chunkSize >> 8 & 255 ); buffer[ 7 ] = (byte) ( chunkSize & 255 );
            chunkSize -= 36;
            Toast.makeText( this, chunkSize + "second", Toast.LENGTH_LONG ).show();
            buffer[ 40 ] = (byte) ( chunkSize >> 24 & 255 ); buffer[ 41 ] = (byte) ( chunkSize >> 16 & 255 );
            buffer[ 42 ] = (byte) ( chunkSize >> 8 & 255 ); buffer[ 43 ] = (byte) ( chunkSize & 255 );
        }
        out.write( buffer, 0, length ); // Print Header
		buffer = new byte[ 16*1024 ];
        while ( ( length = is.read( buffer ) ) != -1 ) 
        	out.write( buffer, 0, length );
        out.flush();
        out.close();
        return openFileInput( "temp.dat" );
	}
}
