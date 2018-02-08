package de.szse.service.business;

import static org.hamcrest.CoreMatchers.isA;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import de.szse.service.UnitTest;
import de.szse.service.business.ExampleServiceImpl;
import de.szse.service.model.Example;
import de.szse.service.persistence.ExampleRepository;

/**
 * Unit test example; unit tests should not depend on the concrete implementation of
 * other classes or resources like databases.
 */
public class ExampleUnitTest extends UnitTest {

    @InjectMocks
    private ExampleServiceImpl service;

    @Mock
    private ExampleRepository repository;

    /**
     * Runs before every test method
     */
    @Before
    public void setUp() {
        when(repository.findById(1L)).thenReturn(Optional.of(new Example()));
        when(repository.findById(2L)).thenThrow(new NoSuchElementException());
    }

    /**
     * Example test
     */
    @Test
    public void testExampleRetrieval() {
        assertThat(service.getExample(1L), isA(Example.class));
    }

    /**
     * This test is supposed to throw an exception as normal behaviour.
     */
    @Test(expected = NoSuchElementException.class)
    public void testExceptionExpectation() {
        service.getExample(2L);
    }
}