package io.robusta.classify.controller.config.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

// spring servlet context
// all we have to add here is the compomnent scan to use @Annotation for all other components
@Configuration
@EnableWebMvc
@ComponentScan({"io.robusta.classify.controller"})
public class WebConfig {

}
