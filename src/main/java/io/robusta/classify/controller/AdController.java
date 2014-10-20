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
import io.robusta.rra.cache.MyCache;
import io.robusta.rra.cache.RraCache;
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

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

/**
 * Nicolas Zozol for Robusta Code 2014
 * 
 * @author Nicolas Zozol
 */

@Path("ad")
// @Produces( "application/json" )
public class AdController extends JaxRsController {

    AdBusiness   adBusiness   = new AdBusiness();
    UserBusiness userBusiness = new UserBusiness();

    @GET
    public String list() {
        String path = uriInfo.getPath();

        // EhCache
        Ehcache rraCache = RraCache.getCache( "rraCache" );
        Element element = rraCache.get( path );
        Representation representation = null;
        if ( element == null ) {
            ResourceList<Long, Ad> ads = adBusiness.list();
            if ( ads != null ) {
                representation = new GsonRepresentation( ads );
                element = new Element( path, representation );
                rraCache.put( element );
            }
        } else {
            System.out.println( path + ": get from cache" );
            representation = (Representation) element.getObjectValue();
        }
        RraCache.getInstance().displayCache();

        // MyCache
        Representation representation1 = MyCache.getInstance().get( path );
        if ( representation1 == null ) {
            ResourceList<Long, Ad> ads = adBusiness.list();
            if ( ads != null ) {
                representation1 = new GsonRepresentation( ads );
                MyCache.getInstance().put( path, representation1, ads );
            }
        }
        MyCache.getInstance().displayCache();

        if ( representation != null ) {
            return representation.toString();
        }
        // if ( representation1 != null ) {
        // return representation1.toString();
        // }
        return "";
    }

    @GET
    @Path( "{ad}" )
    public String listByAd( @PathParam( "ad" ) Long adId ) {

        String path = uriInfo.getPath();

        // EhCache
        Ehcache rraCache = RraCache.getCache( "rraCache" );
        Element element = rraCache.get( path );
        Representation representation = null;
        if ( element == null ) {
            Resource resource = adBusiness.find( adId );
            if ( resource != null ) {
                representation = new GsonRepresentation( resource );
                element = new Element( path, representation );
                rraCache.put( element );
            }
        } else {
            System.out.println( path + ": get from cache" );
            representation = (Representation) element.getObjectValue();
        }
        RraCache.getInstance().displayCache();

        // MyCache
        Representation representation1 = MyCache.getInstance().get( path );
        if ( representation1 == null ) {
            Resource resource = adBusiness.find( adId );
            if ( resource != null ) {
                representation1 = new GsonRepresentation( resource );
                MyCache.getInstance().put( path, representation1, resource );
            }
        }
        MyCache.getInstance().displayCache();

        if ( representation != null ) {
            return representation.toString();
        }
        // if ( representation1 != null ) {
        // return representation1.toString();
        // }
        return "";
    }

    @POST
    @Path( "create" )
    @Consumes( "application/json" )
    public String createAdJson( String json ) {
        GsonRepresentation representation = new GsonRepresentation( json );
        Ad ad = representation.get( Ad.class );
        adBusiness.add( ad );
        return representation.toString();
    }

    @PUT
    @Path( "update" )
    @Consumes( "application/json" )
    public String addAd( String json ) {
        GsonRepresentation representation = new GsonRepresentation( json );
        Ad ad = representation.get( Ad.class );
        adBusiness.set( ad );
        representation.set( "new champ", "champ" );
        MyCache.getInstance().invalidate( "ad:" + ad.getId() );
        MyCache.getInstance().displayCache();
        return representation.toString();
    }

    @DELETE
    @Path( "delete/{ad}" )
    public String deleteAd( @PathParam( "ad" ) Long adId ) {
        Ad ad = adBusiness.find( adId );
        adBusiness.delete( ad );
        GsonRepresentation representation = new GsonRepresentation( ad );
        MyCache.getInstance().invalidate( "ad:" + ad.getId() );
        MyCache.getInstance().displayCache();
        return representation.toString();
    }
}
