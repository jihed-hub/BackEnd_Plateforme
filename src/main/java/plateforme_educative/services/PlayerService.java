package plateforme_educative.services;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import plateforme_educative.models.User;
import plateforme_educative.repositorys.UserRepository;

@Service
@Transactional
public class PlayerService {
    
    private final UserRepository playerRepository;
    @Autowired
    public PlayerService(UserRepository playerRepository) {
        this.playerRepository = playerRepository;
    }
    public User getCurrentUser(){
    	UserDetailImplementation principal = (UserDetailImplementation) SecurityContextHolder.
    			getContext().getAuthentication().getPrincipal();
    	return playerRepository.findOneByUsername(principal.getUsername());
    	
    }
}
