package rw.auca.rwandan_farmer_connect.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import rw.auca.rwandan_farmer_connect.domain.Recommendation;
import rw.auca.rwandan_farmer_connect.service.RecommendationService;

@RestController 
@RequestMapping ("/api/recommendations")
public class RecommendationController {

    @Autowired 
    private RecommendationService recommendationService;

    // E1:write a recommendation

    @PostMapping ("/save")
    public ResponseEntity<String> save(@RequestBody Recommendation recommendation,
                                       @RequestParam UUID farmVisitId
    ){
        String message = recommendationService.saveRecommendaction(recommendation, farmVisitId);
        if(message.contains("does not exists")){
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
        if(message.contains("only for")){
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    // E2: recommendation history of a farmer

    @GetMapping ("/farmer/{farmerCode}")
    public ResponseEntity<List<Recommendation>> getHistory(@PathVariable String farmerCode){
        return new ResponseEntity<>(recommendationService.getHistoryByFarmerCode(farmerCode), HttpStatus.OK);
    }

    // E3: delete a recommendation

    @DeleteMapping ("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id){
        String message = recommendationService.deleteRecommendation(id);
        if(message.contains("does not exist")){
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
        
    }

}
