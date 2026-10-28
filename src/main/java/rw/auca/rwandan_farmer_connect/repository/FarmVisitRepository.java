package rw.auca.rwandan_farmer_connect.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import rw.auca.rwandan_farmer_connect.domain.FarmVisit;
import rw.auca.rwandan_farmer_connect.domain.VisitStatus;

public interface FarmVisitRepository extends JpaRepository<FarmVisit, UUID> {

    List<FarmVisit> findByStatusOrderByVisitDateAsc(VisitStatus status);

    List<FarmVisit> findByVisitDateBetweenOrderByVisitDateAsc(LocalDate start, LocalDate end);

    boolean existsByAgronomist_IdAndVisitDateAndStatusNot(UUID agronomistId, LocalDate visitDate, VisitStatus status );

}
