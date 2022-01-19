package plateforme_educative.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import plateforme_educative.models.Client;
import plateforme_educative.models.User;
import plateforme_educative.repositorys.ClientRepository;
import plateforme_educative.repositorys.UserRepository;


@Service
public class ClientService {
	
	@Autowired
	UserRepository userrepository ;
	@Autowired
	ClientRepository clientRepository;
	
	public User Afficher_client_by_name(String username) {
		return userrepository.findByUsername(username).get();
	}
	public Client edit_client(Client cl1, Client clt) {
		//Client cl1 = clientrepository.findById(clt.getId()).get();
	
		cl1.setAdresseclt(clt.getAdresseclt());
		cl1.setEmailclt(clt.getEmailclt());
		cl1.setFirstNameclt(clt.getFirstNameclt());
		cl1.setLastNameclt(clt.getLastNameclt());
		cl1.setTelclt(clt.getTelclt());
		
		clientRepository.save(cl1);
		return clt;
	}
	public Client ajouterclient_sansverif(Long id, Client clt) {
		User user = userrepository.findById(id).get();
		clientRepository.save(clt);
		user.setClient(clt);
		userrepository.save(user);

		return clt;
	}
	
}
