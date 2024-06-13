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
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(asyncSupported=true, urlPatterns="/MainServlet")
public class MainServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public MainServlet() {    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletOutputStream outputStream = response.getOutputStream();
		this.displayTestInformation(outputStream);
		outputStream.println("---------------------------------------------------</BR>");
		outputStream.println("Executing the servlet's service method</BR>");
		outputStream.println("The servlet name is " + this.getServletName()+"</BR>");
		//put this servlet into async mode
		AsyncContext myAsyncContext = request.startAsync();
		try {
			//normally you don't need to pass the printWriter to the runnable
			//it is only being passed to log what the servlet is doing
			myAsyncContext.start(new MyAsyncRunnable(myAsyncContext, outputStream));
		} catch (Throwable th) {
			outputStream.println("Caught Throwable " + th.getLocalizedMessage()+"</BR>");
		}
		//if you would like to see that the runnable is running asynchronously, 
		//you can add a sleep in here and then print some more content
		//also, note that this method must complete before a dispatch can be done from the asynchronous thread
		/*Float value = new Random().nextFloat() * 1000;
		try {
			outputStream.println("sleeping for " + value + " milliseconds in the main servlet</BR>");
			Thread.sleep(value.longValue());
		} catch (InterruptedException e) {
			outputStream.println("problem in MainServlet</BR>");
			e.printStackTrace();
		}*/	
		outputStream.println("Exiting out of the main servlet's service method</BR>");
	}
	
	private void displayTestInformation(ServletOutputStream output) throws IOException {
		output.println("<HTML><TITLE>Asyncronous Servlet Demonstration</TITLE></H1> " +
				"<BODY bgcolor=\"FFFFEE\">" +
				"<H1>Asyncronous Servlet Demonstration</H1>" +
				"<P>" +
				"<B>This servlet kicks off another thread which is executing asynchronously</BR>" +
				"The other thread invokes a dispatch and prints out more information to this response" +
				"</BR>" +
				"See the source code for an understanding of how it was done.</B>" +
				"</BR>" +
				"<B>The output of the response is:</B>" +
				"</BR>" +
				"<table BORDER=\"2\" WIDTH=\"65%\" BGCOLOR=\"#DDDDFF\"><tr><td><p>");
	}

}
