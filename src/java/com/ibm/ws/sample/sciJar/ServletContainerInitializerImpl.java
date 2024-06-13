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

import java.util.EnumSet;
import java.util.Set;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.HandlesTypes;

//since our HandlesTypes annotation contains the class Servlet, then all classes within the
//application that implement Servlet will be sent to the onStartup method below
@HandlesTypes(javax.servlet.Servlet.class)
public class ServletContainerInitializerImpl implements ServletContainerInitializer {

	@Override
	public void onStartup(Set<Class<?>> setOfClassesInterestedIn, ServletContext context) throws ServletException {
		//going to add a context attribute to show the set of classes that were passed in
		if (setOfClassesInterestedIn!=null) {
			context.setAttribute("SET_OF_SERVLETS_IN_APP", setOfClassesInterestedIn);
		} else {
			context.setAttribute("SET_OF_SERVLETS_IN_APP", "null");
		}
		
		//going to add a ServletContextListener programmatically
		context.addListener(com.ibm.ws.sample.sciJar.ServletContextListenerImpl.class);
		
		//going to add a Filter programmatically
		//if this jar is used as a shared library, then this filter will be applied to all requests
		FilterRegistration.Dynamic dynamic = context.addFilter("MySharedFilter", com.ibm.ws.sample.sciJar.SharedFilter.class);
		dynamic.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");		
	}

}
