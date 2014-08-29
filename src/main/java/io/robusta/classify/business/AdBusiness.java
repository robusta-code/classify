package io.robusta.classify.business;

import io.robusta.classify.ClassifyDataSource;
import io.robusta.classify.domain.Ad;
import io.robusta.classify.domain.User;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by dev on 29/08/14.
 */
public class AdBusiness {

    ClassifyDataSource ds = ClassifyDataSource.getInstance();

    public Collection<Ad> list(){
        return ds.getAds();
    }

    public Collection<Ad> list(User user){

        if (user == null){
            throw new IllegalArgumentException("User shall not be null at thus point");
        }
        Collection<Ad> result = new ArrayList<Ad>();

        for (Ad ad : ds.getAds()){
            if (user.equals(ad.getUser())){
                result.add(ad);
            }
        }
        return result;
    }

}
