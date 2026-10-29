package rw.auca.rwandan_farmer_connect.domain;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity 
@Table (name = "recommendation")
public class Recommendation {

    @Id 
    @GeneratedValue (strategy = GenerationType.UUID)
    private UUID id;

    private String inputName;

    private String advice;

    private int quantityKg;

    private LocalDate givenDate;

    @ManyToOne 
    @JoinColumn (name="farm-visit-id")
    private FarmVisit farmVisit;

    public Recommendation() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getInputName() {
        return inputName;
    }

    public void setInputName(String inputName) {
        this.inputName = inputName;
    }

    public String getAdvice() {
        return advice;
    }

    public void setAdvice(String advice) {
        this.advice = advice;
    }

    public int getQuantityKg() {
        return quantityKg;
    }

    public void setQuantityKg(int quantityKg) {
        this.quantityKg = quantityKg;
    }

    public LocalDate getGivenDate() {
        return givenDate;
    }

    public void setGivenDate(LocalDate givenDate) {
        this.givenDate = givenDate;
    }

    public FarmVisit getFarmVisit() {
        return farmVisit;
    }

    public void setFarmVisit(FarmVisit farmVisit) {
        this.farmVisit = farmVisit;
    }

    

}
