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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import rw.auca.rwandan_farmer_connect.domain.Agronomist;
import rw.auca.rwandan_farmer_connect.service.AgronomistService;

@RestController 
@RequestMapping ("/api/agronomists")
public class AgronomistController {

    @Autowired 
    private AgronomistService agronomistService;

    // save agronomist

    @PostMapping ("/save")
    public ResponseEntity<String> save(@RequestBody Agronomist agronomist){
        String message = agronomistService.saveAgronomist(agronomist);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    // get all agronomist

    @GetMapping ("/all")
    public ResponseEntity<List<Agronomist>> getAll() {
        return new ResponseEntity<>(agronomistService.getAllAgronomists(), HttpStatus.OK);

    }

    // get agronomist by ID

    @GetMapping ("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        Optional<Agronomist> agronomist = agronomistService.getAgronomistsById(id);
      
        if (agronomist.isEmpty()){
            return new ResponseEntity<>("The agronomist with that id does not exist", HttpStatus.OK);
        }
        return new ResponseEntity<>(agronomist.get(), HttpStatus.OK);
    }

    // update an agronomist 

    @PutMapping ("/update/{id}")
    public ResponseEntity<String> update(@PathVariable UUID id, @RequestBody Agronomist agronomist){
        String message = agronomistService.updateAgronomist(id, agronomist);
        if(message.contains("does not exist")){
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    // delete an agronomist

    @DeleteMapping ("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id){
        String message = agronomistService.deleteAgronomist(id);
        if(message.contains("does not exist")){
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    // agronomist who hold a crop

    @GetMapping ("/by-crop")
    public ResponseEntity<List<Agronomist>> getByCrop(@RequestParam String name) {
        return new ResponseEntity<>(agronomistService.getAgronomistsByCropName(name), HttpStatus.OK);
    }




}
