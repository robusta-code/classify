package io.robusta.classify.controller;

import io.robusta.rra.controller.JaxRsController;
import io.robusta.rra.representation.implementation.GsonRepresentation;
import io.robusta.rra.representation.implementation.JacksonRepresentation;

import java.util.List;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * Created by Stephanie Pitou on 15/09/14.
 */
@Path( "jaxrs" )
public class JaxRsImpl extends JaxRsController {

    @Context
    Response    response;
    @Context
    Request     request;
    @Context
    HttpHeaders httpHeader;
    @Context
    UriInfo     uriInfo;

    public JaxRsImpl() {
        super();
        setClientProperty( new MyClientPropertyJaxRs() );
    }

    @GET
    @Path( "auth" )
    @Consumes( "application/json" )
    public Response getAuth() {
        String username = getBasicAuthentification()[0];
        String password = getBasicAuthentification()[1];

        System.out.println( "username basic authentification :::: " + username );
        System.out.println( "password basic authentification :::: " + password );

        return getBasicAuthentificationResponse();
    }

    @POST
    @Path( "/validate" )
    @Consumes( "application/json" )
    public Response validateRepresentation( String requestEntity ) {
        // decomment to override the current representation
        // JaxRsController.defaultRepresentation = new JacksonRepresentation();
        System.out.println( "representation " + getRepresentation( requestEntity ) );
        System.out.println( "Gson representation "
                + ( JaxRsController.defaultRepresentation instanceof GsonRepresentation ) );
        System.out.println( "Jackson representation "
                + ( JaxRsController.defaultRepresentation instanceof JacksonRepresentation ) );
        System.out.println( "validate " + validate( requestEntity, "email", "name" ) );

        return validateResponse( requestEntity, "email", "name" );
    }

    @POST
    @Path( "/agent" )
    public Response userAgentMethod( String requestEntity ) {
        System.out.println( "isChrome " + getClientProperty().isChrome( httpHeader ) );
        System.out.println( "isFirefox " + getClientProperty().isFirefox( httpHeader ) );
        System.out.println( "isTablet " + getClientProperty().isTablet( httpHeader ) );
        System.out.println( "isMobile " + getClientProperty().isMobile( httpHeader ) );
        System.out.println( "isWebKit " + getClientProperty().isWebKit( httpHeader ) );

        return Response.status( 200 ).entity( getUserAgent() ).build();
    }

    @GET
    @Path( "header" )
    public Response getHeader() {
        MultivaluedMap<String, String> requestHeaders = getHttpHeader().getRequestHeaders();
        Set<String> requestHeaderSet = requestHeaders.keySet();
        for ( String currentHeader : requestHeaderSet ) {
            System.out.println( currentHeader );
            System.out.println( requestHeaders.get( currentHeader ) );
        }

        List<String> connection = requestHeaders.get( "connection" );
        List<String> authorization = requestHeaders.get( "authorization" );
        List<String> userAgent = requestHeaders.get( "user-agent" );

        System.out.println( "connection " + connection );
        System.out.println( "authorization " + authorization );
        System.out.println( "user-agent " + userAgent );

        return Response.status( 200 ).entity( "connection :: " + connection + " -- user-agent :: " + userAgent )
                .build();
    }

    @POST
    @Path( "ad" )
    @Consumes( "application/json" )
    public GsonRepresentation createAd( GsonRepresentation representation ) {
        System.out.println( "created and saved in database ad : " + representation.toString() );
        return representation;
        // return Response.status(201).build();
    }

    @Override
    public MyClientPropertyJaxRs getClientProperty() {
        return (MyClientPropertyJaxRs) super.getClientProperty();
    }
}
