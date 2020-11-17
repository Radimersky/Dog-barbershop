package cz.muni.fi.pa165.dto;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Objects;

/**
 * Totals for planned Visit
 * Used for checkout or confirmation before send
 */
public class VisitTotalsDTO {

    private Duration totalLength;

    private BigDecimal totalPrice;

    private String procedureDescriptions;

    public String getProcedureDescriptions() {
        return procedureDescriptions;
    }

    public void setProcedureDescriptions(String procedureDescriptions) {
        this.procedureDescriptions = procedureDescriptions;
    }

    public Duration getTotalLength() {
        return totalLength;
    }

    public void setTotalLength(Duration totalLength) {
        this.totalLength = totalLength;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VisitTotalsDTO)) return false;
        VisitTotalsDTO that = (VisitTotalsDTO) o;
        return Objects.equals(getTotalLength(), that.getTotalLength()) &&
                Objects.equals(getTotalPrice(), that.getTotalPrice()) &&
                Objects.equals(getProcedureDescriptions(), that.getProcedureDescriptions());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTotalLength(), getTotalPrice(), getProcedureDescriptions());
    }
}
