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

import rw.auca.rwandan_farmer_connect.domain.Farmer;
import rw.auca.rwandan_farmer_connect.service.FarmerService;

@RestController 
@RequestMapping ("/api/farmers")
public class FarmerController {

    @Autowired 
    private  FarmerService farmerService;

    // save a farmer

    @PostMapping ("/save")
    public ResponseEntity<String> save(@RequestBody Farmer farmer){
        String message = farmerService.saveFarmer(farmer);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    // get all famers

    @GetMapping ("/all")
    public ResponseEntity<List<Farmer>>getAll(){
        return  new ResponseEntity<>(farmerService.getAllFarmers(), HttpStatus.OK);
    }

    // get a farmer by id 
    @GetMapping ("/{id}")
    public ResponseEntity<?>getById(@PathVariable UUID id){
        Optional<Farmer> farmer = farmerService.getFarmerById(id);
        if(farmer.isEmpty()){
            return new ResponseEntity<>("The farmer with that id does not exists", HttpStatus.NOT_FOUND);
            
        }
        return new ResponseEntity<>(farmer.get(),HttpStatus.OK);
    }

    // update a farmer 
    
    @PutMapping ("/update/{id}")
    public ResponseEntity<String> update(@PathVariable UUID id, @RequestBody Farmer farmer){
        String message = farmerService.updateFarmer(id, farmer);
        if(message.contains("does not exist")){
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    // delete a farmer

    @DeleteMapping ("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id){
        String message = farmerService.deleteFarmer(id);
        if(message.contains("does not exist")){
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
