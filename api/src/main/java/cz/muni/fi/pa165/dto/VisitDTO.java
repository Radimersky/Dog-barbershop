package cz.muni.fi.pa165.dto;

import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.Objects;

/**
 * Visit DTO representing transfered data about Visit
 *
 * @author Martin.Palic
 */
public class VisitDTO {

    private Long Id;
    @NotNull
    private DogDTO dog;
    private Date start;
    private Date finish;

    public VisitDTO() {
    }

    public VisitDTO(DogDTO dog, Date start, Date finish) {
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

    public DogDTO getDog() {
        return dog;
    }

    public void setDog(DogDTO dogs) {
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
        if (!(o instanceof VisitDTO)) return false;
        VisitDTO visitDTO = (VisitDTO) o;
        return Objects.equals(getId(), visitDTO.getId()) &&
                getDog().equals(visitDTO.getDog()) &&
                Objects.equals(getStart(), visitDTO.getStart()) &&
                Objects.equals(getFinish(), visitDTO.getFinish());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getDog(), getStart(), getFinish());
    }
}
