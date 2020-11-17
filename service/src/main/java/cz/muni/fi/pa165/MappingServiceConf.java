package cz.muni.fi.pa165;

import cz.muni.fi.pa165.dto.*;
import cz.muni.fi.pa165.entity.*;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.Collections;

/**
 * Mapping service configuration
 *
 * @author Aneta Moravcikova, 456444
 */

@Configuration
@Import(PersistenceSampleApplicationContext.class)
@ComponentScan(basePackages = "cz.muni.fi.pa165")
public class MappingServiceConf {
    @Bean
    public Mapper getMapper() {
        DozerBeanMapper mapper = new DozerBeanMapper();
        mapper.setMappingFiles(Collections.singletonList("dozerConverter.xml"));
        mapper.addMapping(new DozerConfig());
        return mapper;
    }

    public static class DozerConfig extends BeanMappingBuilder {
        @Override
        protected void configure() {
            mapping(Dog.class, DogDTO.class);
            mapping(Employment.class, EmploymentDTO.class);
            mapping(Visit.class, VisitDTO.class);
            mapping(PerformedService.class, PerformedServiceDTO.class);
            mapping(ServiceType.class, ServiceTypeDTO.class);
            mapping(Person.class, PersonDTO.class);
        }
    }

}
