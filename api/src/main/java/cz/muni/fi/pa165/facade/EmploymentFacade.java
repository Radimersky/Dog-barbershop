package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.EmploymentDTO;

import java.util.List;

/**
 * Facade for Employment entity
 *
 * @author Matúš Špik
 */
public interface EmploymentFacade {

    /**
     * Create new employment.
     *
     * @param employmentDTO employmentDTO to be created
     * @return id of created employment
     */
    Long createEmployment(EmploymentDTO employmentDTO);

    /**
     * Update employment with new parameters
     *
     * @param employmentDTO employment to update
     */
    void updateEmployment(EmploymentDTO employmentDTO);

    /**
     * Delete employment
     *
     * @param employmentDTO EmploymentDTO to be removed
     */
    void deleteEmployment(EmploymentDTO employmentDTO);

    /**
     * Get employment with given id
     *
     * @param id id of the employment to be found
     * @return EmploymentDTO of specific employment
     */
    EmploymentDTO getEmployment(Long id);

    /**
     * Get all employments
     *
     * @return list of all EmploymentDTO
     */
    List<EmploymentDTO> getAllEmployments();
    
    /**
     * Filters employment by employed person phone or name
     * @param phone phone of employed person to filter
     * @param name name of employed person to filter
     * @return filtered employments
     */
    List<EmploymentDTO> filterEmployments(String phone, String name);


}
