package de.szse.service.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;

import de.szse.service.model.Example;

/**
 * This is the implementation class for the repository customisation {@link CustomisedExampleRepository}.
 */
public class CustomisedExampleRepositoryImpl implements CustomisedExampleRepository {

    @PersistenceContext
    protected EntityManager em;

    /**
     * This customisation can now use hibernate criterias instead of the query creation mechanisms of
     * spring-data.<br>
     * Actually you could also use Spring's <code>Specifications</code> that uses criteria queries under the hood. But
     * this method should only demonstrate how you can do it vour way if you want or need to.
     */
    public List<Example> criteriaSearch(Example example) {
        Session session = em.unwrap(Session.class);
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Example> query = builder.createQuery(Example.class);
        Root<Example> root = query.from(Example.class);
        query.select(root);
        return session.createQuery(query).getResultList();
    }

}
