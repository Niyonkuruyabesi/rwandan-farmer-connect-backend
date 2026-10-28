package rw.auca.rwandan_farmer_connect.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import rw.auca.rwandan_farmer_connect.domain.Farmer;

public interface FarmerRepository extends JpaRepository <Farmer, UUID> {

    List<Farmer> findByLastNameIgnoreCaseOrderByFirstNameAsc(String lastName);

}
