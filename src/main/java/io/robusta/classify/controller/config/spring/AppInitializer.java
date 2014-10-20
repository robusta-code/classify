/*
 * Copyright (c) 2014. by Robusta Code and individual contributors
 *  as indicated by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package io.robusta.classify.controller.config.spring;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;


/**
 * Nicolas Zozol for Robusta Code 2014
 * 
 * @author Stephanie Pitou
 */
public class AppInitializer implements WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {

		// create spring root application context
		AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
		rootContext.register(ApplicationConfig.class);

		// add this root context to the servlet context as listener
		servletContext.addListener(new ContextLoaderListener(rootContext));

		// create spring servlet context
		AnnotationConfigWebApplicationContext servletConfig = new AnnotationConfigWebApplicationContext();
		servletConfig.register(WebConfig.class);

		// add this context to servlet context as servlet
		ServletRegistration.Dynamic registration = servletContext.addServlet("dispatcher", new DispatcherServlet(
				servletConfig));
		registration.setLoadOnStartup(1);
		// the below line is mapping servlet to "/spring" path
		registration.addMapping("/spring/*");

	}

}
