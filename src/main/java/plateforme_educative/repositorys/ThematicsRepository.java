package plateforme_educative.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import plateforme_educative.models.Thematics;

@Repository
public interface ThematicsRepository extends JpaRepository<Thematics,Long>{
	
}
