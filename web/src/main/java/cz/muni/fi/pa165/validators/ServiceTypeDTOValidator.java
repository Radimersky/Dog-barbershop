package cz.muni.fi.pa165.validators;

import cz.muni.fi.pa165.dto.ServiceTypeDTO;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.math.BigDecimal;

/**
 * ServiceTypeDTO Validator
 *
 * @author Aneta Moravcikova, 456444
 */
public class ServiceTypeDTOValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return ServiceTypeDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ServiceTypeDTO serviceTypeDTO = (ServiceTypeDTO) o;

        if (serviceTypeDTO.getName() == null || serviceTypeDTO.getName().trim().isEmpty() || !serviceTypeDTO.getName().matches("[A-Za-z ]+")) {
            errors.rejectValue("name", "serviceType.name.error");
        }
        if (serviceTypeDTO.getDescription() == null || serviceTypeDTO.getDescription().trim().isEmpty() || !serviceTypeDTO.getDescription().matches("[A-Za-z0-9., ]+")) {
            errors.rejectValue("description", "serviceType.description.error");
        }

        if (serviceTypeDTO.getStandardLengthFormated() == null || serviceTypeDTO.getStandardLengthFormated().trim().isEmpty() || !serviceTypeDTO.getStandardLengthFormated().matches("[0-9]+[:][0-9]+")) {
            errors.rejectValue("standardLengthFormated", "serviceType.getStandardLengthFormated.error");
        }

        if (serviceTypeDTO.getPrice() == null || BigDecimal.ZERO.compareTo(serviceTypeDTO.getPrice()) >= 0) {
            errors.rejectValue("price", "serviceType.price.error");
        }

    }
}
