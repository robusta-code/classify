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

import io.robusta.classify.business.AdBusiness;
import io.robusta.classify.business.UserBusiness;
import io.robusta.classify.domain.Ad;
import io.robusta.rra.controller.JaxRsController;
import io.robusta.rra.representation.Representation;
import io.robusta.rra.representation.implementation.GsonRepresentation;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

/**
 * Nicolas Zozol for Robusta Code 2014
 *
 * @author Nicolas Zozol
 */

@Path("ad")
// @Produces( "application/json" )
public class AdController extends JaxRsController {

	AdBusiness adBusiness = new AdBusiness();
	UserBusiness userBusiness = new UserBusiness();

	@GET
	public String list() {
		Representation representation;
		List<Ad> ads = (List<Ad>) adBusiness.list();
		representation = new GsonRepresentation(ads);
		return representation.toString();
	}

	@GET
	@Path("{ad}")
	public String listByAd(@PathParam("ad") Long adId) {
		Ad ad = adBusiness.find(adId);
		Representation representation = new GsonRepresentation(ad);

		return representation.toString();

	}

	@POST
	@Path("create")
	@Consumes("application/json")
	public String createAdJson(String json) {
		GsonRepresentation rep = new GsonRepresentation(json);
		Ad ad = rep.get(Ad.class);
		adBusiness.add(ad);
		return rep.toString();
	}

	@PUT
	@Path("update")
	@Consumes("application/json")
	public String addAd(String json) {
		GsonRepresentation rep = new GsonRepresentation(json);
		Ad ad = rep.get(Ad.class);
		adBusiness.set(ad);
		rep.set("new champ", "champ");
		return rep.toString();
	}

	@DELETE
	@Path("delete/{ad}")
	public String deleteAd(@PathParam("ad") Long adId) {
		Ad ad = adBusiness.find(adId);
		adBusiness.delete(ad);
		GsonRepresentation rep = new GsonRepresentation(ad);
		return rep.toString();
	}

}
