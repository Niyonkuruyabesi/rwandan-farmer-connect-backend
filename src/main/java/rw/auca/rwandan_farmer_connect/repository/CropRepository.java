package rw.auca.rwandan_farmer_connect.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import rw.auca.rwandan_farmer_connect.domain.Crop;

public interface CropRepository extends JpaRepository<Crop, UUID> {

    @Query("SELECT c FROM Crop c WHERE c.agronomists IS EMPTY")
    List<Crop> findUnusedCrops();

}
