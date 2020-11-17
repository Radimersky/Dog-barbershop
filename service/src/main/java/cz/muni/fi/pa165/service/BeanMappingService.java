package cz.muni.fi.pa165.service;

import org.dozer.Mapper;

import java.util.Collection;
import java.util.List;

/**
 * @author Aneta Moravcikova, 456444
 */
public interface BeanMappingService {

    /**
     * Map objects to specified class
     * @param objects objects to map
     * @param mapToClass class to map objects on
     * @param <T> class
     * @return List of mapped objects
     */
    public <T> List<T> mapTo(Collection<?> objects, Class<T> mapToClass);

    /**
     * Map object to specified class
     * @param u object to map
     * @param mapToClass class to map object on
     * @param <T> class
     * @return Mapped object
     */
    public <T> T mapTo(Object u, Class<T> mapToClass);

    /**
     * Get mapper
     * @return mapper
     */
    public Mapper getMapper();
}
