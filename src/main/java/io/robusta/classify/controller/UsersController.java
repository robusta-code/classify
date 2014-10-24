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
import io.robusta.rra.controller.JaxRsController;
import io.robusta.rra.representation.Representation;
import io.robusta.rra.representation.implementation.GsonRepresentation;
import io.robusta.rra.resource.Resource;
import io.robusta.rra.resource.ResourceList;

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
@Path( "users" )
// @Produces( "application/json" )
public class UsersController extends JaxRsController {

    UserBusiness userBusiness = new UserBusiness();

    @GET
    public String list() {
        String path = uriInfo.getPath();

        // MyCache
        Representation representation = MyCache.getInstance().get( path );
        if ( representation == null ) {
            ResourceList<Long, User> users = userBusiness.list();
            representation = new GsonRepresentation( users );
            MyCache.getInstance().put( path, representation, users );
        }
        MyCache.getInstance().displayCache();

        if ( representation != null ) {
            return representation.toString();
        }
        return "";
    }

    @GET
    @Path( "{user}" )
    public String listByUser( @PathParam( "user" ) Long userId ) {

        String path = uriInfo.getPath();

        // MyCache
        Representation representation = MyCache.getInstance().get( path );
        if ( representation == null ) {
            Resource resource = userBusiness.find( userId );
            if ( resource != null ) {
                representation = new GsonRepresentation( resource );
                MyCache.getInstance().put( path, representation, resource );
            }
        }
        MyCache.getInstance().displayCache();

        if ( representation != null ) {
            return representation.toString();
        }
        return "";

    }

    @POST
    @Consumes( "application/json" )
    public String createUserJson( String json ) {
        String path = uriInfo.getPath();
        GsonRepresentation representation = new GsonRepresentation( json );
        User u = representation.get( User.class );
        userBusiness.add( u );
        MyCache.getInstance().invalidate( path );
        return representation.toString();
    }

    @PUT
    @Path( "{user}" )
    @Consumes( "application/json" )
    public String updateUser( @PathParam( "user" ) Long userId, String json ) {
        GsonRepresentation representation = new GsonRepresentation( json );
        User u = representation.get( User.class );
        userBusiness.set( u );
        MyCache.getInstance().invalidate( "user:" + u.getId() );
        MyCache.getInstance().displayCache();
        return representation.toString();
    }

    @DELETE
    @Path( "{user}" )
    public String deleteUser( @PathParam( "user" ) Long userId ) {
        if ( !userBusiness.userExists( userId ) ) {
            throw new IllegalArgumentException();
        }
        User u = userBusiness.find( userId );
        userBusiness.delete( u );
        GsonRepresentation rep = new GsonRepresentation( u );
        MyCache.getInstance().invalidate( "user:" + u.getId() );
        MyCache.getInstance().displayCache();
        return rep.toString();
    }
}
