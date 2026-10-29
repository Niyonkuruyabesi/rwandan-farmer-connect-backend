package rw.auca.rwandan_farmer_connect.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import rw.auca.rwandan_farmer_connect.domain.FarmVisit;
import rw.auca.rwandan_farmer_connect.domain.Recommendation;

public interface RecommendationRepository extends JpaRepository<Recommendation, UUID>{

    @Query("SELECT r FROM Recommendation r "
        + "WHERE r.farmVisit.farmer.farmerCode = :farmerCode "
        + "ORDER BY r.givenDate DESC"
    )
    List<Recommendation> findHistoryByFarmerCode(@Param("farmerCode")String farmerCode);

    boolean existsByFarmVisit(FarmVisit farmVisit);

}
