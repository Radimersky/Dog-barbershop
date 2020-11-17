package cz.muni.fi.pa165.validators;

import cz.muni.fi.pa165.dto.EmploymentDTO;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.math.BigDecimal;

/**
 * EmploymentDTO Validator
 *
 * @author Aneta Moravcikova, 456444
 */
public class EmploymentDTOValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return EmploymentDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object o, Errors errors) {
        EmploymentDTO employmentDTO = (EmploymentDTO) o;

        if (employmentDTO.getPositionName() == null || employmentDTO.getPositionName().trim().isEmpty()) {
            errors.rejectValue("positionName", "employment.positionName.error");
        }

        if (employmentDTO.getSalary() == null || BigDecimal.ZERO.compareTo(employmentDTO.getSalary()) >= 0) {
            errors.rejectValue("salary", "employment.salary.error");
        }

        if (employmentDTO.getPerson().getName() == null || employmentDTO.getPerson().getName().trim().isEmpty() || !employmentDTO.getPerson().getName().matches("[A-Za-z ]+")) {
            errors.rejectValue("person.name", "person.name.error");
        }

        if (employmentDTO.getPerson().getSurname() == null || employmentDTO.getPerson().getSurname().trim().isEmpty() || !employmentDTO.getPerson().getSurname().matches("[A-Za-z ]+")) {
            errors.rejectValue("person.surname", "person.surname.error");
        }

        if (employmentDTO.getPerson().getAddress() == null || employmentDTO.getPerson().getAddress().trim().isEmpty() || !employmentDTO.getPerson().getAddress().matches("[A-Za-z0-9, ]+")) {
            errors.rejectValue("person.address", "person.address.error");
        }

        if (employmentDTO.getPerson().getPhoneNumber() == null || employmentDTO.getPerson().getPhoneNumber().trim().isEmpty() || !employmentDTO.getPerson().getPhoneNumber().matches("[+0-9 ]+")) {
            errors.rejectValue("person.phoneNumber", "person.phoneNumber.error");
        }

        if (employmentDTO.getPerson().getPassword() == null || employmentDTO.getPerson().getPassword().trim().isEmpty()) {
            errors.rejectValue("person.password", "person.password.error");
        }

    }
}
