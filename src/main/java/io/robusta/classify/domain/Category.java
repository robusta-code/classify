package io.robusta.classify.domain;

import io.robusta.rra.Resource;

import java.util.*;

/**
 * Created by dev on 29/08/14.
 */
public class Category implements Resource<Long>, Comparable<Category>{

    long id;
    String name;
    TreeSet<Category> children;


    public Category(long id, String name) {
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

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Category> getChildren() {
        return children;
    }

    public void addChildren(Category ...categories){
        if (this.children==null){
            this.children = new TreeSet<Category>();
        }
        for (Category c : categories){
            this.children.add(c);
        }
    }

    @Override
    public int compareTo(Category category) {
        return this.toString().compareTo(category.toString());
    }

    @Override
    public String toString() {
        return this.name;
    }
}
