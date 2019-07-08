package com.hansoncoyne.cfa633;

import java.io.*;
import java.util.*;
import com.hansoncoyne.cfa633.StreamGobbler;
import com.hansoncoyne.cfa633.StdErrorReader;

public class Mp3Player
{
	private static Mp3Player instance;
	private File tempPlaylist;
	private Process mpg123;
	private DisplayListener displayListener;

	public static Mp3Player getInstance()
	{
		if (instance==null) instance = new Mp3Player();
		return instance;
	}
	private Mp3Player()
	{
	}
	public void play(File file) 
	{
		try 
		{
			/** 
			 *  Before we play, ensure a stop.
			 */
			this.stop();

			/** 
			 *  Build a play list 
			 */
			if (tempPlaylist != null) tempPlaylist.delete();
			tempPlaylist = File.createTempFile("cfa633-mpg123", ".pls");
			tempPlaylist.deleteOnExit();
			PrintWriter out = new PrintWriter( new BufferedWriter( new FileWriter( tempPlaylist )));
			buildPlaylist(file, out);
			out.close();

			/**
			 *  get the full path to the playlist and build the exec array
			 *  > mpg123 -z -v -m -b 1024 -@ /mp3/tmp/all.pls
			 */
			String cmd[] = {"mpg123","-z","-v","-m","-b","1024","-@","dummy"};
			String mp3 = tempPlaylist.getAbsolutePath();
			cmd[7] = mp3;

			/**
			 * run it and get handles to StdErr, and StdOut
			 */
			Runtime rt = Runtime.getRuntime();
			mpg123 = rt.exec(cmd);
			StdErrorReader errorReader = new StdErrorReader(mpg123.getErrorStream(), "ERR", displayListener, false);
			StreamGobbler outputReader = new StreamGobbler(mpg123.getInputStream(), "OUT");

			/** 
			 * the proces should be running, start listening for StdOut and StdErr
			 */
			errorReader.start();
			outputReader.start();
		}
		catch (Exception e) { }
	}
	public void setDisplayListener(DisplayListener displayListener)
	{
		this.displayListener = displayListener;
	}
	private static void buildPlaylist(File file, PrintWriter out) 
	{
		if (file.isDirectory()) 
		{
			String[] children = file.list();
			for (int i=0; i<children.length; i++) 
			{
				buildPlaylist(new File(file, children[i]), out);
			}
		} 
		else if (file.isFile())
		{
			String mp3 = file.getAbsolutePath();
			out.println(mp3);
		}
	}
	public void stop()
	{
		try
		{
			mpg123.destroy();
			/*
			PrintWriter out = new PrintWriter( new OutputStreamWriter ( mpg123.getOutputStream() ) );
			out.print("q");
			out.flush();
			out.close();
			*/
		}
		catch (Exception e) { }
	}
	public boolean isPlaying()
	{
		boolean isPlaying = false;
		try 
		{
			int rv = mpg123.exitValue();
		}
		catch (IllegalThreadStateException e)
		{
			isPlaying = true;
		}
		catch (Exception e)
		{
		
		}
		return isPlaying;
	}
}
