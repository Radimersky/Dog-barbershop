package cz.muni.fi.pa165.config;

import cz.muni.fi.pa165.dto.DogDTO;
import cz.muni.fi.pa165.facade.DogFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * @author Marek Radimersky
 */
@Component
public class DogConverter implements Converter<String, DogDTO> {

    @Autowired
    DogFacade dogFacade;

    public DogConverter() {
    }

    @Override
    public DogDTO convert(String id) {
        return dogFacade.getDog(Long.parseLong(id));
    }
}
