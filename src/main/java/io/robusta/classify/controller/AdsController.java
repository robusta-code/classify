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
import io.robusta.classify.domain.Ad;
import io.robusta.rra.cache.MyCache;
import io.robusta.rra.controller.JaxRsController;
import io.robusta.rra.representation.Representation;
import io.robusta.rra.representation.RepresentationException;
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
 * @author Jean-Marc Villatte
 */

@Path( "ads" )
// @Produces( "application/json" )
public class AdsController extends JaxRsController {

    AdBusiness adBusiness = new AdBusiness();

    @GET
    public String list() {
        String path = uriInfo.getPath();

        // MyCache
        Representation representation = MyCache.getInstance().get( path );
        if ( representation == null ) {
            ResourceList<Long, Ad> ads = adBusiness.list();
            representation = new GsonRepresentation( ads );
            MyCache.getInstance().put( path, representation, ads );
        }
        MyCache.getInstance().displayCache();

        if ( representation != null ) {
            return representation.toString();
        }
        return "";
    }

    @GET
    @Path( "{ad}" )
    public String listByAd( @PathParam( "ad" ) Long adId ) {

        String path = uriInfo.getPath();

        // MyCache
        Representation representation = MyCache.getInstance().get( path );
        if ( representation == null ) {
            Resource resource = adBusiness.find( adId );
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
    public String createAdJson( String json ) {
        String path = uriInfo.getPath();
        GsonRepresentation representation = new GsonRepresentation( json );
        Ad a;
        try {
            a = representation.get( Ad.class );
        } catch ( RepresentationException e ) {
            return "";
        }
        adBusiness.add( a );
        MyCache.getInstance().invalidate( path );
        return representation.toString();
    }

    @PUT
    @Path( "{ad}" )
    @Consumes( "application/json" )
    public String updateAd( @PathParam( "ad" ) Long adId, String json ) {
        GsonRepresentation representation = new GsonRepresentation( json );
        Ad a = representation.get( Ad.class );
        adBusiness.set( a );
        MyCache.getInstance().invalidate( "ad:" + a.getId() );
        MyCache.getInstance().displayCache();
        return representation.toString();
    }

    @DELETE
    @Path( "{ad}" )
    public String deleteAd( @PathParam( "ad" ) Long adId ) {
        if ( !adBusiness.adExists( adId ) ) {
            throw new IllegalArgumentException();
        }
        Ad a = adBusiness.find( adId );
        adBusiness.delete( a );
        GsonRepresentation rep = new GsonRepresentation( a );
        MyCache.getInstance().invalidate( "ad:" + a.getId() );
        MyCache.getInstance().displayCache();
        return rep.toString();
    }
}
