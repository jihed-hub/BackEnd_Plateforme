package plateforme_educative.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import plateforme_educative.exception.ResourceNotFoundException;
import plateforme_educative.models.Thematics;
import plateforme_educative.repositorys.ThematicsRepository;

@RestController
@RequestMapping("/api/v1")
@PreAuthorize("hasRole('ADMIN')")
@CrossOrigin(origins = "*")
public class ThematicsController {
	
    @Autowired
	private ThematicsRepository thematicsRepository;
    
    @GetMapping("/thematics")
    public List<Thematics> getThematics(){
    	return thematicsRepository.findAll();
    }
    
    @GetMapping("/thematics/{id}")
    public ResponseEntity<Thematics> getThematicById(@PathVariable(value = "id") Long thematicsid)throws ResourceNotFoundException{
    	Thematics t=thematicsRepository.findById(thematicsid).orElseThrow(() -> new ResourceNotFoundException("thematic not found"));
    	return ResponseEntity.ok().body(t);
    }
    
    @PostMapping("addThematic")
   public Thematics createThematic(@Valid @RequestBody Thematics thematic) {
	   return thematicsRepository.save(thematic);
   }
    
    
    
}
