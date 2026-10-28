package rw.auca.rwandan_farmer_connect.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rw.auca.rwandan_farmer_connect.domain.Agronomist;
import rw.auca.rwandan_farmer_connect.repository.AgronomistRepository;

@Service 
public class AgronomistService {

    @Autowired 
    private AgronomistRepository agronomistRepository;

    // save agronomist

    public String saveAgronomist(Agronomist agronomist){
        agronomistRepository.save(agronomist);
        return "Agronomist saved successfully";
    }

    // get all agronomists

    public List<Agronomist> getAllAgronomists(){
        return agronomistRepository.findAll();
    }

    // get agronomist by ID

    public Optional<Agronomist> getAgronomistsById(UUID id){
        return agronomistRepository.findById(id);
    }

    // update an agronomist

    public String updateAgronomist(UUID id, Agronomist newAgronomist){
        Optional<Agronomist> found = agronomistRepository.findById(id);
        if(found.isEmpty()){
            return "The agronomistwith that id does not exist";
        }
        Agronomist agronomist = found.get();
        agronomist.setFirstName(newAgronomist.getFirstName());
        agronomist.setLastName(newAgronomist.getLastName());
        agronomist.setEmail(newAgronomist.getEmail());
        agronomistRepository.save(agronomist);
        return "Agronomist updated successfully";
    }

    // delete agronomist

    public String deleteAgronomist(UUID id){
        if(!agronomistRepository.existsById(id)){
            return "The agronomist with that id does not exist";
        }
        agronomistRepository.deleteById(id);
        return "Agronomist deleted successfully";
    }

    // agronomist who hold a crop

    public List<Agronomist> getAgronomistsByCropName(String name){
        return agronomistRepository.findAgronomistsByCropName(name);
    }

}
