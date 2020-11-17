package cz.muni.fi.pa165.dto;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Create visit DTO representing transfered data about visit creation
 *
 * @author Marek Radimersky
 */
public class VisitCreateDTO {

    private Date start;
    private List<String> servicesId;

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public List<String> getServicesId() {
        return servicesId;
    }

    public void setServicesId(List<String> servicesId) {
        this.servicesId = servicesId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VisitCreateDTO)) return false;
        VisitCreateDTO that = (VisitCreateDTO) o;
        return Objects.equals(getStart(), that.getStart()) &&
                Objects.equals(getServicesId(), that.getServicesId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStart(), getServicesId());
    }
}
