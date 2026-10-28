package rw.auca.rwandan_farmer_connect.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import rw.auca.rwandan_farmer_connect.domain.Farmer;

public interface FarmerRepository extends JpaRepository <Farmer, UUID> {

    List<Farmer> findByLastNameIgnoreCaseOrderByFirstNameAsc(String lastName);

    @Query("SELECT DISTINCT v.farmer FROM FarmVisit v WHERE v.agronomist.id = :agronomistId")
    List<Farmer> findFarmersByAgronomistId(@Param("agronomistId") UUID agronomistId);

}
