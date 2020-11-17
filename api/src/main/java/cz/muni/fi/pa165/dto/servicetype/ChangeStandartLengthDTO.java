package cz.muni.fi.pa165.dto.servicetype;

import java.time.Duration;
import java.util.Objects;

/**
 * DTO for changing standart length
 *
 * @author Marek Radimersky, 456518
 */
public class ChangeStandartLengthDTO {

    private Long id;
    private Duration standardLength;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Duration getStandardLength() {
        return standardLength;
    }

    public void setStandardLength(Duration standardLength) {
        this.standardLength = standardLength;
    }

    @Override
    public String toString() {
        return "ChangeStandartLengthDTO{" +
                "id=" + id +
                ", standardLength=" + standardLength +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChangeStandartLengthDTO)) return false;
        ChangeStandartLengthDTO that = (ChangeStandartLengthDTO) o;
        return Objects.equals(getId(), that.getId()) &&
                Objects.equals(getStandardLength(), that.getStandardLength());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getStandardLength());
    }
}
