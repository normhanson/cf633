package com.hansoncoyne.cfa633;

/*
 * Swing version
 */

import javax.swing.*;
import java.awt.Frame;
import java.awt.TextField;
import java.awt.Panel;
import java.awt.Canvas;
import java.awt.event.*;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Color;
import javax.comm.*;
import java.io.*;

public class CrystalFontDebugger implements KeyListener,WindowListener
{
    OutputStream out;
    TextField line1;
    TextField line2;

	public CrystalFontDebugger(SerialPort serialPort) throws Exception
	{
		Frame f = new Frame("Crystal Font LCD");

		line1 = new TextField(16);
		line1.setEditable(false);
		line1.addKeyListener(this);

		line2 = new TextField(16);
		line2.setEditable(false);
		line2.addKeyListener(this);
		Panel contentPane = new Panel();
		contentPane.setLayout(new BorderLayout());
		contentPane.add(line1, BorderLayout.NORTH);
		contentPane.add(line2, BorderLayout.SOUTH);
		contentPane.addKeyListener(this);

		f.add(contentPane);
		f.pack();
		f.setResizable(false);
		f.show();
		f.addWindowListener(this);

		out = serialPort.getOutputStream();
		out.write(SerialComm.simpleCommand(SerialComm.PING));

	}
    public void keyTyped(KeyEvent e) { }
    public void keyReleased(KeyEvent e) { }
    public void keyPressed(KeyEvent e)
    {
		try
		{
			switch (e.getKeyCode())
			{
				case KeyEvent.VK_UP:
					out.write((byte) 0x80);
					out.write((byte) 0x00);
					out.write((byte) 0x01);
				break;
				case KeyEvent.VK_DOWN:
					out.write((byte) 0x80);
					out.write((byte) 0x00);
					out.write((byte) 0x02);
				break;
				case KeyEvent.VK_LEFT:
					out.write((byte) 0x80);
					out.write((byte) 0x00);
					out.write((byte) 0x03);
				break;
				case KeyEvent.VK_RIGHT:
					out.write((byte) 0x80);
					out.write((byte) 0x00);
					out.write((byte) 0x04);
				break;
				case KeyEvent.VK_ENTER:
					out.write((byte) 0x80);
					out.write((byte) 0x00);
					out.write((byte) 0x05);
				break;
				case KeyEvent.VK_ESCAPE:
					out.write((byte) 0x80);
					out.write((byte) 0x00);
					out.write((byte) 0x06);
				break;
			}
		}
		catch (Exception ex) {}
	}
    public void windowOpened(WindowEvent e) {}
    public void windowClosing(WindowEvent e)
{System.exit(0);}
    public void windowClosed(WindowEvent e) {}
    public void windowIconified(WindowEvent e){}
    public void windowDeiconified(WindowEvent e) {}
    public void windowActivated(WindowEvent e) {}
    public void windowDeactivated(WindowEvent e) {}

    public void setLine(String line)
    {
		if (line.length() > 16)
		{
			line1.setText(line.substring(0,16));
			line2.setText(line.substring(16));
		}
		else
		{
			line1.setText(line);
			line2.setText("");
		}


	}


}
