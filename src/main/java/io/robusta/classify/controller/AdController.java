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
import io.robusta.classify.domain.Category;
import io.robusta.classify.domain.User;
import io.robusta.rra.Representation;
import io.robusta.rra.representation.implementation.GsonRepresentation;
import io.robusta.rra.representation.implementation.JacksonRepresentation;
import io.robusta.rra.representation.implementation.XstreamRepresentation;

import javax.ws.rs.*;
import java.util.*;

/**
 * Nicolas Zozol for Robusta Code 2014
 *
 * @author Nicolas Zozol
 */
@Path("ad")
@Produces("application/json")
public class AdController /* Here should extends JaxRsController with all the cool features */ {


    AdBusiness business = new AdBusiness();
    UserBusiness userBusiness = new UserBusiness();

    @GET
    public Collection<Ad> list() {
        return business.list();
    }

    @GET
    @Path("user/{user}")
    public Collection<Ad> listByUser(@PathParam("user") Long userId) {

        if (!userBusiness.userExists(userId)) {
            throw new IllegalArgumentException();
        }

        User u = userBusiness.find(userId);
        return business.list();
    }

    /*@POST
    public void addAd(@FormParam("title") String title, @FormParam("content") String content){
        Ad ad = new Ad(45, userBusiness.find(1L), title, content, 12f, null);
        System.out.println(ad);
    }*/

    @POST
    @Path("add")
    public String addAd(@FormParam("id") String id,@FormParam("title") String title, @FormParam("content") String content, @FormParam("price") String price) {
        Ad ad = new Ad(Long.valueOf(id), userBusiness.find(1L), title, content, Float.valueOf(price), null);
        GsonRepresentation rep = new GsonRepresentation(ad.serialize());
        System.out.println(rep.set("new Champ", "champ"));

        return rep.toString();
    }

    @GET
    @Path("stream")
    public String getStream() {

        Ad ad = business.find(1L);
        XstreamRepresentation rep = new XstreamRepresentation(ad.serialize());
        //rep.remove("guy");

        return rep.toString();

    }

    @GET
    @Path("jack")
    public String getJack() {

        String user = "{\"email\":\"email\", \"name\":\"name\"}";
        int i =3;
        Hashtable<Integer, String> source = new Hashtable<Integer,String>();
        HashMap<Integer, String>  map = new HashMap(source);

        map.put(21, "Twenty One");
        map.put(22, "Twenty Two");
        map.put(23, "Twenty Threse");

        Ad ad = business.find(1L);
        Ad ad1 = business.find(2L);
        JacksonRepresentation rep = new JacksonRepresentation(ad);
        JacksonRepresentation rep2 = new JacksonRepresentation(user);
        JacksonRepresentation rep3 = new JacksonRepresentation(map);
        JacksonRepresentation rep4 = new JacksonRepresentation();
        JacksonRepresentation rep5 = new JacksonRepresentation(ad.serialize());
        JacksonRepresentation rep6 = new JacksonRepresentation(ad1.serialize());
        JacksonRepresentation rep7 = new JacksonRepresentation(i);
        rep4.createArray();
        rep4.addToArray(ad);
        rep4.addToArray(ad1);

        rep.set("new Champ", "champ");
        //rep.remove("guy");
       //rep.remove("category.name");
        //rep.set("toto",ad1);
        List<String> maList= new ArrayList<String>();
        maList.add("titi");
        maList.add("toto");
        maList.add("tata");
        rep.set("maList", maList);
        rep.add("maList","tutu");

        rep.addAll("maList",maList);

        Representation repMerge=rep5.merge("titi","toto",rep6);

        //return rep7.get(Integer.class).toString();

       // return repMerge.toString();

        //return rep.fetch("guy.email").toString();
        //return rep.copy().toString();


        return rep.getValues("maList").toString();
        //return rep.get("content").toString();
        //return rep.toString();
        //return rep4.pluck(Ad.class,"title").toString();
        //return rep2.set("toto",ad1).toString();
       // return rep4.toString();
        //return rep5.get(House.class).toString();
        //return rep.get(Ad.class).toString();
        //return rep2.get(User.class).toString();
        //return rep5.toString();
      /*  if (rep.hasPossiblyEmpty("title")){
            return "true";
        }else{
            return "false";
        }*/


    }

    @GET
    @Path("gson")
    public String getGson() {

        String user = "{\"email\":\"email\", \"name\":\"name\"}";
        int i =3;
        Hashtable<Integer, String> source = new Hashtable<Integer,String>();
        HashMap<Integer, String>  map = new HashMap(source);

        map.put(21, "Twenty One");
        map.put(22, "Twenty Two");
        map.put(23, "Twenty Three");

        Ad ad = business.find(1L);
        Ad ad1 = business.find(2L);
        GsonRepresentation rep = new GsonRepresentation(ad);
        GsonRepresentation rep2 = new GsonRepresentation(user);
        GsonRepresentation rep3 = new GsonRepresentation(map);
        GsonRepresentation rep4 = new GsonRepresentation();
        GsonRepresentation rep5 = new GsonRepresentation(ad.serialize());
        GsonRepresentation rep6 = new GsonRepresentation(ad1.serialize());
        GsonRepresentation rep7 = new GsonRepresentation(i);

        rep4.createArray();
        rep4.addToArray(ad);
        rep4.addToArray(ad1);

        rep.set("new Champ", "champ");
        //rep.remove("guy");
       // rep.remove("category.name");
        //rep.add("toto",ad1);
        List<String> maList= new ArrayList<String>();
        maList.add("titi");
        maList.add("toto");
        maList.add("tata");
        rep.set("maList", maList);
        rep.add("maList","tutu");

        rep.addAll("maList",maList);
        //return rep.get("content").toString();

        Representation repMerge=rep5.merge("titi","toto",rep6);

        //return rep7.get(Integer.class).toString();
        return rep.getValues("maList").toString();
        //return repMerge.toString();
        //return rep.fetch("guy.email").toString();
        //return rep.copy().toString();
        //return rep.toString();
        //return rep2.toString();
        //return rep4.get(Integer.class).toString();
        //return rep.get(Ad.class).toString();
        //return rep4.pluck(Ad.class,"title").toString();
        //return rep4.toString();
        /*if (rep.hasPossiblyEmpty("title")){
            return "true";
        }else{
            return "false";
        }*/

    }

    @GET
    @Path("{id}")
    public String findById(@PathParam("id") Long id) {
        //We should just make :`return new GsonRepresentation(business.find(id));`
        Ad ad = business.find(id);
        GsonRepresentation rep = new GsonRepresentation(ad.serialize());
        //rep.remove("guy");
        return rep.toString();
    }

    @GET
    @Path("merge/{id}")
    public String merge(@PathParam("id") Long id) {
        //We should just make :`return new GsonRepresentation(business.find(id));`
        Ad ad = business.find(id);
        GsonRepresentation rep = new GsonRepresentation(ad.serialize());
        //rep.remove("guy");

        Ad ad1 = business.find(2L);
        GsonRepresentation rep1 = new GsonRepresentation(ad1.serialize());

        Representation repMerge=rep.merge("titi","toto",rep1);
        return repMerge.toString();
    }


}
