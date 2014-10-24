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
import io.robusta.classify.domain.User;
import io.robusta.rra.resource.ResourceList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Nicolas Zozol for Robusta Code 2014
 * 
 * @author Nicolas Zozol
 */
public class UserBusiness {
    ClassifyDataSource ds = ClassifyDataSource.getInstance();

    public boolean userExists( long id ) {
        return ds.getUsers().containsById( id );
    }

    public User find( Long id ) {

        return ds.getUsers().getById( id );
        // return ds.getUsers().getById( id );
    }

    public ResourceList<Long, User> list() {
        return ds.getUsers();
    }

    public User set( User user ) {
        ResourceList<Long, User> users = ds.getUsers();
        users.replace( user.getId(), user );
        return user;
    }

    public User add( User user ) {
        ResourceList<Long, User> users = ds.getUsers();
        List<Long> ids = new ArrayList<Long>();
        for ( User user2 : users ) {
            ids.add( user2.getId() );
        }
        long newId = 1;
        if ( ids.size() > 0 ) {
            newId = Collections.max( ids ) + 1;
        }
        user.setId( newId );
        users.add( user );
        return user;
    }

    public User delete( User user ) {
        ResourceList<Long, User> users = ds.getUsers();
        users.remove( user );
        return user;
    }

}
