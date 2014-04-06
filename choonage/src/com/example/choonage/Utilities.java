package com.example.choonage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

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
	
	public static void createAndRunThread() {
		Thread myThread = new ListenerThread();
		myThread.start();
	}
}
