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
package com.ibm.ws.sample.sciJar;

import javax.servlet.ServletRequest;

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
	
}
