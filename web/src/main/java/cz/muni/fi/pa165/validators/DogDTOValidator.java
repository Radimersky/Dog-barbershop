package cz.muni.fi.pa165.validators;

import cz.muni.fi.pa165.dto.DogDTO;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Date;

/**
 * DogDTO Validator
 *
 * @author Aneta Moravcikova, 456444
 */
public class DogDTOValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return DogDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object o, Errors errors) {
        DogDTO dogDTO = (DogDTO) o;

        if (dogDTO.getName() == null || dogDTO.getName().trim().isEmpty() || !dogDTO.getName().matches("[A-Za-z ]+")) {
            errors.rejectValue("name", "dog.name.error");
        }

        if (dogDTO.getBreed() == null || dogDTO.getBreed().trim().isEmpty() || !dogDTO.getBreed().matches("[A-Za-z0-9 ]+")) {
            errors.rejectValue("breed", "dog.breed.error");
        }

        if (dogDTO.getDateOfBirth() == null || dogDTO.getDateOfBirth().after(new Date())) {
            errors.rejectValue("dateOfBirth", "dog.birth.error");
        }
    }
}
