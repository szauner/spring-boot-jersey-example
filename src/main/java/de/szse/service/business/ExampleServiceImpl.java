package de.szse.service.business;

import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.szse.service.model.Example;
import de.szse.service.persistence.ExampleRepository;

@Service
public class ExampleServiceImpl implements ExampleService {
	@Autowired
	private HttpServletRequest servletRequest;
	@Autowired
	private ExampleRepository exampleRepository;

	public String getExampleValue() {
		return "This server's name is " + servletRequest.getServerName() + "; Service: " + getServiceId();
	}

	public String getServiceId() {
	    return Objects.toString(this);
	}

	public String getSessionId() {
		return servletRequest.getSession().getId();
	}

    @Transactional
    public Example getExample(Long id) {
        return exampleRepository.findById(id).get();
    }

    @Transactional
    public void saveExample(Example ex) {
        exampleRepository.save(ex);
    }

    @Transactional
    public void resetData() {
        exampleRepository.deleteAll();
    }
}