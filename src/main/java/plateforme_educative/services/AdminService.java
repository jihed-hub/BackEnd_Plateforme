package plateforme_educative.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import plateforme_educative.models.Client;
import plateforme_educative.models.User;
import plateforme_educative.repositorys.ClientRepository;
import plateforme_educative.repositorys.UserRepository;



@Service
public class AdminService {
	@Autowired
	ClientRepository clientRepository;
	@Autowired
	UserRepository userrepository;
	public List<Client> clientsinactifs() {
		return clientRepository.findByEtat();
	}
	
	public List<Client> Accepter_Client(long idclient) {
		  Client cl = clientRepository.findById(idclient).get();
		  User user = userrepository.findByClientId(idclient);
		  cl.setEtat(1);
		  clientRepository.save(cl);
		  user.setEtat(true);
		  userrepository.save(user);
		return clientRepository.findByEtat();
	}
	
	public String refuser_client(long idclient) {
		userrepository.deleteById(idclient);
		return "user supprime" ;
		
	}
	public int nbrdemandesinscriptionsnotif() {
		return clientRepository.findByEtatnotification().size(); 
	}
	public int Demandesnotificationsaffiches() {
		List<Client> cltnotis = clientRepository.findByEtatnotification();
		for (int i=0;i<cltnotis.size();i++){
			  cltnotis.get(i).setNotif_etat(true);
			  clientRepository.save(cltnotis.get(i));		  
		}
		return clientRepository.findByEtatnotification().size() ;
	}
	public User Afficher_admin_by_name(String username) {
		return userrepository.findByUsername(username).get();

	}
	public List<Client> getAllClient() {
		return   clientRepository.findAll();
	}
	public String deleteClient(long idclient) {
		User user=userrepository.findByClientId(idclient);
		userrepository.delete(user);
		clientRepository.deleteById(idclient);
		return "client supprimé";
	}
}
