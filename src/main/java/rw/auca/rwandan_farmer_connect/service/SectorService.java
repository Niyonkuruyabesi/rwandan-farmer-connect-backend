package rw.auca.rwandan_farmer_connect.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rw.auca.rwandan_farmer_connect.domain.Sector;
import rw.auca.rwandan_farmer_connect.repository.SectorRepository;

@Service 
public class SectorService {

    @Autowired 
    private SectorRepository sectorRepository;

    // save a sector

    public String saveSector(Sector sector){
        sectorRepository.save(sector);
            return "Sector saved successfully";
        }

        // get all sectors

        public List<Sector> getAllSectors(){
            return sectorRepository.findAll();
        }

        // get sector by ID

        public Optional<Sector> getSectorById(UUID id){
            return sectorRepository.findById(id);
        }

        // update a sector

        public String updateSector(UUID id, Sector newSector){
            Optional<Sector> found = sectorRepository.findById(id);
            if(found.isEmpty()){
                return "The sector with that id does not exists";
            }
            Sector sector = found.get();
            sector.setSectorNumber(newSector.getSectorNumber());
            sector.setName(newSector.getName());
            sectorRepository.save(sector);
            return "Sector update successfully";
        }

        // delete a sector 

        public  String deleteSector(UUID id){
            if(!sectorRepository.existsById(id)){
                return "The sector with that id does not exist";
            }
            sectorRepository.deleteById(id);
            return "Sector deleted successfully";
        }
    }


