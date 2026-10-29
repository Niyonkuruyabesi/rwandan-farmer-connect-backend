package rw.auca.rwandan_farmer_connect.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import rw.auca.rwandan_farmer_connect.domain.FarmVisit;
import rw.auca.rwandan_farmer_connect.domain.VisitStatus;

public interface FarmVisitRepository extends JpaRepository<FarmVisit, UUID> {


    //B2
    List<FarmVisit> findByStatusOrderByVisitDateAsc(VisitStatus status);

    //B3
    List<FarmVisit> findByVisitDateBetweenOrderByVisitDateAsc(LocalDate start, LocalDate end);

    //D1
    boolean existsByAgronomist_IdAndVisitDateAndStatusNot(UUID agronomistId, LocalDate visitDate, VisitStatus status );

    //F1
    @Query("SELECT v.status, COUNT(v) FROM FarmVisit v GROUP BY v.status")
    List<Object[]> countFarmVisitsStatus();

    //F2
    @Modifying 
    @Query ("UPDATE FarmVisit v SET v.status = :declined "
        + "WHERE v.agronomist.id = :agronomistId "
        + "AND v.visitDate = :date " 
        + "AND v.status <> :visited "
    )
    int cancelDay(@Param("agronomistId") UUID agronomistId,
                  @Param ("date") LocalDate date,
                  @Param ("declined") VisitStatus declined,
                  @Param ("visited") VisitStatus visited
);

// bonus dashboard
long countByVisitDate(LocalDate visitDate);

long countByStatus(VisitStatus status);
}
