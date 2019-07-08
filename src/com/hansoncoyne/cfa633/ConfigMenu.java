package com.hansoncoyne.cfa633;

import java.io.*;
import java.util.*;

public class ConfigMenu implements Menu
{
	private static ConfigMenu instance;
	private String[]  menu = {"kill mpg123", "ifconfig"};
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
	}
	public void enter()
	{
		switch (index)
		{
			case 0:
				kill -9 `ps -opid -C mpg123`
				break;
			case 1:
				break;
			default:
				break;
		}
	}
	public String getLine()
	{
		return menu[index];
	}
	public static ConfigMenu getInstance()
	{
		if (instance==null) instance = new ConfigMenu();
		return instance;
	}
	private ConfigMenu()
	{
	}
	public Menu getNextMenu()
	{
		return (Menu) ConfigMenu.getInstance();
	}
	public Menu getPrevMenu()
	{
			displayListener.setDisplay(MainMenu.getInstance().getLine());
			return (Menu) MainMenu.getInstance();
	}
	public void setDisplayListener(DisplayListener displayListener)
	{
		this.displayListener = displayListener;
	}
}
