package com.example.choonage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.util.Enumeration;
import java.util.zip.GZIPInputStream;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class Utilities {
	
	public static String getLocalIpAddress() {
	    try {
	        for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
	            NetworkInterface intf = en.nextElement();
	            for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
	                InetAddress inetAddress = enumIpAddr.nextElement();
	                if (!inetAddress.isLoopbackAddress() && inetAddress.getHostAddress().indexOf( '%' ) <= 0) {
	                	return inetAddress.getHostAddress();
	                }
	            }
	        }
	    } catch (SocketException ex) {
	    	System.out.println( ex.toString() );
	    }
	    return null;
	}
	
	public FileInputStream readFile( File file, int pos ) throws IOException {
		FileInputStream stream = new FileInputStream( file );
		File convertedFile = File.createTempFile( "convertedFile", ".dat" );
		OutputStream out = new FileOutputStream(convertedFile);
		
		byte buffer[] = new byte[16384];
        int length = 0;
        while ( (length = stream.read(buffer)) != -1 ) 
        {
          out.write(buffer,0, length);
        }
        out.close();

        return stream;
	}
	
	public static void postSong( String song ) throws IOException {
		String msg = "usr=jcriquet&ip=" + getLocalIpAddress() + "&ttl=" + song;
		HttpURLConnection connection;
		connection = (HttpURLConnection) ( new URL( "169.233.230.216/TomCatServerServlet/Blah.HTML" ) ).openConnection();
		connection.setDoOutput(true);
		connection.setDoInput(true);
		connection.setRequestMethod( "POST" );
		connection.setRequestProperty( "Content-Length", "" + msg.length() );
		OutputStream connectionOut = connection.getOutputStream();
		connectionOut.write( msg.getBytes() );
		connectionOut.flush();
		connectionOut.close();
		Log.d( "Utilities", "Send..." );
		
		BufferedReader reader = new BufferedReader( new InputStreamReader( connection.getInputStream() ) );
		String response = reader.readLine(); // Get the first line...
		String inputLine;
		while ( ( inputLine = reader.readLine() ) != null )
			response = response + '\n' + inputLine; // ...and any other lines if they exist
		reader.close();
		connection.disconnect();
		Log.d( "Utilities", "Response: " + response );
	}
	
	public static void createAndRunThread() {
		Thread myThread = new ListenerThread();
		myThread.start();
	}
}
