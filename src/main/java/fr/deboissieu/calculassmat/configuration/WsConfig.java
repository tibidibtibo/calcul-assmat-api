package fr.deboissieu.calculassmat.configuration;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

@Configuration
@ApplicationPath("api")
public class WsConfig extends ResourceConfig {

	public WsConfig() {
		packages("fr.deboissieu.calculassmat.api");
	}

}