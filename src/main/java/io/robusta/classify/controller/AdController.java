package io.robusta.classify.controller;

import io.robusta.classify.ClassifyDataSource;
import io.robusta.classify.business.AdBusiness;
import io.robusta.classify.business.UserBusiness;
import io.robusta.classify.domain.Ad;
import io.robusta.classify.domain.User;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.Collection;

/**
 * Created by dev on 29/08/14.
 */
@Path("ad")
@Produces("application/json")
public class AdController {


    AdBusiness business = new AdBusiness();
    UserBusiness userBusiness = new UserBusiness();

    @GET
    public Collection<Ad> list(){
        return business.list();
    }

    @GET
    @Path("{user}")
    public Collection<Ad> listByUser(@PathParam("user") Long userId){

        if (!userBusiness.userExists(userId)){
            throw new IllegalArgumentException();
        }

        User u = userBusiness.find(userId);
        return business.list();
    }


}
