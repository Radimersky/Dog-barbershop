package cz.muni.fi.pa165.dto.servicetype;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * DTO for changing service type name
 *
 * @author Marek Radimersky, 456518
 */
public class ChangeNameDTO {

    private Long id;
    @NotNull
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ChangeNameDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChangeNameDTO)) return false;
        ChangeNameDTO that = (ChangeNameDTO) o;
        return Objects.equals(getId(), that.getId()) &&
                getName().equals(that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName());
    }
}
