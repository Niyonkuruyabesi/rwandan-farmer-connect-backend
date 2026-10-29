package rw.auca.rwandan_farmer_connect.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import rw.auca.rwandan_farmer_connect.domain.FarmVisit;
import rw.auca.rwandan_farmer_connect.domain.VisitStatus;
import rw.auca.rwandan_farmer_connect.service.FarmVisitService;

@RestController 
@RequestMapping ("/api/farm-visits")
public class FarmVisitController {

    @Autowired 
    private FarmVisitService farmVisitService;

    // D1: safe saving
    @PostMapping ("/save")
    public ResponseEntity<String> save(@RequestBody FarmVisit farmVisit){
        String message = farmVisitService.saveFarmVisit(farmVisit);
        if(message.contains("past")){
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
        if (message.contains("already")){
            return new ResponseEntity<>(message, HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    // get all farm visit

    @GetMapping ("/all")
        public ResponseEntity<List<FarmVisit>> getAll(){
            return new ResponseEntity<>(farmVisitService.getAllFarmVisits(), HttpStatus.OK);
        }

    // get a farm visit by ID
    
    @GetMapping ("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id){
        Optional<FarmVisit> farmVisit = farmVisitService.getFarmerVisitById(id);
        if(farmVisit.isEmpty()){
            return new ResponseEntity<>("The farm visit with thatid does not exist", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(farmVisit.get(), HttpStatus.OK);
    }

    // update a farm visit

    @PutMapping ("/update/{id}")
    public ResponseEntity<String> update(@PathVariable UUID id, @RequestBody FarmVisit farmVisit) {
        String message = farmVisitService.updateFarmVisit(id, farmVisit);
        if (message.contains("does not exist")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    // delete a farm visit

    @DeleteMapping ("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String message = farmVisitService.deleteFarmVisit(id);
        if (message.contains("does not exist")){
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
        if(message.contains("has recommendations")){
            return new ResponseEntity<>(message, HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    // farm visit by status

    @GetMapping ("/by-status")
    public ResponseEntity<List<FarmVisit>>getByStatus(@RequestParam VisitStatus status){
        return new ResponseEntity<>(farmVisitService.getFarmVisitByStatus(status), HttpStatus.OK);

    }

    // farm Visit between two dates

    @GetMapping ("/between")
    public ResponseEntity<List<FarmVisit>> getBetween(@RequestParam String start, @RequestParam String end){
        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);
        return new ResponseEntity<>(farmVisitService.getFarmVisitsBetween(startDate, endDate), HttpStatus.OK);
    }

    // D2: status flow

    @PatchMapping ("/{id}/status")
    public ResponseEntity<String> changeStatus(@PathVariable UUID id, @RequestParam VisitStatus status){
        String message = farmVisitService.changeStatus(id, status);
        if(message.contains("does not exist")){
            return new  ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
        if(message.contains("Cannot change")){
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    //F1: farm visits per status

    @GetMapping ("/stats/by-status")
    public ResponseEntity<List<Object[]>> getStats(){
        return new ResponseEntity<>(farmVisitService.getFarmVisitStats(), HttpStatus.OK);
    }

    //F2: agronomist is absent

    @PatchMapping ("/cancel-day")
    public ResponseEntity<String> cancelDay(@RequestParam UUID agronomistId, @RequestParam String date){
        LocalDate day = LocalDate.parse(date);
        String message = farmVisitService.cancelDay(agronomistId, day);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    
}
