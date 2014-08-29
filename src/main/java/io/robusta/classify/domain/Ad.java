package io.robusta.classify.domain;


import io.robusta.rra.Resource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

/**
 * Created by dev on 29/08/14.
 */
public class Ad implements Resource<Long> {

    long id;
    User user;
    String title;
    String content;
    float price;

    Category category;
    Collection<Tag> tags = new ArrayList<Tag>();

    public Ad(long id, User user, String title, String content, float price, Category category) {
        this.id = id;
        this.user = user;
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
        return null;
    }

    @Override
    public String getCollectionPrefix() {
        return "ads";
    }


    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void addTags(Tag... tags){
        Collections.addAll(this.tags, tags);
    }


    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public User getUser() {
        return user;
    }

    public Category getCategory() {
        return category;
    }

    public Collection<Tag> getTags() {
        return tags;
    }
}
