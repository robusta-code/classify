package io.robusta.classify.business;

import io.robusta.classify.ClassifyDataSource;
import io.robusta.classify.domain.User;

/**
 * Created by dev on 29/08/14.
 */
public class UserBusiness {
    ClassifyDataSource ds = ClassifyDataSource.getInstance();

    public boolean userExists(long id){
        return ds.getUsers().containsById(id);
    }

    public User find(Long id) {
        return ds.getUsers().getById(id);
    }
}
