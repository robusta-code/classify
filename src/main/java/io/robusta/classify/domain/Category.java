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

import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * Nicolas Zozol for Robusta Code 2014
 * 
 * @author Nicolas Zozol
 */
public class Category implements Resource<Long>, Comparable<Category> {

    long              id;
    String            name;
    TreeSet<Category> children;

    public Category() {
        super();
    }

    public Category( long id, String name ) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String getPrefix() {
        return "category";
    }

    @Override
    public String getCollectionPrefix() {
        return "categories";
    }

    @Override
    public Map<String, Object> serialize() {
        return null;
    }

    public void setId( long id ) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public Set<Category> getChildren() {
        return children;
    }

    public void addChildren( Category... categories ) {
        if ( this.children == null ) {
            this.children = new TreeSet<Category>();
        }
        for ( Category c : categories ) {
            this.children.add( c );
        }
    }

    @Override
    public int compareTo( Category category ) {
        return this.toString().compareTo( category.toString() );
    }

    @Override
    public String toString() {
        return this.name;
    }
}
