package cz.muni.fi.pa165.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * Entity class representing PerformedService during Visit.class
 *
 * @author Martin.Palic
 */
@Entity
public class PerformedService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @ManyToOne
    @NotNull
    private Visit visit;
    @ManyToOne
    @NotNull
    private ServiceType serviceType;

    public PerformedService() {
    }

    public PerformedService(Visit visit, ServiceType serviceType) {
        this.visit = visit;
        this.serviceType = serviceType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PerformedService)) return false;
        PerformedService that = (PerformedService) o;
        return Objects.equals(getId(), that.getId()) &&
                Objects.equals(getVisit(), that.getVisit()) &&
                Objects.equals(getServiceType(), that.getServiceType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getVisit(), getServiceType());
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public Visit getVisit() {
        return visit;
    }

    public void setVisit(Visit visit) {
        this.visit = visit;
    }

    public ServiceType getServiceType() {
        return serviceType;
    }

    public void setServiceType(ServiceType serviceType) {
        this.serviceType = serviceType;
    }

}
