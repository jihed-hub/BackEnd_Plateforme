package plateforme_educative.repositorys;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import plateforme_educative.models.Client;
import plateforme_educative.models.User;



@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	User findById(int id);
	Optional<User> findByUsername(String username);
	Boolean existsByUsername(String username);
	Boolean existsByEmail(String email);
    User findOneByUsername(String username);
    User findByClientId(@Param("idclient") Long id);
    @Query("SELECT c from Client c where c.etat=0 and c.notif_etat=0")
	List<Client> findByEtatnotification();


	

}
