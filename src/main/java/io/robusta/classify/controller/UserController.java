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
import io.robusta.rra.cache.Cache;
import io.robusta.rra.controller.JaxRsController;
import io.robusta.rra.representation.Representation;
import io.robusta.rra.representation.implementation.GsonRepresentation;
import io.robusta.rra.representation.implementation.JacksonRepresentation;
import io.robusta.rra.resource.Resource;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
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
@Path( "user" )
// @Produces( "application/json" )
public class UserController extends JaxRsController {

    UserBusiness userBusiness = new UserBusiness();

    @GET
    public String list() {
        Representation representation;
        // Resource resource;
        // resource = Cache.getInstance().get( "users" );
        // System.out.println( Cache.getInstance().getAll() );
        // if ( resource != null ) {
        // representation = new GsonRepresentation( resource );
        // } else {
        // List<User> users = (List<User>) userBusiness.list();
        // Cache.getInstance().put( "users", users );
        // representation = new GsonRepresentation( users );
        // return rep.toString();
        // }
        List<User> users = (List<User>) userBusiness.list();
        // Cache.getInstance().put( "users", users );
        representation = new GsonRepresentation( users );
        return representation.toString();
    }

    @GET
    @Path( "{user}" )
    public String listByUser( @PathParam( "user" ) Long userId ) {
        Resource resource = Cache.getInstance().get( "user" + userId );
        if ( resource == null ) {
            if ( !userBusiness.userExists( userId ) ) {
                return "";
            }
            resource = userBusiness.find( userId );
            Cache.getInstance().put( resource );
        }
        Representation representation = new GsonRepresentation( resource );
        representation.remove( "email" );
        Cache.getInstance().displayCache();
        return representation.toString();
    }

    @POST
    @Path( "createJson" )
    @Consumes( "application/json" )
    public String createUserJson( String json ) {
        GsonRepresentation rep = new GsonRepresentation( json );
        User u = rep.get( User.class );
        userBusiness.add( u );
        // Cache.getInstance().invalidate( "users" );
        return rep.toString();
    }

    @POST
    @Path( "createForm" )
    @Consumes( "application/x-www-form-urlencoded" )
    public String createUserForm( @FormParam( "id" ) Long id, @FormParam( "name" ) String name,
            @FormParam( "email" ) String email ) {
        User newUSer = new User( id, name, email );
        System.out.println( id );
        GsonRepresentation rep = new GsonRepresentation( newUSer );
        userBusiness.add( newUSer );
        // Cache.getInstance().invalidate( "users" );
        return rep.toString();

    }

    @PUT
    @Path( "update" )
    @Consumes( "application/json" )
    public String updateUser( String json ) {
        // GsonRepresentation rep = new GsonRepresentation( json );
        JacksonRepresentation rep = new JacksonRepresentation( json );
        User u = rep.get( User.class );
        userBusiness.set( u );
        rep.set( "new champ", "champ" );
        Cache.getInstance().invalidate( "user" + u.getId() );
        Cache.getInstance().displayCache();
        return rep.toString();
    }

    @DELETE
    @Path( "delete/{user}" )
    public String deleteUser( @PathParam( "user" ) Long userId ) {
        if ( !userBusiness.userExists( userId ) ) {
            throw new IllegalArgumentException();
        }
        User u = userBusiness.find( userId );
        userBusiness.delete( u );
        GsonRepresentation rep = new GsonRepresentation( u );
        Cache.getInstance().invalidate( "user" + u.getId() );
        Cache.getInstance().displayCache();
        return rep.toString();
    }

}
