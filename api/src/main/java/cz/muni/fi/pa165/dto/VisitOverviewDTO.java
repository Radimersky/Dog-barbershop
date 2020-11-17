package cz.muni.fi.pa165.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class VisitOverviewDTO {

    private Long id;
    private DogDTO dog;
    private Date start;
    private Date finish;
    private List<ServiceTypeDTO> serviceTypes;
    BigDecimal totalPrice;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DogDTO getDog() {
        return dog;
    }

    public void setDog(DogDTO dog) {
        this.dog = dog;
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

    public List<ServiceTypeDTO> getServiceTypes() {
        return serviceTypes;
    }

    public void setServiceTypes(List<ServiceTypeDTO> serviceTypes) {
        this.serviceTypes = serviceTypes;
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
        if (!(o instanceof VisitOverviewDTO)) return false;
        VisitOverviewDTO that = (VisitOverviewDTO) o;
        return Objects.equals(getId(), that.getId()) &&
                Objects.equals(getDog(), that.getDog()) &&
                Objects.equals(getStart(), that.getStart()) &&
                Objects.equals(getFinish(), that.getFinish()) &&
                Objects.equals(getServiceTypes(), that.getServiceTypes()) &&
                Objects.equals(getTotalPrice(), that.getTotalPrice());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getDog(), getStart(), getFinish(), getServiceTypes(), getTotalPrice());
    }
}
