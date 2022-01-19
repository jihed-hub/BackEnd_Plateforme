package plateforme_educative.repositorys;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import plateforme_educative.models.DBFile;

@Repository
public interface DBFileRepository extends JpaRepository<DBFile, String>{
	void deleteById(String id);
	List<DBFile> findBySubthematicId(Long id);
	List<DBFile> findAll();
	
	
	
}
