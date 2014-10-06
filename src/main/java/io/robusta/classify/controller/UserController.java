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
import io.robusta.rra.controller.JaxRsController;
import io.robusta.rra.representation.implementation.GsonRepresentation;
import io.robusta.rra.representation.implementation.JacksonRepresentation;

import java.util.Collection;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 * Nicolas Zozol for Robusta Code 2014
 *
 * @author Nicolas Zozol
 */
@Path( "user" )
@Produces( "application/json" )
public class UserController extends JaxRsController {

    UserBusiness userBusiness = new UserBusiness();

    @GET
    public Collection<User> list() {
        return userBusiness.list();
    }

    @GET
    @Path( "{user}" )
    public String listByUser( @PathParam( "user" ) Long userId ) {

        if ( !userBusiness.userExists( userId ) ) {
            throw new IllegalArgumentException();
        }
        User u = userBusiness.find( userId );
        GsonRepresentation rep = new GsonRepresentation( u );
        // JacksonRepresentation rep = new JacksonRepresentation( u );
        rep.remove( "email" );
        return rep.toString();
    }

    @POST
    @Path( "createJson" )
    @Consumes( "application/json" )
    public String createUserJson( String json ) {
        GsonRepresentation rep = new GsonRepresentation( json );
        User user = rep.get( User.class );
        return user.toString();
    }

    @POST
    @Path( "createForm" )
    @Consumes( "application/x-www-form-urlencoded" )
    public String createUserForm( @FormParam( "id" ) Long id, @FormParam( "name" ) String name,
            @FormParam( "email" ) String email ) {
        User newUSer = new User( id, name, email );
        System.out.println( id );
        GsonRepresentation rep = new GsonRepresentation( newUSer );

        return rep.toString();

    }

    @PUT
    @Path( "update" )
    @Consumes( "application/json" )
    public String addAd( String json ) {
        // GsonRepresentation rep = new GsonRepresentation( json );
        JacksonRepresentation rep = new JacksonRepresentation( json );
        User user = rep.get( User.class );
        userBusiness.set( user );
        rep.set( "new champ", "champ" );
        return rep.toString();
    }

    @DELETE
    @Path( "delete/{user}" )
    public String deleteUser( @PathParam( "user" ) Long userId ) {

        if ( !userBusiness.userExists( userId ) ) {
            throw new IllegalArgumentException();
        }
        User u = userBusiness.find( userId );
        GsonRepresentation rep = new GsonRepresentation( u );
        return rep.toString();
    }

}
