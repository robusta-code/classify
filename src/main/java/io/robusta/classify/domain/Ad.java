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

package io.robusta.classify.domain;

import io.robusta.rra.resource.Resource;
import io.robusta.rra.resource.ResourceSerializer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

/**
 * Nicolas Zozol for Robusta Code 2014
 * 
 * @author Nicolas Zozol
 */
public class Ad implements Resource<Long>, Comparable<Ad> {

    long            id;
    User            guy;
    String          title;
    String          content;
    float           price;

    Category        category;
    Collection<Tag> tags = new ArrayList<Tag>();

    public Ad() {
    }

    public Ad( long id, User guy, String title, String content, float price, Category category ) {
        this.id = id;
        this.guy = guy;
        this.title = title;
        this.content = content;
        this.price = price;
        this.category = category;
    }

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public String getPrefix() {
        return "ad";
    }

    @Override
    public Map<String, Object> serialize() {
        return ResourceSerializer.serialize( this );
    }

    @Override
    public String getCollectionPrefix() {
        return "ads";
    }

    public void setId( long id ) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle( String title ) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent( String content ) {
        this.content = content;
    }

    public void addTags( Tag... tags ) {
        Collections.addAll( this.tags, tags );
    }

    public float getPrice() {
        return price;
    }

    public void setPrice( float price ) {
        this.price = price;
    }

    public User getGuy() {
        return guy;
    }

    public Category getCategory() {
        return category;
    }

    public Collection<Tag> getTags() {
        return tags;
    }

    @Override
    public String toString() {
        return "Ad [id=" + id + ", guy=" + guy + ", title=" + title + ", content=" + content + ", price=" + price
                + ", category=" + category + ", tags=" + tags + "]";
    }

    @Override
    public int compareTo( Ad ad ) {
        return ( (Float) this.price ).compareTo( ad.getPrice() );
    }
}
