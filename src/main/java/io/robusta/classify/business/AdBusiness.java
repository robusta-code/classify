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

package io.robusta.classify.business;

import io.robusta.classify.ClassifyDataSource;
import io.robusta.classify.domain.Ad;
import io.robusta.classify.domain.User;
import io.robusta.rra.resource.ResourceCollectionWrapper;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Nicolas Zozol for Robusta Code 2014
 * 
 * @author Nicolas Zozol
 */
public class AdBusiness {

    ClassifyDataSource ds = ClassifyDataSource.getInstance();

    public Collection<Ad> list() {
        return ds.getAds();
    }

    public Collection<Ad> list( User user ) {

        if ( user == null ) {
            throw new IllegalArgumentException( "User shall not be null at thus point" );
        }
        Collection<Ad> result = new ArrayList<Ad>();

        for ( Ad ad : ds.getAds() ) {
            if ( user.equals( ad.getGuy() ) ) {
                result.add( ad );
            }
        }
        return result;
    }

    public Ad find( long id ) {
        ResourceCollectionWrapper<Long, Ad> wrapper = new ResourceCollectionWrapper( ds.getAds() );
        return wrapper.getById( id );

    }

}
