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

import io.robusta.classify.business.UserBusiness;
import io.robusta.classify.domain.User;
import io.robusta.rra.cache.MyCache;
import io.robusta.rra.cache.RraCache;
import io.robusta.rra.controller.JaxRsController;
import io.robusta.rra.representation.Representation;
import io.robusta.rra.representation.implementation.GsonRepresentation;
import io.robusta.rra.representation.implementation.JacksonRepresentation;
import io.robusta.rra.resource.Resource;
import io.robusta.rra.resource.ResourceList;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

/**
 * Nicolas Zozol for Robusta Code 2014
 * 
 * @author Nicolas Zozol
 */
// @Path("user")
// @Produces( "application/json" )
public class UserController extends JaxRsController {

	UserBusiness userBusiness = new UserBusiness();

	@GET
	public String list() {
		String path = uriInfo.getPath();

		// EhCache
		Ehcache rraCache = RraCache.getCache("rraCache");
		Element element = rraCache.get(path);
		Representation representation = null;
		if (element == null) {
			ResourceList<Long, User> users = userBusiness.list();
			if (users != null) {
				representation = new GsonRepresentation(users);
				element = new Element(path, representation);
				rraCache.put(element);
			}
		} else {
			System.out.println(path + ": get from cache");
			representation = (Representation) element.getObjectValue();
		}
		RraCache.getInstance().displayCache();

		// MyCache
		Representation representation1 = MyCache.getInstance().get(path);
		if (representation1 == null) {
			ResourceList<Long, User> users = userBusiness.list();
			representation1 = new GsonRepresentation(users);
			MyCache.getInstance().put(path, representation1, users);
		}
		MyCache.getInstance().displayCache();

		if (representation != null) {
			return representation.toString();
		}
		// if ( representation1 != null ) {
		// return representation1.toString();
		// }
		return "";
	}

	@GET
	@Path("{user}")
	public String listByUser(@PathParam("user") Long userId) {

		String path = uriInfo.getPath();

		// EhCache
		Ehcache rraCache = RraCache.getCache("rraCache");
		Element element = rraCache.get(path);
		Representation representation = null;
		if (element == null) {
			Resource resource = userBusiness.find(userId);
			if (resource != null) {
				representation = new GsonRepresentation(resource);
				element = new Element(path, representation);
				rraCache.put(element);
			}
		} else {
			System.out.println(path + ": get from cache");
			representation = (Representation) element.getObjectValue();
		}
		RraCache.getInstance().displayCache();

		// MyCache
		Representation representation1 = MyCache.getInstance().get(path);
		if (representation1 == null) {
			Resource resource = userBusiness.find(userId);
			if (resource != null) {
				representation1 = new GsonRepresentation(resource);
				MyCache.getInstance().put(path, representation1, resource);
				representation1.remove("email");
			}
		}
		MyCache.getInstance().displayCache();

		if (representation != null) {
			return representation.toString();
		}
		// if ( representation1 != null ) {
		// return representation1.toString();
		// }
		return "";

	}

	@POST
	@Path("createJson")
	@Consumes("application/json")
	public String createUserJson(String json) {
		GsonRepresentation rep = new GsonRepresentation(json);
		User u = rep.get(User.class);
		userBusiness.add(u);
		// Cache.getInstance().invalidate( "users" );
		return rep.toString();
	}

	@POST
	@Path("createForm")
	@Consumes("application/x-www-form-urlencoded")
	public String createUserForm(@FormParam("id") Long id, @FormParam("name") String name,
			@FormParam("email") String email) {
		User newUSer = new User(id, name, email);
		System.out.println(id);
		GsonRepresentation rep = new GsonRepresentation(newUSer);
		userBusiness.add(newUSer);
		// Cache.getInstance().invalidate( "users" );
		return rep.toString();

	}

	@PUT
	@Path("update")
	@Consumes("application/json")
	public String updateUser(String json) {
		// GsonRepresentation rep = new GsonRepresentation( json );
		JacksonRepresentation rep = new JacksonRepresentation(json);
		User u = rep.get(User.class);
		userBusiness.set(u);
		rep.set("new champ", "champ");
		MyCache.getInstance().invalidate("user:" + u.getId());
		MyCache.getInstance().displayCache();
		return rep.toString();
	}

	@DELETE
	@Path("delete/{user}")
	public String deleteUser(@PathParam("user") Long userId) {
		if (!userBusiness.userExists(userId)) {
			throw new IllegalArgumentException();
		}
		User u = userBusiness.find(userId);
		userBusiness.delete(u);
		GsonRepresentation rep = new GsonRepresentation(u);
		MyCache.getInstance().invalidate("user:" + u.getId());
		MyCache.getInstance().displayCache();
		return rep.toString();
	}
}
