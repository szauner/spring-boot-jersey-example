package de.szse.service;

/**
 * This class defines the Spring profiles used in the project. The idea behind this class
 * is that it helps to avoid typos when using the profiles in code.
 * <ul>
 *   <li>The DEVELOPMENT profile for developer machines</li>
 *   <li>The TEST profile should be used when running the application in the test environment</li>
 *   <li>The PRODUCTION profile will be used when running the application in the production environment</li>
 *   <li>The CUSTOM profile lets developers define custom values for properties in their machines using
 *       a file named <code>application-custom.properties</code></li>
 * </ul>
 *
 * The precedence of property configurations is documented here:
 * <a href="https://docs.spring.io/spring-boot/docs/current-SNAPSHOT/reference/htmlsingle/#boot-features-external-config">boot-features-external-config</a>
 *
 * @author Stefan Zauner
 */
public class Profiles {
    public static final String DEVELOPMENT = "development";
    public static final String TEST = "test";
    public static final String PRODUCTION = "production";
    public static final String CUSTOM = "custom";

    /**
     * Prevent instantiation.
     */
    private Profiles() {}
}