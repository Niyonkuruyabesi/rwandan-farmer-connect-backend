package rw.auca.rwandan_farmer_connect.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rw.auca.rwandan_farmer_connect.domain.Crop;
import rw.auca.rwandan_farmer_connect.repository.CropRepository;

@Service 
public class CropService {

    @Autowired 
    private  CropRepository cropRepository;

    // save a new crop
   
    public String saveCrop(Crop crop){
        cropRepository.save(crop);
        return "Crop saved successfully";
    }

    // get all crops

    public List<Crop> getAllCrops(){
        return cropRepository.findAll();
    }

    // get crop by ID;

    public Optional<Crop> getCropById(UUID id){
        return  cropRepository.findById(id);
    }

    // update a crop

    public String updateCrop(UUID id, Crop newCrop){
        Optional<Crop> found = cropRepository.findById(id);
        if(found.isEmpty()){
            return"the crop with that id does not exist";
        }
        Crop crop = found.get();
        crop.setName(newCrop.getName());
        cropRepository.save(crop);
        return "Crop updated successfully";

    }

    // delete a crop

    public String deleteCrop(UUID id){

        if(!cropRepository.existsById(id)){
            return "The crop with that id does not exist";
        }
        cropRepository.deleteById(id);
        return "Crop deleted successfully";
    }

}
