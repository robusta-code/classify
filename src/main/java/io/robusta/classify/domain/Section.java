package io.robusta.classify.domain;

import io.robusta.rra.Resource;

import java.util.*;

/**
 * Created by dev on 29/08/14.
 */
public class Section implements Resource<String>{

    String name;
    Set<Category> categories = new TreeSet<Category>();

    public Section(String name) {
        this.name=name;
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

    public void setName(String name) {
        this.name = name;
    }

    public Set<Category> getCategories() {
        return categories;
    }



    public void addCategories(Category... categories){
        Collections.addAll(this.categories, categories);
    }
}
