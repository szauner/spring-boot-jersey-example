package de.szse.service.business;

import de.szse.service.model.Example;

/**
 * Business logic services should have an interface they are implemented against.
 * On the one hand that is necessary for Spring to work properly on the other hand it is
 * common practice and makes it easy to mock the services.
 */
public interface ExampleService {
	public String getExampleValue();
	public String getServiceId();
	public String getSessionId();
	public Example getExample(Long id);
	public void saveExample(Example ex);
	public void resetData();
}
