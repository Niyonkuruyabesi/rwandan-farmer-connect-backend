package rw.auca.rwandan_farmer_connect.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import rw.auca.rwandan_farmer_connect.domain.Crop;

public interface CropRepository extends JpaRepository<Crop, UUID> {

}
