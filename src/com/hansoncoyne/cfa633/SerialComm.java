package com.hansoncoyne.cfa633;

import com.hansoncoyne.cfa633.CRC16;

/**
* singleton class used to create the comm messages
*/

public class SerialComm extends Object
{
	public static final byte PING 		= 0x00;
	public static final byte HW_FW	 	= 0x01;
	public static final byte REBOOT 	= 0x05;
	public static final byte CLEARLCD 	= 0x06;
	public static final byte SETLINE1 	= 0x07;
	public static final byte SETLINE2 	= 0x08;

	private static final String SPACE 	= "                ";

	public static final byte[] simpleCommand(byte command)
	{	byte[] data = new byte[4];
		data[0] = command;
		data[1] = 0x00;
		int crc16 = CRC16.compute(data,2);
		data[2] = (byte)(crc16 & 0xff);
		data[3] = (byte)((crc16>>8) & 0xff);
		return data;
	}
	public static final byte[] setTextCommand(byte command, String text)
	{
		byte[] data = new byte[20];
		data[0] = command;
		data[1] = 0x10;
		byte[] textByte = textBytes(text);
		for (int i=0; i < (textByte.length>16?16:textByte.length); i++) data[i+2] = textByte[i];
		int crc16 = CRC16.compute(data,18);
		data[18] = (byte)(crc16 & 0xff);
		data[19] = (byte)((crc16>>8) & 0xff);
		return data;

	}
	private static final byte[] textBytes(String arg)
	{
		String newString = null;
		int len = arg.length();
		if ( len == 16 )
		{
			newString = arg;
		}
		else if (len > 16)
		{
			newString = arg.substring(0, 16);
		}
		else
		{
			StringBuffer paddedString = new StringBuffer();
			paddedString.append(arg);
			paddedString.append(SPACE);
			newString = paddedString.substring(0, 16);
		}
		return newString.getBytes();
	}
}
