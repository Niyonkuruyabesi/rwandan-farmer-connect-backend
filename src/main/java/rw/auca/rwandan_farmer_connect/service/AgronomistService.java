package rw.auca.rwandan_farmer_connect.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import rw.auca.rwandan_farmer_connect.domain.Agronomist;
import rw.auca.rwandan_farmer_connect.domain.Crop;
import rw.auca.rwandan_farmer_connect.domain.Sector;
import rw.auca.rwandan_farmer_connect.repository.AgronomistRepository;
import rw.auca.rwandan_farmer_connect.repository.CropRepository;
import rw.auca.rwandan_farmer_connect.repository.SectorRepository;

@Service 
public class AgronomistService {

    @Autowired 
    private AgronomistRepository agronomistRepository;

    @Autowired 
    private CropRepository cropRepository;

    @Autowired 
    private SectorRepository sectorRepository;

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

    // D3: add one crop to an agronomist

    @Transactional 
    public String addCrop (UUID agronomistId, UUID cropId){
        Optional<Agronomist> foundAgronomist = agronomistRepository.findById(agronomistId);
        if(foundAgronomist.isEmpty()){
            return "The agronomist with that id does not exist";
        }
        Optional<Crop> foundCrop = cropRepository.findById(cropId);
        if(foundCrop.isEmpty()){
            return "That crop does not exist";
        }
        Agronomist agronomist = foundAgronomist.get();
        Crop crop = foundCrop.get();

        if(agronomist.getCrops().contains(crop)){
            return "The agronomist already has that crop";
        }
        agronomist.getCrops().add(crop);
        agronomistRepository.save(agronomist);
        return "Crop added to the agronomist";
    }

    // D4: move an agronominst to another sector sector

    public String changeSector(UUID agronomistId, int sectorNumber){
        Optional<Agronomist> foundAgronomist = agronomistRepository.findById(agronomistId);
        if(foundAgronomist.isEmpty()){
            return "The agronomist with that id does not exist";
        }
        Optional<Sector> foundSector = sectorRepository.findBySectorNumber(sectorNumber);
        if (foundSector.isEmpty()){
            return "The sector with that sectorNumber does not exist";
        }
        Agronomist agronomist = foundAgronomist.get();
        Sector sector = foundSector.get();

        if(agronomistRepository.existsBySectorAndIdNot(sector, agronomistId)){
            return " That sector is taken by another agronomist";

        }
        agronomist.setSector(sector);
        agronomistRepository.save(agronomist);
        return "Sector changed successfully";
    }

}
