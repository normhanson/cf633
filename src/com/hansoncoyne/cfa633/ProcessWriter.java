package com.hansoncoyne.cfa633;

import java.util.*;
import java.io.*;

public class ProcessWriter extends Object
{
	PrintWriter prcOut;

	ProcessWriter(OutputStream out) throws Exception
	{
		prcOut = new PrintWriter( new OutputStreamWriter ( out ) );
	}
	public void load(String loadThis) throws Exception
	{
		prcOut.println("LOAD " + loadThis);
		prcOut.flush();
	}
	public void stop() throws Exception
	{
		prcOut.println("STOP");
		prcOut.flush();
	}
}

