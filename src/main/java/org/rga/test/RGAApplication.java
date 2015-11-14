package org.rga.test;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.rga.test.rest.CustomerResource;
import org.springframework.web.filter.RequestContextFilter;

public class RGAApplication extends ResourceConfig {
	public RGAApplication() {
		// Register application resources
		register(CustomerResource.class);
		
		// register filters
		// which is a Spring filter that provides a bridge between JAX-RS and Spring request attributes
		register(RequestContextFilter.class);
				
		// register features
		// which is a feature that registers Jackson JSON providers � you need it for the application to understand JSON data
		register(JacksonFeature.class);
		
		// Login filter
		register(LoginFilter.class);
	}
}
