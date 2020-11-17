package cz.muni.fi.pa165.dto.servicetype;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * DTO for changing service type description
 *
 * @author Marek Radimersky, 456518
 */
public class ChangeDescriptionDTO {

    private Long id;
    @NotNull
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChangeDescriptionDTO)) return false;
        ChangeDescriptionDTO that = (ChangeDescriptionDTO) o;
        return Objects.equals(getId(), that.getId()) &&
                getDescription().equals(that.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getDescription());
    }

    @Override
    public String toString() {
        return "ChangeDescriptionDTO{" +
                "id=" + id +
                ", description='" + description + '\'' +
                '}';
    }
}
