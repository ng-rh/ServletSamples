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
package com.ibm.ws.sample.programmatic;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebListener;
@WebListener
public class BaseServletContextListener implements ServletContextListener {

    public BaseServletContextListener() {
    }

    public void contextDestroyed(ServletContextEvent event) {
    }

    public void contextInitialized(ServletContextEvent event) {
    	ServletContext context = event.getServletContext();

    	addServlets(context);
    	addFilters(context);
    	addListeners(context);
    }
    
    private void addServlets(ServletContext context) {
    	//adding a servlet programmatically using 
    	//ServletRegistration.Dynamic addServlet(java.lang.String servletName, java.lang.Class<? extends Servlet> servletClass)
    	ServletRegistration.Dynamic dynamic = context.addServlet("Servlet1Name", Servlet1.class);
		dynamic.addMapping("/Servlet1");
    		
    	//adding a servlet programmatically using
    	//ServletRegistration.Dynamic addServlet(java.lang.String servletName, Servlet servlet)
    	try {
			Servlet2 servlet2Class = context.createServlet(Servlet2.class);
			dynamic = context.addServlet("Servlet2Name", servlet2Class);
			dynamic.addMapping("/Servlet2");
		} catch (ServletException e) {
			e.printStackTrace();
		}
		
		//adding a servlet programmatically using
		//ServletRegistration.Dynamic addServlet(java.lang.String servletName, java.lang.String className)
		dynamic=context.addServlet("Servlet3Name", "com.ibm.ws.sample.programmatic.Servlet3");
		dynamic.addMapping("/Servlet3");
    }
    
    private void addFilters(ServletContext context) {
    	EnumSet<DispatcherType> dispatcherTypeSet = EnumSet.of(DispatcherType.REQUEST);
    	
    	//adding a filter programmatically using
		//FilterRegistration.Dynamic addFilter(java.lang.String filterName, java.lang.Class<? extends Filter> filterClass)
    	FilterRegistration.Dynamic dynamic1 = context.addFilter("Filter1Name", Filter1.class);
    	//adds a mapping for the url pattern matching "/Servlet1"
    	dynamic1.addMappingForUrlPatterns(dispatcherTypeSet, true, "/Servlet1");
    	
		//adding a filter programmatically using
		//FilterRegistration.Dynamic addFilter(java.lang.String filterName,Filter filter)
		try {
			Filter2 filter2Class = context.createFilter(Filter2.class);
			//you can add additional mappings, etc by using the FilterRegistration.dynamic object
			FilterRegistration.Dynamic dynamic2 = context.addFilter("Filter2Name", filter2Class);
			//adds a mapping for a servletName using Request as the dispatcherType
			dynamic2.addMappingForServletNames(dispatcherTypeSet, true, "Servlet2Name");
		} catch (ServletException e) {
			e.printStackTrace();
		}
		
		//adding a filter programmatically using
		//FilterRegistration.Dynamic addFilter(java.lang.String filterName, java.lang.String className)
		FilterRegistration.Dynamic dynamic3 = context.addFilter("Filter3Name", "com.ibm.ws.sample.programmatic.Filter3");
		dynamic3.addMappingForUrlPatterns(dispatcherTypeSet, true, "/Servlet3");
    }
    
    private void addListeners(ServletContext context) {
		//adding a listener programmatically using
		//void addListener(java.lang.Class<? extends java.util.EventListener> listenerClass)
		context.addListener(Listener1.class);    	
    	
		//adding a listener programmaticaly using		
		//<T extends java.util.EventListener> void addListener(T t)
		try {
			Listener2 listener2Class = context.createListener(Listener2.class);
			context.addListener(listener2Class);
		} catch (ServletException e) {
			e.printStackTrace();
		}
	
		//adding a listener programmaticaly using
		//void addListener(java.lang.String className)
		context.addListener("com.ibm.ws.sample.programmatic.Listener3");
		
		//in order to add a ServletContextListener programmatically, you need to use a ServletContainerIntiailizer
		//see the SampleServletContainerInitializer module
    }
	
}
