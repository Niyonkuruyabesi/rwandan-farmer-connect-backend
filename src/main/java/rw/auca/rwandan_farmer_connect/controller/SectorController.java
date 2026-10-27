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

import rw.auca.rwandan_farmer_connect.domain.Sector;
import rw.auca.rwandan_farmer_connect.service.SectorService;

@RestController 
@RequestMapping ("/api/sectors")
public class SectorController {

    @Autowired 
    private  SectorService sectorService;

    // save a sector

    @PostMapping ("/save")
    public ResponseEntity<String> save(@RequestBody Sector sector){
        String message = sectorService.saveSector(sector);
        return new ResponseEntity<>(message, HttpStatus.CREATED); 
    }

    // get all sectors

    @GetMapping ("/all")
    public ResponseEntity<List<Sector>>getAll(){
        return new ResponseEntity<>(sectorService.getAllSectors(), HttpStatus.OK);
    }

    // get sector by it's ID

    @GetMapping ("/{id}")
    public ResponseEntity<?> getByID(@PathVariable UUID id){
        Optional<Sector> sector = sectorService.getSectorById(id);
        if(sector.isEmpty()){
            return new  ResponseEntity<>("The  sector with that id does not exist ", HttpStatus.NOT_FOUND);

        }
        return new ResponseEntity<>(sector.get(), HttpStatus.OK);
    }

    @PutMapping ("/update/{id}")
    public ResponseEntity<String> update(@PathVariable UUID id, @RequestBody Sector sector){
        String message = sectorService.updateSector(id, sector);
        if(message.contains("does not exist")){
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @DeleteMapping ("/delete/{id}")
    public ResponseEntity<String> delete (@PathVariable  UUID id){
        String message = sectorService.deleteSector(id);
        if(message.contains("does not exist")){
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
        return  new ResponseEntity<>(message, HttpStatus.OK);
    }


}
