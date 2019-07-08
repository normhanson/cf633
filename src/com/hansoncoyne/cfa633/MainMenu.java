package com.hansoncoyne.cfa633;

public class MainMenu implements Menu
{
	private static MainMenu instance;
	private static String[]  menu = {"MP3's", "Playlists", "Configuration"};
	private int index = 0;
	private ProcessWriter processWriter;
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
	public void enter()
	{
	}
	public void stop()
	{
		try
		{
			processWriter.stop();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	public String getLine()
	{
		return menu[index];
	}
	public Menu getNextMenu()
	{
		Menu menu;
		switch (index)
		{
			case 0:
				Mp3Menu mp3 = Mp3Menu.getInstance();
				mp3.setRoot();
				menu = (Menu) mp3;
				break;
			case 2:
				menu = (Menu) ConfigMenu.getInstance();
				break;
			default:
				menu = (Menu) MainMenu.getInstance();
				break;
		}
		displayListener.setDisplay(menu.getLine());
		return menu;
	}
	public Menu getPrevMenu()
	{
		return (Menu) this;
	}
	public static MainMenu getInstance()
	{
		if (instance==null) instance = new MainMenu();
		return instance;
	}
	private MainMenu()
	{

	}
	public void setProcessWriter(ProcessWriter arg)
	{
		processWriter = arg;
	}
	public void setDisplayListener(DisplayListener displayListener)
	{
		this.displayListener = displayListener;
	}

}
