package de.szse.service.model;

/**
 * Marks the classes' package for being scanned by Spring's @EnableJpaRepositories-Annotation.

 * @see org.springframework.data.jpa.repository.config.EnableJpaRepositories#basePackageClasses()
 */
public final class JpaMarkerModel {
	/**
	 * Since this is only a marker class, instantiation is prohibited.
	 */
	private JpaMarkerModel() {};
}