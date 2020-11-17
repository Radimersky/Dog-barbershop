package cz.muni.fi.pa165.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.Objects;

/**
 * Entity class representing type of service
 *
 * @author Matúš Špik
 */
@Entity
public class ServiceType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @NotNull
    @Pattern(regexp = "^[a-z A-Z]*$", message = "Service's name can include only alphabetical characters.")
    private String name;
    private Duration standardLength;
    private BigDecimal price;

    @NotNull
    @Pattern(regexp = "^[a-z0-9,'. A-Z]*$", message = "Service's description can include only alphanumerical characters")
    private String description;

    public ServiceType() {
    }

    public ServiceType(String name, Duration standardLength, BigDecimal price, String description) {
        this.name = name;
        this.standardLength = standardLength;
        this.price = price;
        this.description = description;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long Id) {
        this.Id = Id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Duration getStandardLength() {
        return standardLength;
    }

    public void setStandardLength(Duration standardLength) {
        this.standardLength = standardLength;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
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
        if (!(o instanceof ServiceType)) return false;
        ServiceType that = (ServiceType) o;
        return Objects.equals(getId(), that.getId()) &&
                Objects.equals(getName(), that.getName()) &&
                Objects.equals(getStandardLength(), that.getStandardLength()) &&
                Objects.equals(getPrice(), that.getPrice()) &&
                Objects.equals(getDescription(), that.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getStandardLength(), getPrice(), getDescription());
    }
}
