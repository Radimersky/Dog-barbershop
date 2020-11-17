package cz.muni.fi.pa165.dto;

import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Objects;


/**
 * ServiceType DTO representing transfered data about ServiceType
 *
 * @author Martin.Palic
 */
public class ServiceTypeDTO {

    private Long Id;
    @NotNull
    private String name;
    private Duration standardLength;
    private BigDecimal price;
    @NotNull
    private String description;
    private String standardLengthFormated;

    public ServiceTypeDTO() {
    }

    public ServiceTypeDTO(String name, Duration standardLength, BigDecimal price, String description) {
        this.name = name;
        this.standardLength = standardLength;
        this.price = price;
        this.description = description;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        this.Id = id;
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
        if (standardLength == null) return;
        long seconds = this.standardLength.getSeconds();
        long absSeconds = Math.abs(seconds);
        String positive = String.format(
                "%d:%02d",
                absSeconds / 3600,
                (absSeconds % 3600) / 60);
        this.standardLengthFormated = seconds < 0 ? "-" + positive : positive;
    }

    public String getStandardLengthFormated() {
        return standardLengthFormated;
    }

    public void setStandardLengthFormated(String standardLengthFormated) {
        int total = 0;
        try {
            total += Integer.parseInt(standardLengthFormated.split(":")[0]) * 3600;
            total += Integer.parseInt(standardLengthFormated.split(":")[1]) * 60;
        } catch (Exception e) {
            return;
        }
        this.standardLength = Duration.ofSeconds(total);
        this.standardLengthFormated = standardLengthFormated;
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
        if (!(o instanceof ServiceTypeDTO)) return false;
        ServiceTypeDTO that = (ServiceTypeDTO) o;
        return Objects.equals(getId(), that.getId()) &&
                getName().equals(that.getName()) &&
                Objects.equals(getStandardLength(), that.getStandardLength()) &&
                Objects.equals(getPrice(), that.getPrice()) &&
                getDescription().equals(that.getDescription()) &&
                Objects.equals(getStandardLengthFormated(), that.getStandardLengthFormated());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getStandardLength(), getPrice(), getDescription(), getStandardLengthFormated());
    }
}
