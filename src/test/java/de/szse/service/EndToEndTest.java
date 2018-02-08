package de.szse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

/**
 * End to end tests like {@link IntegrationTest} will create the whole Spring Context. But furthermore the server
 * application and the constructed HTTP client (for example used by {@link TestRestTemplate}) will use different
 * threads (this is triggered by the use of <code>webEnvironment=WebEnvironment.RANDOM_PORT</code>).<br>
 * The use of two threads means that persistence operations performed on the server side, would not be rolled back
 * after the test has ended, even if the test had been marked by @Transactionl! IF marked like that only operations
 * made in the client code or the test code respectively will be rolled back. But then especially clean up operations
 * would be rolled back to and therefore no cleanup of the server operations would be possible. That is the reason
 * why <code>EndToEndTest</code>s are not marked by the transactional annotation and do not extend
 * {@link AbstractTransactionalJUnit4SpringContextTests} but {@link AbstractJUnit4SpringContextTests} instead.
 *
 * @author Stefan Zauner
 */
@SpringBootTest(classes=TestApplicationContext.class, webEnvironment=WebEnvironment.RANDOM_PORT)
public abstract class EndToEndTest extends AbstractJUnit4SpringContextTests {
    // provide the random port by letting spring inject it into a member field
    @LocalServerPort
    protected int randomServerPort;

    // provide means to access REST endpoints for every integration test
    @Autowired
    protected TestRestTemplate restTemplate;
}