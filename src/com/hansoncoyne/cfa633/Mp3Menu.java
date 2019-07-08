package com.hansoncoyne.cfa633;

import java.io.*;
import java.util.*;
import com.hansoncoyne.cfa633.Mp3Player;

public class Mp3Menu implements Menu
{
	private static Mp3Menu instance;
	private File mp3Root;
	private File file;
	private String[]  menu;
	private int index = 0;
	private DisplayListener displayListener;

	public void next()
	{
		index++;
		index = index >=menu.length?0:index;
		displayListener.setDisplay(menu[index]);
	}
	public void previous()
	{
		index--;
		index = index < 0?menu.length-1:index;
		displayListener.setDisplay(menu[index]);
	}
	public void stop()
	{
		Mp3Player.getInstance().stop();
	}
	public void enter()
	{
		File selectedFile = new File(file, menu[index]);
		Mp3Player.getInstance().play(selectedFile);
	}
	public String getLine()
	{
		return menu[index];
	}
	public void setMp3Root(String arg)
	{
		mp3Root = new File(arg);
	}
	public void setRoot()
	{
		file = mp3Root;
		menu = file.list();
		Arrays.sort(menu);
	}
	public static Mp3Menu getInstance()
	{
		if (instance==null) instance = new Mp3Menu();
		return instance;
	}
	private Mp3Menu()
	{
	}
	public Menu getNextMenu()
	{
		File newFile = new File(file, menu[index]);
		if (newFile.isDirectory())
		{
			file = newFile;
			menu = file.list();
			Arrays.sort(menu);
			index = 0;
		}
		displayListener.setDisplay(menu[index]);
		return (Menu) instance;
	}
	public Menu getPrevMenu()
	{
		if (file.equals(mp3Root))
		{
			displayListener.setDisplay(MainMenu.getInstance().getLine());
			return (Menu) MainMenu.getInstance();
		}
		else
		{
			index = 0;
			String parentName = file.getName();
			File newFile = file.getParentFile();
			file = newFile;
			menu = file.list();
			Arrays.sort(menu);
			for (int i=0;i < menu.length; i++)
			{
				if (menu[i].equals(parentName)) index = i;
			}
			displayListener.setDisplay(menu[index]);
			return (Menu) instance;
		}
	}
	public void setDisplayListener(DisplayListener displayListener)
	{
		this.displayListener = displayListener;
	}
    

}
