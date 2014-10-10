package io.robusta.classify.controller.config.spring;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class AppInitializer implements WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {

		// create spring root application context
		AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
		rootContext.register(ApplicationConfig.class);

		// add this root context to the servlet context as listener
		servletContext.addListener(new ContextLoaderListener(rootContext));

		// create spring servlet context
		AnnotationConfigWebApplicationContext servletConfig = new AnnotationConfigWebApplicationContext();
		servletConfig.register(WebConfig.class);

		// add this context to servlet context as servlet
		ServletRegistration.Dynamic registration = servletContext.addServlet("dispatcher", new DispatcherServlet(
				servletConfig));
		registration.setLoadOnStartup(1);
		// the below line is mapping servlet to "/spring" path
		registration.addMapping("/spring/*");

	}

}
