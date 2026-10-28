package rw.auca.rwandan_farmer_connect.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rw.auca.rwandan_farmer_connect.domain.Farmer;
import rw.auca.rwandan_farmer_connect.repository.FarmerRepository;

@Service 
public class FarmerService {

    @Autowired 
    private FarmerRepository farmerRepository;

    // save a farmer

    public String saveFarmer(Farmer farmer){
        farmerRepository.save(farmer);
        return "Farmer saved successfully";
    }

    // get all farmers

    public List<Farmer> getAllFarmers(){
        return farmerRepository.findAll();
    }

    // get a farmer by ID

    public Optional<Farmer> getFarmerById(UUID id){
        return farmerRepository.findById(id);
    }

    // update a farmer

    public String updateFarmer(UUID id, Farmer newFarmer){

        Optional<Farmer> found = farmerRepository.findById(id);
        if (found.isEmpty()){
            return "the farmer with that id does not exist";
        }
        Farmer farmer = found.get();
        farmer.setFirstName(newFarmer.getFirstName());
        farmer.setLastName(newFarmer.getLastName());
        farmer.setFarmerCode(newFarmer.getFarmerCode());
        farmer.setPhone(newFarmer.getPhone());
        farmer.setVillage(newFarmer.getVillage());
        farmerRepository.save(farmer);
        return "Farmer updated successfully";

    }

    // delete a farmer

    public String deleteFarmer(UUID id){
        if (!farmerRepository.existsById(id)){
            return "The farmer with that id does not exists";

        }
        farmerRepository.deleteById(id);
        return "farmer deleted successfully";
    }

    // get farmers by last name

    public List<Farmer> getFarmersByLastName(String lastName){
        return farmerRepository.findByLastNameIgnoreCaseOrderByFirstNameAsc(lastName);
    }

}
