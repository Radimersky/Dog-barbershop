package cz.muni.fi.pa165.dto;

import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

/**
 * Employment DTO representing transfered data about employment
 *
 * @author Martin.Palic
 */
public class EmploymentDTO {

    private Long Id;
    @NotNull
    private PersonDTO person;
    private String positionName;
    private BigDecimal salary;
    private Date startDate;
    private Date endDate;

    public EmploymentDTO(@NotNull PersonDTO person, String positionName, BigDecimal salary, Date startDate, Date endDate) {
        this.person = person;
        this.positionName = positionName;
        this.salary = salary;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public EmploymentDTO() {
    }

    public EmploymentDTO(Long id, PersonDTO person, String positionName, Date startDate, Date endDate) {
        Id = id;
        this.person = person;
        this.positionName = positionName;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public PersonDTO getPerson() {
        return person;
    }

    public void setPerson(PersonDTO person) {
        this.person = person;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EmploymentDTO)) return false;
        EmploymentDTO that = (EmploymentDTO) o;
        return Objects.equals(getId(), that.getId()) &&
                getPerson().equals(that.getPerson()) &&
                Objects.equals(getPositionName(), that.getPositionName()) &&
                Objects.equals(getSalary(), that.getSalary()) &&
                Objects.equals(getStartDate(), that.getStartDate()) &&
                Objects.equals(getEndDate(), that.getEndDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getPerson(), getPositionName(), getSalary(), getStartDate(), getEndDate());
    }
}
