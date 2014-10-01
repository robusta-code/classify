package io.robusta.classify.controller;

import io.robusta.rra.representation.implementation.GsonJaxRsProvider;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

/**
 * Created by dev on 16/09/14.
 */
@Path( "/myProvider" )
public class JaxRsProviderImpl extends GsonJaxRsProvider {

    @Context
    Response response;

    @POST
    @Path( "test" )
    public void myTest() {

        // System.out.println(response.readEntity(String.class));

        System.out.println( "Hello Provider" );

        /*
         * final GsonRepresentation myRepresentation = new
         * GsonRepresentation("bonjour"); //("posted MyBean", 11); WebTarget
         * webTarget = Response response =
         * webTarget.path("resource").request("application/xml")
         * .post(Entity.entity(myRepresentation, "application/xml"));
         * 
         * System.out.println(response.getStatus()); final String responseEntity
         * = response.readEntity(String.class);
         * System.out.println(responseEntity);
         */
    }

    @POST
    @Path( "entity" )
    public Response getEntity( String entity ) {
        return response.status( 401 ).entity( entity ).build();
    }

}
