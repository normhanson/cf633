package com.hansoncoyne.cfa633;

import java.util.*;
import java.io.*;

class StdErrorReader extends Thread
{
	InputStream is;
	String type;
	String title;
	String atrist;
	boolean debug;
	DisplayListener displayListener;
	
    
	StdErrorReader(InputStream is, String type, DisplayListener displayListener, boolean debug)
	{
		this.is = is;
		this.type = type;
		this.displayListener = displayListener;
		this.debug = debug;
	}

	public void run()
	{
	try
	{
	    InputStreamReader isr = new InputStreamReader(is);
	    BufferedReader br = new BufferedReader(isr);
	    String line=null;
	    while ( (line = br.readLine()) != null)
	    {
		
		if (debug)
		{
			System.out.println(type + ">" + line); 
		}
		if (line.startsWith("Title") )
		{
			StringTokenizer st = new StringTokenizer(line, ":");
			if (st.hasMoreTokens()) 
			{
				st.nextToken();
				title = st.nextToken();
				displayListener.setDisplay(title);
			}
		}
	    }
	} catch (IOException ioe)
	    {
	    ioe.printStackTrace();  
	    }
	}
}
