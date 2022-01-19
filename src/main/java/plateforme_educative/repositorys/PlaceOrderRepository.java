package plateforme_educative.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import plateforme_educative.models.PlaceOrder;

@Repository
public interface PlaceOrderRepository extends JpaRepository<PlaceOrder,Long> {

	PlaceOrder findByOrderId(int orderId);

}
