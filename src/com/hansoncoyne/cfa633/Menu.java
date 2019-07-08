package com.hansoncoyne.cfa633;

public interface Menu
{
	public void next();
	public void previous();
	public void enter();
	public void stop();
	public String getLine();
	public Menu getNextMenu();
	public Menu getPrevMenu();
	public void setDisplayListener(DisplayListener displayListener);


}
