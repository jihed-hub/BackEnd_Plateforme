 package plateforme_educative.repositorys;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import plateforme_educative.models.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{
	
	void deleteByProductid(int productid);
	
	Product findByProductid(int productid);
	List<Product> findAll();
}
