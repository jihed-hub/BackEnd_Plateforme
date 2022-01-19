package plateforme_educative.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import plateforme_educative.models.Address;
import plateforme_educative.models.User;

@Repository
public interface AddressRepository extends JpaRepository<Address,Long>{
	Address findByUser(User user);
}
