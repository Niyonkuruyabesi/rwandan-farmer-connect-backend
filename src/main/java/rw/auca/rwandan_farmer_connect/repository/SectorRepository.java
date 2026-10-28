package rw.auca.rwandan_farmer_connect.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import rw.auca.rwandan_farmer_connect.domain.Sector;

public interface SectorRepository extends JpaRepository<Sector, UUID> {

    @Query("SELECT s FROM Sector s WHERE NOT EXISTS "
        + "(SELECT a FROM Agronomist a where a.sector = s)"
        + "ORDER BY s.sectorNumber ASC"
    )
    List<Sector> findFreeSectors();


}
