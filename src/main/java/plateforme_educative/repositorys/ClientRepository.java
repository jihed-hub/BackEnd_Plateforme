package plateforme_educative.repositorys;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import plateforme_educative.models.Client;


@Repository
public interface ClientRepository extends JpaRepository<Client, Long>{
	 @Query("SELECT c from Client c where c.etat=0")
		List<Client> findByEtat();
	 @Query("SELECT c from Client c where c.etat=0 and c.notif_etat=0")
		List<Client> findByEtatnotification();
}
