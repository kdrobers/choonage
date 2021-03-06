package com.example.choonage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends ActionBarActivity {

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		TextView testText = (TextView) findViewById(R.id.testtext);
		testText.setText( Utilities.getLocalIpAddress() );
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
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

	public void login(View view) {
		EditText usernameField = (EditText)findViewById(R.id.enterUsername);
		EditText passwordField = (EditText)findViewById(R.id.enterPassword);
		
		String username = usernameField.getText().toString();
		String password = passwordField.getText().toString();
		//check username and password on server
		Intent intent = new Intent(LoginActivity.this, HomepageActivity.class);
		intent.putExtra(username,username);
		startActivity(intent);
	}
}
