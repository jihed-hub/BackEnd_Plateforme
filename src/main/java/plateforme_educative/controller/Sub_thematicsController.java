package plateforme_educative.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import plateforme_educative.exception.ResourceNotFoundException;
import plateforme_educative.models.Sub_thematics;
import plateforme_educative.models.Thematics;
import plateforme_educative.repositorys.Sub_thematicsRepository;
import plateforme_educative.repositorys.ThematicsRepository;

@CrossOrigin(origins = "*")
@RestController
public class Sub_thematicsController {

	@Autowired
	private ThematicsRepository thematicsRepository;
	@Autowired
	private Sub_thematicsRepository sub_thematicsRepository;
	
    @GetMapping("getsubthematics/{thematicsId}")
	public List<Sub_thematics> GetSub_thematicsByThematic(@PathVariable(value = "thematicsId") Long thematicsId){
		return sub_thematicsRepository.findByThematicId(thematicsId);
	}
	
    @PostMapping("addsubthematic/{thematicid}")
	public Sub_thematics createSub_thematic(@PathVariable(value="thematicid") Long thematicid,@Valid @RequestBody
			Sub_thematics sub_thematic) throws ResourceNotFoundException{
				Thematics t=thematicsRepository.findById(thematicid).orElseThrow(()->new ResourceNotFoundException("thematic not found"));
				sub_thematic.setThematic(t);
				return sub_thematicsRepository.save(sub_thematic);
	}

}
