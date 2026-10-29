package rw.auca.rwandan_farmer_connect.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rw.auca.rwandan_farmer_connect.domain.FarmVisit;
import rw.auca.rwandan_farmer_connect.domain.Recommendation;
import rw.auca.rwandan_farmer_connect.domain.VisitStatus;
import rw.auca.rwandan_farmer_connect.repository.FarmVisitRepository;
import rw.auca.rwandan_farmer_connect.repository.RecommendationRepository;

@Service 
public class RecommendationService {

    @Autowired 
    private  RecommendationRepository recommendationRepository;

    @Autowired 
    private FarmVisitRepository farmVisitRepository;

   // E1: write a recommendation for a VISITED farm visit

   public String saveRecommendaction(Recommendation recommendation, UUID farmVisitId){
    Optional<FarmVisit> found = farmVisitRepository.findById(farmVisitId);
    if(found.isEmpty()){
        return "The farm visit with that id does not exist";
    }
    FarmVisit farmVisit = found.get();
    if(farmVisit.getStatus() !=VisitStatus.VISITED){
        return "Recommendations are only for VISITED farm visits";
    }
    recommendation.setGivenDate(LocalDate.now());
    recommendation.setFarmVisit(farmVisit);
    recommendationRepository.save(recommendation);
    return "Recommendation saved successfully";
   }

   // E2: recommendation history of a farmer, newest first

   public List<Recommendation > getHistoryByFarmerCode(String farmerCode){
    return recommendationRepository.findHistoryByFarmerCode(farmerCode);
   }

   // E3: delete a recommendation

   public String deleteRecommendation(UUID id){
    if(!recommendationRepository.existsById(id)){
        return "The recommendation with that id does not exist";
    }
    recommendationRepository.deleteById(id);
    return "Recommendation deleted successfully";
   }

}
