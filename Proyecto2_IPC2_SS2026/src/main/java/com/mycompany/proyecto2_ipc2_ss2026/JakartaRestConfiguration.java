package com.mycompany.proyecto2_ipc2_ss2026;

import jakarta.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;

/**
 * Configures Jakarta RESTful Web Services for the application.
 * @author Juneau
 */
@ApplicationPath("api/v1")
public class JakartaRestConfiguration extends ResourceConfig {
    
    public JakartaRestConfiguration() {
        packages("com.mycompany.proyecto2_ipc2_ss2026.resources").property(ServerProperties.RESPONSE_SET_STATUS_OVER_SEND_ERROR, true);
    }
    
}
