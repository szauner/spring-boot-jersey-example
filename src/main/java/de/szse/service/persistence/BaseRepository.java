package de.szse.service.persistence;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import javax.persistence.GeneratedValue;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

/**
 * A basic jpa repository that delivers some CRUD-functionality. It is created instead of
 * directly extending {@link CrudRepository} to be able to selectively expose functionality.

 * @param <T> the type of the entity that will be managed by the repository
 * @param <ID> the type of the entitiy's identifier
 */
// Ensure that Spring Data JPA does not try to create an implementation for this base repository interface.
@NoRepositoryBean
interface BaseRepository<T, ID extends Serializable> extends Repository<T, ID> {

    /**
     * Delete the specified entity from the persistence storage.
     *
     * @param entity the entity to delete
     */
    public void delete(T entity);

    /**
     * Find all entities of type T and return them as a list.
     *
     * @return a list of entities of Type T
     */
    public List<T> findAll();

    /**
     * Find one specific entity.
     *
     * @param id the identifier of the entity to find
     *
     * @return an Optional that may or may not contain an entity of type T
     */
    public Optional<T> findById(ID id);

    /**
     * Save an entity to the persistence storage. New entities will have an identifier set automatically after
     * saving when their identifier field is marked by the {@link GeneratedValue}-annotation.
     *
     * @param entity the entity to persist
     *
     * @return the persisted entity
     */
    public T save(T entity);
}