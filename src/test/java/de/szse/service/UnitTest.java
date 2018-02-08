package de.szse.service;

import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * Simple parent for unit tests that already enables the usage of mocks by defining the
 * Mockito runner as test runner. So when extending this class there is no need for
 * <code>MockitoAnnotations.initMocks(this);</code> in an init-method.<br><br>
 * Furthermore the usage of {@link MockitoJUnitRunner} should discourage from using a
 * Spring test runner and effectively transforming the unit tests into integration tests.
 *
 * @author Stefan Zauner
 */
@RunWith(MockitoJUnitRunner.class)
public abstract class UnitTest {

}