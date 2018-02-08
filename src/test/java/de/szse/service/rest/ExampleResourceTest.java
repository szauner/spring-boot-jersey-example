package de.szse.service.rest;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.equalToIgnoringWhiteSpace;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import de.szse.service.EndToEndTest;
import de.szse.service.business.ExampleService;
import de.szse.service.model.Example;

public class ExampleResourceTest extends EndToEndTest {
    private final static String INITIAL_STRING_VALUE = "initial";
    private final static Integer INITIAL_INTEGER_VALUE = 8;
    private final static Float INITIAL_FLOAT_VALUE = 1.5f;
    private final static String SAVED_STRING_VALUE = "saved";
    private final static Integer SAVED_INTEGER_VALUE = 5;
    private final static Float SAVED_FLOAT_VALUE = 4.5f;

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private ExampleService exampleService;

    private Long testExampleId;

    @Before
    public void initTestData() throws InterruptedException {
        exampleService.resetData();

        Example ex = new Example();
        ex.setStringValue(INITIAL_STRING_VALUE);
        ex.setIntValue(INITIAL_INTEGER_VALUE);
        ex.setFloatValue(INITIAL_FLOAT_VALUE);
        exampleService.saveExample(ex);

        testExampleId = ex.getId();
    }

    @Test
    public void testRetrievalOfExampleObject() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<String> httpEntity = new HttpEntity<String>(null, headers);

        UriComponentsBuilder builder = UriComponentsBuilder
            .fromUriString("/services/example/object").queryParam("id", testExampleId);
        ResponseEntity<String> entity = this.restTemplate.exchange(
            builder.toUriString(), HttpMethod.GET, httpEntity, String.class);

        assertThat(entity.getStatusCode(), equalTo(HttpStatus.OK));
        assertThat(entity.getBody(), equalToIgnoringWhiteSpace(
            "{\"id\":" + testExampleId
            + ",\"stringValue\":\"" + INITIAL_STRING_VALUE
            + "\",\"floatValue\":" + INITIAL_FLOAT_VALUE
            + ",\"intValue\":" + INITIAL_INTEGER_VALUE
            + "}"));
    }

    @Test
    public void testStatelessServiceInstantiation() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<String> httpEntity = new HttpEntity<String>(null, headers);

        UriComponentsBuilder builder = UriComponentsBuilder
            .fromUriString("/services/example/service").queryParam("type", "stateless");
        ResponseEntity<String> entityStatelessOne = this.restTemplate.exchange(
            builder.toUriString(), HttpMethod.GET, httpEntity, String.class);
        ResponseEntity<String> entityStatelessTwo = this.restTemplate.exchange(
            builder.toUriString(), HttpMethod.GET, httpEntity, String.class);

        assertThat(entityStatelessOne.getStatusCode(), equalTo(HttpStatus.OK));
        assertThat(entityStatelessTwo.getStatusCode(), equalTo(HttpStatus.OK));

        String firstServiceId = entityStatelessOne.getBody();
        String secondServiceId = entityStatelessTwo.getBody();
        assertThat(firstServiceId, not(equalTo(secondServiceId)));
    }

    @Test
    public void testStatefulServiceInstantiation() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<String> httpEntity = new HttpEntity<String>(null, headers);

        UriComponentsBuilder builder = UriComponentsBuilder
            .fromUriString("/services/example/service").queryParam("type", "stateful");
        ResponseEntity<String> entityStatefulOne = this.restTemplate.exchange(
            builder.toUriString(), HttpMethod.GET, httpEntity, String.class);
        ResponseEntity<String> entityStatefulTwo = this.restTemplate.exchange(
            builder.toUriString(), HttpMethod.GET, httpEntity, String.class);

        assertThat(entityStatefulOne.getStatusCode(), equalTo(HttpStatus.OK));
        assertThat(entityStatefulTwo.getStatusCode(), equalTo(HttpStatus.OK));

        String firstServiceId = entityStatefulOne.getBody();
        String secondServiceId = entityStatefulOne.getBody();
        assertThat(firstServiceId, equalTo(secondServiceId));
    }

    @Test
    public void testIllegalServiceInstantiationInformationRequest() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<String> httpEntity = new HttpEntity<String>(null, headers);

        UriComponentsBuilder builder = UriComponentsBuilder
            .fromUriString("/services/example/service").queryParam("type", "ergtrhweh");
        ResponseEntity<String> entity = this.restTemplate.exchange(
            builder.toUriString(), HttpMethod.GET, httpEntity, String.class);

        assertThat(entity.getStatusCode(), equalTo(HttpStatus.BAD_REQUEST));
    }

    @Test
    public void testPostingExample() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("one", SAVED_STRING_VALUE);
        params.add("two", SAVED_INTEGER_VALUE.toString());
        params.add("three", SAVED_FLOAT_VALUE.toString());

        HttpEntity<MultiValueMap<String, String>> request =
                new HttpEntity<MultiValueMap<String, String>>(params, headers);

        ResponseEntity<String> entity = this.restTemplate.postForEntity(
            "/services/example", request, String.class);

        Example ex = exampleService.getExample(testExampleId + 1);
        assertThat(entity.getStatusCode(), equalTo(HttpStatus.OK));
        assertNotNull(ex);
        assertThat(ex.getStringValue(), equalTo(SAVED_STRING_VALUE));
        assertThat(ex.getIntValue(), equalTo(SAVED_INTEGER_VALUE));
        assertThat(ex.getFloatValue(), equalTo(SAVED_FLOAT_VALUE));
    }
}