package cz.muni.fi.pa165.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

/**
 * Entity class representing Employment of Person.class
 *
 * @author Martin.Palic
 */
@Entity
public class Employment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @OneToOne
    @NotNull
    private Person person;
    private String positionName;
    private BigDecimal salary;
    @NotNull
    private Date startDate;
    private Date endDate;

    public Employment(Person person, String positionName, BigDecimal salary, Date startDate, Date endDate) {
        this.person = person;
        this.positionName = positionName;
        this.salary = salary;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Employment() {
    }

    public Employment(Person person, String positionName, Date startDate, Date endDate) {
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

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
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

    public void setStartDate(Date start) {
        this.startDate = start;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date end) {
        this.endDate = end;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employment)) return false;
        Employment that = (Employment) o;
        return Objects.equals(getId(), that.getId()) &&
                Objects.equals(getPerson(), that.getPerson()) &&
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
