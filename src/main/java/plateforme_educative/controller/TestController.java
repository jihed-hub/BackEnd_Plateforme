package plateforme_educative.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import plateforme_educative.models.User;
import plateforme_educative.services.UserDetailsServiceImpl;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/test")
public class TestController {
	@Autowired
	UserDetailsServiceImpl userService;
	
	@GetMapping("/all")
	public String allAccess() {
		return "Public Content.";
	}
	
	@GetMapping("/admin")
	@PreAuthorize("hasRole('ADMIN')")
	public String adminAccess() {
		return "Admin Board.";
	}
	
	@GetMapping("/internaute")
	@PreAuthorize("hasRole('USER')")
	public String userAccess() {
		return "User Content.";
	}
	
	@GetMapping("/Finduserr/{id}")
	public User FinduserById(@PathVariable(value = "id") long id) {
	
			return userService.Afficher_user_by_name(id);
			
		
		
}
	  @GetMapping(value = "/apii/image/logo/{image}")
	   public ResponseEntity<InputStreamResource> getclientImage(@PathVariable(value = "image") String image ) throws IOException {

	       ClassPathResource imgFile = new ClassPathResource(image);

	       return ResponseEntity
	               .ok()
	               .contentType(MediaType.IMAGE_PNG)
	               .body(new InputStreamResource(imgFile.getInputStream()));
	   }


}
