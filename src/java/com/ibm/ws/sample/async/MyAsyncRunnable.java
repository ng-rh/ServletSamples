/*COPYRIGHT_START***********************************************************
*
* COPYRIGHT LICENSE: This information contains sample code provided in source
* code form.  You may copy, modify, and distribute these sample programs in any
* form without payment to IBM for the purposes of developing, using, marketing 
* or distributing application programs conforming to the application programming 
* interface for the operating platform for which the sample code is written. 
* Notwithstanding anything to the contrary, IBM PROVIDES THE SAMPLE SOURCE CODE 
* ON AN "AS IS" BASIS AND IBM DISCLAIMS ALL WARRANTIES, EXPRESS OR IMPLIED, 
* INCLUDING, BUT NOT LIMITED TO, ANY IMPLIED WARRANTIES OR CONDITIONS OF 
* MERCHANTABILITY, SATISFACTORY QUALITY, FITNESS FOR A PARTICULAR PURPOSE, 
* TITLE, AND ANY WARRANTY OR CONDITION OF NON-INFRINGEMENT. IBM SHALL NOT BE 
* LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL OR CONSEQUENTIAL DAMAGES 
* ARISING OUT OF THE USE OR OPERATION OF THE SAMPLE SOURCE CODE. IBM HAS NO 
* OBLIGATION TO PROVIDE MAINTENANCE, SUPPORT, UPDATES, ENHANCEMENTS OR 
* MODIFICATIONS TO THE SAMPLE SOURCE CODE.

* (C) Copyright IBM Corp. 2011.
* All Rights Reserved. Licensed Materials - Property of IBM.
*
*COPYRIGHT_END*************************************************************/
package com.ibm.ws.sample.async;

import java.io.IOException;
import java.util.Random;

import javax.servlet.AsyncContext;
import javax.servlet.ServletOutputStream;

public class MyAsyncRunnable implements Runnable {

	AsyncContext ac;
	ServletOutputStream outputStream;
	
	public MyAsyncRunnable(AsyncContext ac, ServletOutputStream outputStream) {
		this.ac=ac;
		this.outputStream=outputStream;
	}
	
	public void run() {
		String prefix = "     ";
		//normally you should NOT write anything out to the printWriter here, but wait for the dispatch
		try {
			outputStream.println(prefix+"~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~</BR>");
			outputStream.println(prefix+"starting the asynchronous runnable</BR>");
			//sleeping for a while to simulate a long running asynchronous process
			Float value = new Random().nextFloat() * 3000;
			try {
				outputStream.println("sleeping in the asynchronous runnable</BR>");
				Thread.sleep(value.longValue());
			} catch (InterruptedException e) {
				outputStream.println("problem in runnable</BR>");
				e.printStackTrace();
			}	
			outputStream.println(prefix+"setting a request attribute within the asynchronous runnable</BR>");
			ac.getRequest().setAttribute("runnableExecuted", "true");
			outputStream.println(prefix+"dispatching to /DispatchOne</BR>");
			outputStream.println(prefix+"finished runnable</BR>");
			outputStream.println(prefix+"~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~</BR>");
			ac.dispatch("/DispatchOne");
			//don't need to call complete as a dispatch was done
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
