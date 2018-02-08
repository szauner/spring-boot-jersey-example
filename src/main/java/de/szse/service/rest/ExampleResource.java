package de.szse.service.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;

import de.szse.service.business.ExampleService;
import de.szse.service.business.ExampleStatelessService;
import de.szse.service.model.Example;

@Path("example")
public class ExampleResource {
	private final static Logger log = LogManager.getLogger(ExampleResource.class);

    @Autowired
    private ExampleService exampleService;
    @Autowired
    private ExampleStatelessService exampleStatelessService;

    @GET
    @Path("object")
    @Produces(MediaType.APPLICATION_JSON)
    public Response doGet(@QueryParam("id") Long id) {
        // fish tagging
        ThreadContext.put("sessionId", exampleService.getSessionId());

        Example result = exampleService.getExample(id);

    	ThreadContext.clearMap();
        return Response.ok(result).build();
    }

    @GET
    @Path("service")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    public Response doGet(@QueryParam("type") String type) {
        // fish tagging
        ThreadContext.put("sessionId", exampleService.getSessionId());
        Response result;

        if ("stateless".equals(type)) {
            log.info("ID of stateless service requested.");
            result = Response.ok(exampleStatelessService.getServiceId()).build();
        } else if ("stateful".equals(type)) {
            result = Response.ok(exampleService.getServiceId()).build();
            log.info("ID of stateful service requested.");
        } else {
            result = Response.status(Status.BAD_REQUEST).build();
            log.info("Invalid service ID request encountered.");
        }

        ThreadContext.clearMap();
        return result;
    }


    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postHello(@FormParam("one") String one,
                              @FormParam("two") Integer two,
                              @FormParam("three") Float three) {
        // fish tagging
        ThreadContext.put("sessionId", exampleService.getSessionId());

        Example example = new Example();
        example.setStringValue(one);
        example.setIntValue(two);
        example.setFloatValue(three);
        exampleService.saveExample(example);

        ThreadContext.clearMap();
        return Response.ok().build();
    }
}