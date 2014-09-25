package io.robusta.classify.controller;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Stephanie Pitou on 15/09/14.
 */
@ApplicationPath("res")
public class JaxRsTest extends Application {

    public Set<Class<?>> getClasses() {
        return new HashSet<Class<?>>(Arrays.asList(JaxRsImpl.class, JaxRsProviderImpl.class));
    }
}
