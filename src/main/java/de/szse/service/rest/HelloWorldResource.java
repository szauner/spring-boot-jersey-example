package de.szse.service.rest;

import java.util.Map;
import java.util.TreeMap;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import de.szse.service.business.ExampleService;
import de.szse.service.business.ExampleStatelessService;

@Path("helloworld")
public class HelloWorldResource {
	private final static Logger log = LogManager.getLogger(HelloWorldResource.class);

    public static final String MESSAGE = "Hello World!";

    @Autowired
    private ExampleService exampleService;

    @Autowired
    private ExampleStatelessService exampleStatelessService;

    @GET
    @Produces("text/plain")
    public String doGet(@QueryParam("option") String option) {
        // fish tagging
        ThreadContext.put("sessionId", exampleService.getSessionId());

        log.info("Sending hello world message.");

    	String result = MESSAGE + "\n"
    		+ "Singleton: " + exampleService.getExampleValue() + "\n" // each request will yield same service object
    		+ "Request: " + exampleStatelessService.getExampleValue(); // each request will yield a different object

    	if (option != null && option.length() > 0) {
    	    result += "\nOption has been selected!";
    	}

    	ThreadContext.clearMap();
        return result;
    }

    /**
     * How to handle request parameters:
     * The @Context-annotation lets you access
     * <ul>
     *   <li>UriInfo</li>
     *   <li>Request</li>
     *   <li>HttpHeaders</li>
     *   <li>SecurityContext</li>
     *   <li>Providers</li>
     * </ul>
     */
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public String postHello(@FormParam("option1") String option1,
                            @FormParam("option2") String option2,
                            @Context UriInfo uriInfo) {
        // fish tagging
        ThreadContext.put("sessionId", exampleService.getSessionId());

        MultivaluedMap<String, String> queryParams = uriInfo.getQueryParameters();
        String option1Param = queryParams.getFirst("option1");
        log.info("Got Parameter: {}", option1);
        log.info("Got Parameter: {}", option1Param);

        Map<String, String> jsonValues = new TreeMap<>();
        jsonValues.put("result1", option1);
        jsonValues.put("result2", option2);
        JSONObject json = new JSONObject(jsonValues);

        ThreadContext.clearMap();
        return json.toString();
    }
}