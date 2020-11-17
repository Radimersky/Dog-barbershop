package cz.muni.fi.pa165.dto.servicetype;

import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Objects;

/**
 * DTO for creating service type
 *
 * @author Marek Radimersky, 456518
 */
public class CreateServiceTypeDTO {
    @NotNull
    private String name;
    private BigDecimal price;

    @NotNull
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CreateServiceTypeDTO)) return false;
        CreateServiceTypeDTO that = (CreateServiceTypeDTO) o;
        return getName().equals(that.getName()) &&
                Objects.equals(getPrice(), that.getPrice()) &&
                getDescription().equals(that.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getPrice(), getDescription());
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
