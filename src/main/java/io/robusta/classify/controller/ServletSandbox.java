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

import io.robusta.rra.controller.ServletController;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Nicolas Zozol for Robusta Code 2014
 * 
 * @author Stephanie Pitou, Jean-Marc Villatte
 *
 *         Created on 09/09/14.
 */
@WebServlet("/test")
public class ServletSandbox extends ServletController {

	@Override
	public void init() throws ServletException {
		super.init();
		// System.out.println("My application starts");
		setClientProperty(new MyClientPropertyServlet());
		// decomment to override Rra.defaultRepresentation (GsonRepresentation
		// by default)
		// Rra.defaultRepresentation = new JacksonRepresentation();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {

		String[] userPassword = getBasicAuthentication(request, response);
		if (userPassword[0] != null) {
			System.out.println("user " + userPassword[0].toString());
			System.out.println("Password " + userPassword[1].toString());
		}
		System.out.println("getRepresentation " + getRepresentation(request));
		System.out.println("validate " + validate(request, response, "ndame", "email"));
		System.out.println("isChrome()" + getClientProperty().isChrome(request));
		System.out.println("isFF()" + getClientProperty().isFF(request));
		response.getWriter().println(getRepresentation(request));

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println(getClientProperty().isChrome(request));
	}

	@Override
	public MyClientPropertyServlet getClientProperty() {
		return (MyClientPropertyServlet) super.getClientProperty();
	}

}
