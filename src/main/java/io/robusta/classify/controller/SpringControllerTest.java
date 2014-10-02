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

package io.robusta.classify.controller;

import io.robusta.rra.controller.SpringController;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Handles requests for the application home page.
 */
// @Controller
public class SpringControllerTest extends SpringController {

	@Override
	public void init() {
		super.init();
		setClientProperty(new MyClientPropertyServlet());
		// decomment to override Rra.defaultRepresentation (GsonRepresentation
		// by default)
		// Rra.defaultRepresentation = new JacksonRepresentation();
	}

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/springJson", method = RequestMethod.POST)
	@ResponseBody
	public String springJson(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
		return getRepresentation(request).remove("email").toString();
	}

	@RequestMapping(value = "/spring", method = RequestMethod.POST)
	public String spring(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		String[] userPassword = getBasicAuthentication(request, response);
		if (userPassword[0] != null) {
			System.out.println("user=" + userPassword[0].toString());
			System.out.println("Password=" + userPassword[1].toString());
		}
		System.out.println("getRepresentation=" + getRepresentation(request));
		System.out.println("validate=" + validate(request, response, "namse", "emsail"));
		System.out.println("isChrome()=" + getClientProperty().isChrome(request));
		System.out.println("isFF()=" + getClientProperty().isFF(request));

		response.getWriter().println(getRepresentation(request).remove("email").toString());
		return null;
	}

	@RequestMapping(value = "/client", method = RequestMethod.GET)
	public String client(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		if (request.getParameter("p1") != null) {
			response.getWriter().println(request.getParameter("p1"));
		} else {
			response.getWriter().println("ok");
		}

		return null;
	}

	public void ok() {

	}

	@Override
	public MyClientPropertyServlet getClientProperty() {
		return (MyClientPropertyServlet) super.getClientProperty();
	}
}
