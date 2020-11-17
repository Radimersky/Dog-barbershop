package cz.muni.fi.pa165.config;

import cz.muni.fi.pa165.dto.PersonDTO;
import cz.muni.fi.pa165.facade.PersonFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * @author Marek Radimersky
 */
@Component
public class PersonConverter implements Converter<String, PersonDTO> {

    @Autowired
    PersonFacade personFacade;

    public PersonConverter() {
    }

    @Override
    public PersonDTO convert(String id) {
        return personFacade.getPerson(Long.parseLong(id));
    }
}
