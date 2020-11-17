package cz.muni.fi.pa165.dto.servicetype;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * DTO for changing service type price
 *
 * @author Marek Radimersky, 456518
 */
public class ChangePriceDTO {

    private Long id;
    private BigDecimal price;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "ChangePriceDTO{" +
                "id=" + id +
                ", price=" + price +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChangePriceDTO)) return false;
        ChangePriceDTO that = (ChangePriceDTO) o;
        return Objects.equals(getId(), that.getId()) &&
                Objects.equals(getPrice(), that.getPrice());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getPrice());
    }
}
