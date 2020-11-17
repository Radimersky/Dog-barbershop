package cz.muni.fi.pa165.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Objects;

/**
 * Entity class representing barbershop Visit by Person.class
 * one person can bring multiple Dog.class
 *
 * @author Martin.Palic
 */
@Entity
public class Visit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @ManyToOne
    @NotNull
    private Dog dog;
    @NotNull
    private Date start;
    @NotNull
    private Date finish;

    public Visit() {
    }

    public Visit(Dog dog, Date start, Date finish) {
        this.dog = dog;
        this.start = start;
        this.finish = finish;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public Dog getDog() {
        return dog;
    }

    public void setDog(Dog dogs) {
        this.dog = dogs;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getFinish() {
        return finish;
    }

    public void setFinish(Date finish) {
        this.finish = finish;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Visit)) return false;
        Visit visit = (Visit) o;
        return Objects.equals(getId(), visit.getId()) &&
                Objects.equals(getDog(), visit.getDog()) &&
                Objects.equals(getStart(), visit.getStart()) &&
                Objects.equals(getFinish(), visit.getFinish());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getDog(), getStart(), getFinish());
    }
}
