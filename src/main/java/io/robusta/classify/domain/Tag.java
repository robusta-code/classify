package io.robusta.classify.domain;

import io.robusta.rra.Resource;

import java.util.Map;

/**
 * Created by dev on 29/08/14.
 */
public class Tag implements Resource<Long>{

    long id;
    String name;

    public Tag(long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public String getPrefix() {
        return "tag";
    }

    @Override
    public String getCollectionPrefix() {
        return "tags";
    }

    @Override
    public Map<String, Object> serialize() {
        return null;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
