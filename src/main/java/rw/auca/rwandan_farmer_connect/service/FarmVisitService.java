package rw.auca.rwandan_farmer_connect.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rw.auca.rwandan_farmer_connect.domain.FarmVisit;
import rw.auca.rwandan_farmer_connect.domain.VisitStatus;
import rw.auca.rwandan_farmer_connect.repository.FarmVisitRepository;

@Service 
public class FarmVisitService {

    @Autowired 
    private FarmVisitRepository farmVisitRepository;

   

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

    // delete a farmVisit

    public String deleteFarmVisit(UUID id){
        if(!farmVisitRepository.existsById(id)){
            return "The farm visit with that id does not exist";
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
  



}
