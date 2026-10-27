package rw.auca.rwandan_farmer_connect.domain;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity 
@Table (name="farm_visit")
public class FarmVisit {

    @Id 
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private LocalDate visitDate;

    @Enumerated (EnumType.STRING)
    private VisitStatus status;

    private String purpose;

    @ManyToOne 
    @JoinColumn (name = "agronomist_id")
    private Agronomist agronomist;

    @ManyToOne 
    @JoinColumn (name="farmer_id")
    private Farmer farmer;

    public FarmVisit() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDate getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(LocalDate visitDate) {
        this.visitDate = visitDate;
    }

    public VisitStatus getStatus() {
        return status;
    }

    public void setStatus(VisitStatus status) {
        this.status = status;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public Agronomist getAgronomist() {
        return agronomist;
    }

    public void setAgronomist(Agronomist agronomist) {
        this.agronomist = agronomist;
    }

    public Farmer getFarmer() {
        return farmer;
    }

    public void setFarmer(Farmer farmer) {
        this.farmer = farmer;
    }

    

}
