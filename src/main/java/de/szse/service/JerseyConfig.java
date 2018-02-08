package de.szse.service;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

/**
 * The Main class of the REST service application. It defines the root URI of the services and initialises all
 * resources.
 *
 * @author Stefan Zauner
 */
@Component
@ApplicationPath("services")
public class JerseyConfig extends ResourceConfig {

	/**
	 * Constructor of the Application. Initialises Jersey REST resources.
	 */
	public JerseyConfig() {
        /* From the Spring Boot documentation:
         * "Jersey’s support for scanning executable archives is rather limited. For example,
         * it cannot scan for endpoints in a package found in WEB-INF/classes when running an
         * executable war file. To avoid this limitation, the packages method should not be used,
         * and endpoints should be registered individually by using the register method, as shown
         * in the preceding example."
         * However the following seems to work pretty well. If it turns out that it does not, switch
         * to register the resources individually. */
        packages("de.szse.service.rest");
    }
}
