package plateforme_educative.repositorys;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import plateforme_educative.models.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart,Long>{
	
	List<Cart> findAllByOrderId(int orderId);
	
	List<Cart> findByEmail(String email);
	
	Boolean existsByProductId(int productid);
	
	Cart findByCartId(int cartId);
	
	Cart findByCartIdAndEmail(int cartId,String email);
	
	void deleteByCartIdAndEmail(int cartId,String email);
	
	List<Cart> findAllByEmail(String email);
}
