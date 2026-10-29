package rw.auca.rwandan_farmer_connect.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import rw.auca.rwandan_farmer_connect.domain.FarmVisit;
import rw.auca.rwandan_farmer_connect.domain.VisitStatus;
import rw.auca.rwandan_farmer_connect.repository.FarmVisitRepository;
import rw.auca.rwandan_farmer_connect.repository.RecommendationRepository;

@Service 
public class FarmVisitService {

    @Autowired 
    private FarmVisitRepository farmVisitRepository;

    @Autowired 
    private RecommendationRepository recommendationRepository;

   

   // D1: safe saving

   public String saveFarmVisit(FarmVisit farmVisit){

    if (farmVisit.getVisitDate().isBefore(LocalDate.now())){
        return "Farm visit date cannot be in the past";
    }
    boolean busy = farmVisitRepository.existsByAgronomist_IdAndVisitDateAndStatusNot(
        farmVisit.getAgronomist().getId(), farmVisit.getVisitDate(), VisitStatus.DECLINED);

        if(busy)
            { 
                return "Agronomist already has a farm visit on that date";}
   

   farmVisit.setStatus(VisitStatus.REQUESTED);
   farmVisitRepository.save(farmVisit);
   return "farm visit saved successfully";
}



    // get all farmVisit

    public List<FarmVisit> getAllFarmVisits() {
        return farmVisitRepository.findAll();
    }

    // get a farmVisit by ID

    public Optional<FarmVisit> getFarmerVisitById(UUID id){
        return farmVisitRepository.findById(id);
    }

    // update a farmVisit

    public String updateFarmVisit(UUID id, FarmVisit newFarmVisit){
        Optional<FarmVisit> found = farmVisitRepository.findById(id);
        if(found.isEmpty()){
            return "The farm visit with that id does not exist";
        }
        FarmVisit farmVisit = found.get();
        farmVisit.setVisitDate(newFarmVisit.getVisitDate());
        farmVisit.setPurpose(newFarmVisit.getPurpose());
        farmVisitRepository.save(farmVisit);
        return "Farm visit updated successfully";
    }

    // E3: a farm visit that has recommendations cannot be deleted

    public String deleteFarmVisit(UUID id){
        Optional<FarmVisit> found = farmVisitRepository.findById(id);
        if(found.isEmpty()){
            return "The farm visit with that id not exist";
        }
        if(recommendationRepository.existsByFarmVisit(found.get())){
            return "That farm visit has recommendations, delete them first";
        }
        farmVisitRepository.deleteById(id);
        return "Farm visit deleted successfully";
    }

   

    // get farm visiit by status

    public List<FarmVisit> getFarmVisitByStatus(VisitStatus status){
        return farmVisitRepository.findByStatusOrderByVisitDateAsc(status);
    }

    // farm visits between two dates

    public List<FarmVisit> getFarmVisitsBetween(LocalDate start, LocalDate end){
        return farmVisitRepository.findByVisitDateBetweenOrderByVisitDateAsc(start, end);
    }

    // D2: status flow

    public String changeStatus(UUID id, VisitStatus newStatus){
        Optional<FarmVisit> found = farmVisitRepository.findById(id);
        if(found.isEmpty()){
            return "The farm visit with that id does not exist";
        }
        FarmVisit farmVisit = found.get();
        VisitStatus current = farmVisit.getStatus();

        boolean allowed = 
        (current == VisitStatus.REQUESTED
            &&(newStatus == VisitStatus.ACCEPTED || newStatus == VisitStatus.DECLINED)
        ) || (current == VisitStatus.ACCEPTED
            &&(newStatus == VisitStatus.VISITED || newStatus == VisitStatus.DECLINED)
        );

        if(!allowed){
            return "Cannot change status from " + current + " to " + newStatus;
        }

        farmVisit.setStatus(newStatus);
        farmVisitRepository.save(farmVisit);
        return "Farm visit status updated successfully";
    }

    // F1: farm visit per status

    public List<Object[]> getFarmVisitStats(){
        return farmVisitRepository.countFarmVisitsStatus();
    }

    // F2: agronomist is absent

    @Transactional 
    public String cancelDay(UUID agronomistId, LocalDate date){
        int count = farmVisitRepository.cancelDay(agronomistId, date, VisitStatus.DECLINED, VisitStatus.VISITED);
        return count +"farm visits cancelled";
    }
  



}
