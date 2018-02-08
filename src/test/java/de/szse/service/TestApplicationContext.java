package de.szse.service;

import org.springframework.context.annotation.PropertySource;

import de.szse.service.ExampleServiceApplication;

@PropertySource(value = "classpath:application.properties")
public class TestApplicationContext extends ExampleServiceApplication {

}
