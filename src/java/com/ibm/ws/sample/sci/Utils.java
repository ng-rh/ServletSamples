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
package com.ibm.ws.sample.sci;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Utils {
	public static final String OUTPUT_TEXT_ATTRIBUTE="OutputText";
	
	public static void appendLine(StringBuilder outputBuilder, String text) {
		outputBuilder.append(text);
		outputBuilder.append("</BR>");
		outputBuilder.append("\n");
	}
	
	public static StringBuilder getOutputBuilder(ServletRequest request) {
		StringBuilder outputBuilder = (StringBuilder) request.getAttribute(Utils.OUTPUT_TEXT_ATTRIBUTE);
		if (request.getAttribute(Utils.OUTPUT_TEXT_ATTRIBUTE)==null) {
			outputBuilder = new StringBuilder();
			request.setAttribute(Utils.OUTPUT_TEXT_ATTRIBUTE, outputBuilder);
		}
		return outputBuilder;
	}
	
	public static void printOutAttributes(HttpServletRequest request, StringBuilder outputBuilder, String... attributeNames) throws IOException {
		for (String s:attributeNames) {
			Utils.appendLine(outputBuilder, "attribute " + s + ":" + request.getAttribute(s));
		}
	}
	
	public static void printOutContextAttributes(HttpServletRequest request, StringBuilder outputBuilder, String... attributeNames) throws IOException {
		ServletContext context = request.getServletContext();
		for (String s:attributeNames) {
			Utils.appendLine(outputBuilder, "attribute " + s + ":" + context.getAttribute(s));
		}
	}
		
	public static void displayOutput(HttpServletRequest request, HttpServletResponse response) throws IOException {
		StringBuilder outputText = (StringBuilder)request.getAttribute(Utils.OUTPUT_TEXT_ATTRIBUTE);
		ServletOutputStream output = response.getOutputStream();
		output.println("<HTML>" +
				"<HEAD><TITLE>ServletContainerInitializer Demonstration</TITLE></H1> " +
				"<BODY bgcolor=\"FFFFEE\">" +
				"<H1>ServletContainerInitializer Demonstration</H1>" +
				"<P>" +
				"<B>This servlet, which was annotated, prints out attributes that were set by the </BR>" +
				"ServletContainerInitializer code.  The ServletContainerInitializer also adds a ServletContextListener</BR>" +
				"and a filter.</BR>" +
				"See the source code for an understanding of how it was done.</B>" +
				"</BR>" +
				"<B>The output of the response is:</B>" +
				"</BR>" +
				"<table BORDER=\"2\" WIDTH=\"65%\" BGCOLOR=\"#DDDDFF\"><tr><td><p>");
		output.println(outputText.toString());
		output.println("</p></td></tr></table>" +
				"</BODY>" +
				"</HTML>");
	}

}
