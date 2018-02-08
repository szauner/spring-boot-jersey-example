package de.szse.service.persistence;

import java.util.List;

import de.szse.service.model.Example;

/**
 * You can specify custom behaviour of a repository that exceeds the standard mechanisms of spring-data by defining
 * a separate interface together with an implementation class and then also extending this interface in your actual
 * repository interface.<br>
 * For this example the implementation can be found in {@link CustomisedExampleRepository} and the customisation
 * will be used in {@link ExampleRepository}.<br>
 * You could also change the behaviour of the spring-data base repository my extending the implementation class if you
 * want to change some behaviour for all repositories. See the spring documentation for details.
 */
public interface CustomisedExampleRepository {
    public List<Example> criteriaSearch(Example example);
}
