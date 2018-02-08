package de.szse.service.business;

import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

/*
 * Also possible is the creation of the bean for every request.
 * The proxyMode attribute is necessary because at the moment of the instantiation of the web application context,
 * there is no active request. Spring will create a proxy to be injected as a dependency, and instantiate the
 * target bean when it is needed in a request.
 */
@Service
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ExampleStatelessServiceImpl implements ExampleStatelessService {
    @Autowired
    private HttpServletRequest servletRequest;

    public String getExampleValue() {
        return "This server's name is " + servletRequest.getServerName() + "; Service: " + getServiceId();
    }

    public String getServiceId() {
        return Objects.toString(this);
    }

    public String getSessionId() {
        return servletRequest.getSession().getId();
    }
}