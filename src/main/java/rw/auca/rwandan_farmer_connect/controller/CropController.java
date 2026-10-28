package rw.auca.rwandan_farmer_connect.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rw.auca.rwandan_farmer_connect.domain.Crop;
import rw.auca.rwandan_farmer_connect.service.CropService;

@RestController 
@RequestMapping ("/api/crops")
public class CropController {

    @Autowired 
    private CropService cropService;

    // save a new crop
    @PostMapping ("/save")
    public ResponseEntity<String> save(@RequestBody Crop crop){
        String message = cropService.saveCrop(crop);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    // get all crops

    @GetMapping ("/all")
    public ResponseEntity<List<Crop>> getAll(){
        return new ResponseEntity<>(cropService.getAllCrops(), HttpStatus.OK);
    }

    // get a crop by id 
    @GetMapping ("/{id}")
    public ResponseEntity<?>getById(@PathVariable UUID id){
        Optional<Crop> crop = cropService.getCropById(id);
        if(crop.isEmpty()){
            return new ResponseEntity<>("The crop with that id not exist", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(crop.get(), HttpStatus.OK);
    }

    // update a crop

    @PutMapping ("/update/{id}")
    public ResponseEntity<String> update(@PathVariable UUID id, @RequestBody Crop crop){
        String message = cropService.updateCrop(id, crop);
        if(message.contains("does not exist")){
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
        return  new ResponseEntity<>(message, HttpStatus.OK);
    }

    @DeleteMapping ("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id){
        String message = cropService.deleteCrop(id);
        if (message.contains("does not exist")){
            return new ResponseEntity<>(message, HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    // get crop nobody holds

    @GetMapping ("/unused")
    public ResponseEntity<List<Crop>> getUnused(){
        return new ResponseEntity<>(cropService.getUnusedCrops(), HttpStatus.OK);
    }

}
