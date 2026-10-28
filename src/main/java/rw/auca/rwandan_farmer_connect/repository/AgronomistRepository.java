package rw.auca.rwandan_farmer_connect.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import rw.auca.rwandan_farmer_connect.domain.Agronomist;

public interface AgronomistRepository extends JpaRepository<Agronomist, UUID> {
    
    @Query("SELECT a FROM Agronomist a JOIN a.crops c WHERE LOWER(c.name) = LOWER(:name)")
    List<Agronomist>findAgronomistsByCropName(@Param("name") String name);

}
