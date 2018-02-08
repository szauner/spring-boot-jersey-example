package de.szse.service.rest;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.isA;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import java.util.HashMap;
import java.util.Map;

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

import de.szse.service.EndToEndTest;
import de.szse.service.business.ExampleService;

public class HelloWorldResourceTest extends EndToEndTest {
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private ExampleService exampleService;

    @Before
    public void initTestData() {
        exampleService.resetData();
    }

    @Test
    public void testSimpleGet() {
        ResponseEntity<String> entity = this.restTemplate.getForEntity("/services/helloworld", String.class);
        assertThat(entity.getStatusCode(), isA(HttpStatus.class));
        assertThat(entity.getStatusCode(), equalTo(HttpStatus.OK));
        assertThat(entity.getBody(), containsString("de.szse.service.business.ExampleServiceImpl"));
        assertThat(entity.getBody(), not(containsString("Option has been selected")));
    }

    @Test
    public void testComplexGet() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<String> httpEntity = new HttpEntity<String>(null, headers);

        String getURI = "/services/helloworld?option={option}";
        Map<String, Object> paramValues = new HashMap<>();
        paramValues.put("option", "value");
        ResponseEntity<String> entity = this.restTemplate.exchange(getURI, HttpMethod.GET,
                                                                   httpEntity, String.class, paramValues);

        assertThat(entity.getStatusCode(), isA(HttpStatus.class));
        assertThat(entity.getStatusCode(), equalTo(HttpStatus.OK));
        assertThat(entity.getBody(), containsString("de.szse.service.business.ExampleServiceImpl"));
        assertThat(entity.getBody(), containsString("Option has been selected"));
    }

    @Test
    public void testPost() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("option1", "value1");
        params.add("option2", "value2");

        HttpEntity<MultiValueMap<String, String>> request =
                new HttpEntity<MultiValueMap<String, String>>(params, headers);

        ResponseEntity<String> entity = this.restTemplate.postForEntity("/services/helloworld", request, String.class);

        assertThat(entity.getStatusCode(), isA(HttpStatus.class));
        assertThat(entity.getStatusCode(), equalTo(HttpStatus.OK));
        assertThat(entity.getBody(), containsString("\"result1\":\"value1\""));
        assertThat(entity.getBody(), containsString("\"result2\":\"value2\""));
    }
}