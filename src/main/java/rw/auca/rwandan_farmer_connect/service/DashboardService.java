package rw.auca.rwandan_farmer_connect.service;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rw.auca.rwandan_farmer_connect.domain.VisitStatus;
import rw.auca.rwandan_farmer_connect.repository.AgronomistRepository;
import rw.auca.rwandan_farmer_connect.repository.FarmVisitRepository;
import rw.auca.rwandan_farmer_connect.repository.FarmerRepository;
import rw.auca.rwandan_farmer_connect.repository.RecommendationRepository;
import rw.auca.rwandan_farmer_connect.repository.SectorRepository;

@Service 
public class DashboardService {

    @Autowired 
    private FarmerRepository farmerRepository;

    @Autowired 
    private AgronomistRepository agronomistRepository;

    @Autowired 
    private SectorRepository sectorRepository;

    @Autowired 
    private FarmVisitRepository farmVisitRepository;

    @Autowired 
    private RecommendationRepository recommendationRepository;

    public Map<String, Long> getSummary(){
        Map<String, Long> summary = new LinkedHashMap<>();
        summary.put("farmers", farmVisitRepository.count());
        summary.put("agronomists", agronomistRepository.count());
        summary.put("freeSectors", (long) sectorRepository.findFreeSectors().size());
        summary.put("visitsToday", farmVisitRepository.countByVisitDate(LocalDate.now()));
        summary.put("requested", farmVisitRepository.countByStatus(VisitStatus.REQUESTED));
        summary.put("recommendations", recommendationRepository.count());
        return summary;

    }

}
