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

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * Nicolas Zozol for Robusta Code 2014
 * 
 * @author Nicolas Zozol
 */
public class Section implements Resource<String> {

    String        name;
    Set<Category> categories = new TreeSet<Category>();

    public Section() {
        super();
    }

    public Section( String name ) {
        this.name = name;
    }

    @Override
    public String getId() {
        return name;
    }

    @Override
    public String getPrefix() {
        return "section";
    }

    @Override
    public String getCollectionPrefix() {
        return "sections";
    }

    @Override
    public Map<String, Object> serialize() {
        return null;
    }

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void addCategories( Category... categories ) {
        Collections.addAll( this.categories, categories );
    }
}
