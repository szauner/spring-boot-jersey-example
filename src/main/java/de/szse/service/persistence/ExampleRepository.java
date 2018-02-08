package de.szse.service.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.lang.Nullable;

import de.szse.service.model.Example;

public interface ExampleRepository extends BaseRepository<Example, Long>, CustomisedExampleRepository {

    public void delete(Example deleted);

    public void deleteAll();

    public List<Example> findAll();

    public Optional<Example> findById(Long id);

    public Example save(Example persisted);

    // The annotation @Nullable can be used to indicate that a specific argument my be null.
    // In general that is prohibited by the definition in package-info.java.
    public List<Example> findByStringValueAndIntValue(String stringValue, @Nullable int inValue);

    // The annotation @Nullable can also be used to indicate that a method is allowed to return null.
    // This in general is also prohibited by the definition in package-info.java.
    @Nullable
    public Example findByStringValue(String stringValue);
}