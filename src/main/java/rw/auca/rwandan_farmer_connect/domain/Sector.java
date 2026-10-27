package rw.auca.rwandan_farmer_connect.domain;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity 
@Table (name="sector")
public class Sector {

    @Id 
    @GeneratedValue (strategy = GenerationType.UUID)
    private UUID id;


    @Column (name="sector_number")
    private String sectorNumber;

    private String name;

    public Sector() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getSectorNumber() {
        return sectorNumber;
    }

    public void setSectorNumber(String sectorNumber) {
        this.sectorNumber = sectorNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
        

}
