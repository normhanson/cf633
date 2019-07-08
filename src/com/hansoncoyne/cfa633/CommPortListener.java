package com.hansoncoyne.cfa633;

import java.io.*;
import java.util.*;
import javax.comm.*;

public class CommPortListener implements Runnable, SerialPortEventListener {

    InputStream inputStream;
    SerialPort serialPort;
    KeyPadListener keyPadListener;

    public void run() {
        try 
	{
            Thread.sleep(200);
        } 
	catch (InterruptedException e) {}
    	}

    public void serialEvent(SerialPortEvent event) {
        switch(event.getEventType()) {
        case SerialPortEvent.BI:
        case SerialPortEvent.OE:
        case SerialPortEvent.FE:
        case SerialPortEvent.PE:
        case SerialPortEvent.CD:
        case SerialPortEvent.CTS:
        case SerialPortEvent.DSR:
        case SerialPortEvent.RI:
        case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
            break;
        case SerialPortEvent.DATA_AVAILABLE:
            byte[] readBuffer = new byte[200];
            try
            {
                while (inputStream.available() > 0) {
				int numBytes = inputStream.read(readBuffer);
				if (readBuffer[0] == (byte) 0x80 )
				{
					if (readBuffer[2] == (byte) 0x01) keyPadListener.keyPadUp();
					else if (readBuffer[2] == (byte) 0x02) keyPadListener.keyPadDown();
					else if (readBuffer[2] == (byte) 0x03) keyPadListener.keyPadLeft();
					else if (readBuffer[2] == (byte) 0x04) keyPadListener.keyPadRight();
					else if (readBuffer[2] == (byte) 0x05) keyPadListener.keyPadEnter();
					else if (readBuffer[2] == (byte) 0x06) keyPadListener.keyPadCancel();

				}
				else if (readBuffer[0] == (byte) 0x40 ){System.out.println ("normal ping response");}
				/* debugger events */
				else if (readBuffer[0] == SerialComm.PING ){ System.out.println ("PING LOOPBACK");}
				else if (readBuffer[0] == SerialComm.SETLINE1 ){ System.out.println ("SETLINE1 LOOPBACK");}
				else if (readBuffer[0] == SerialComm.SETLINE2 ){ System.out.println ("SETLINE2 LOOPBACK");}

		}
          }
            	catch (IOException e) {}

        }
    }
    public SerialPort getSerialPort()
    {
		return serialPort;
	}
	public void setKeyPadListener(KeyPadListener arg)
	{
		keyPadListener = arg;
	}
	public void setSerialPort(SerialPort arg) throws Exception
	{
			serialPort = arg;
			inputStream = serialPort.getInputStream();
			serialPort.addEventListener(this);
			serialPort.notifyOnDataAvailable(true);
	}
}
