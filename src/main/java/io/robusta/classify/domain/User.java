package io.robusta.classify.domain;

import io.robusta.rra.Resource;

import java.util.Map;

/**
 * Created by dev on 29/08/14.
 */
public class User implements Resource<Long>{


    Long id;
    String email;
    String name;

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public String getPrefix() {
        return "user";
    }

    @Override
    public String getCollectionPrefix() {
        return "users";
    }

    @Override
    public Map<String, Object> serialize() {
        return null;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
