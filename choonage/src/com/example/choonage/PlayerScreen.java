package com.example.choonage;

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
		player = MediaPlayer.create(this, R.raw.song);
		seekbar.setClickable(false);
		pauseButton.setEnabled(false);
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
}
