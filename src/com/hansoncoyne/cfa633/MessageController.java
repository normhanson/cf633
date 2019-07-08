package com.hansoncoyne.cfa633;

import java.io.*;
import java.util.*;
import javax.comm.*;
import com.hansoncoyne.cfa633.*;

public class MessageController extends Object 
	implements KeyPadListener, DisplayListener
{
	private boolean debug = false;
	private CommPortIdentifier portId;
	private SerialPort serialPort;
	private CommPortListener commListener;
	private CrystalFontDebugger debugger;
	private Menu menu;
	OutputStream out;

	public static void main (String[] args)
	{
		MessageController controller = new MessageController();
	}

	public MessageController()
	{
		try
		{
			/**
			 *  set up the serial port 
			 */
			portId = CommPortIdentifier.getPortIdentifier("/dev/ttyS1");
			serialPort = (SerialPort) portId.open("hansoncoyne", 2000);
			serialPort.setSerialPortParams(19200,
							SerialPort.DATABITS_8,
							SerialPort.STOPBITS_1,
							SerialPort.PARITY_NONE);
			commListener = new CommPortListener();
			commListener.setKeyPadListener((KeyPadListener) this);
			commListener.setSerialPort(serialPort);
			Thread listener = new Thread(commListener);
			listener.setPriority(Thread.MAX_PRIORITY);
			listener.start();
			out = serialPort.getOutputStream();

			/**
			 *  init the menus and the Mp3Player
			 */

			MainMenu mainMenu = MainMenu.getInstance();
			mainMenu.setDisplayListener(this);

			ConfigMenu configMenu = ConfigMenu.getInstance();
			configMenu.setDisplayListener(this);

			Mp3Menu mp3Menu = Mp3Menu.getInstance();
			mp3Menu.setDisplayListener(this);

			Mp3Player mp3Player = Mp3Player.getInstance();
			mp3Player.setDisplayListener(this);
			mp3Menu.setMp3Root("/mp3");
			menu = (Menu) mainMenu;

			/** 
			 * init the display 
			 */

			setDisplay(menu.getLine());
			System.out.println("cfa633-mpg123 ready...");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	public void keyPadLeft()
	{
		menu = menu.getPrevMenu();
	}
	public void keyPadRight()
	{
		menu = menu.getNextMenu();
	}
	public void keyPadUp()
	{
		menu.previous();
	}
	public void keyPadDown()
	{
		menu.next();
	}
	public void keyPadEnter()
	{
		menu.enter();
	}
	public void keyPadCancel()
	{
		menu.stop();
	}
	public void setDisplay(String text)
	{
		try
		{
			if (debug) debugger.setLine(text);
			if (text.length() > 16)
			{
				out.write(SerialComm.setTextCommand(SerialComm.SETLINE1, text.substring(0,16)));;
				out.write(SerialComm.setTextCommand(SerialComm.SETLINE2, text.substring(16)));
			}
			else
			{
				out.write(SerialComm.setTextCommand(SerialComm.SETLINE1, text));
				out.write(SerialComm.setTextCommand(SerialComm.SETLINE2, "                "));
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
