package plateforme_educative.repositorys;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import plateforme_educative.models.Sub_thematics;

@Repository
public interface Sub_thematicsRepository extends JpaRepository<Sub_thematics,Long>{
	List<Sub_thematics> findByThematicId(Long thematicsId);
	Optional<Sub_thematics> findByIdAndThematicId(Long id,Long thematicsId );
}
