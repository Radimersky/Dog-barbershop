package cz.muni.fi.pa165.dto;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * PerformedService DTO representing transfered data about PerformedService
 *
 * @author Martin.Palic
 */
public class PerformedServiceDTO {

    private Long Id;
    @NotNull
    private VisitDTO visit;
    @NotNull
    private ServiceTypeDTO serviceType;

    public PerformedServiceDTO() {
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public VisitDTO getVisit() {
        return visit;
    }

    public void setVisit(VisitDTO visit) {
        this.visit = visit;
    }

    public ServiceTypeDTO getServiceType() {
        return serviceType;
    }

    public void setServiceType(ServiceTypeDTO serviceType) {
        this.serviceType = serviceType;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PerformedServiceDTO)) return false;
        PerformedServiceDTO that = (PerformedServiceDTO) o;
        return Objects.equals(getId(), that.getId()) &&
                getVisit().equals(that.getVisit()) &&
                getServiceType().equals(that.getServiceType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getVisit(), getServiceType());
    }
}
