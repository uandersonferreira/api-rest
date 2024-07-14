package br.com.uanderson.api_rest.dao.repositories;

import br.com.uanderson.api_rest.dao.extended.OrderExtenderRepository;
import br.com.uanderson.api_rest.model.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository//não é necessário
public interface OrderRepository extends JpaRepository<Order, Long>, OrderExtenderRepository {
    Optional<Order> findByOrderNumber(String orderNumber);
}
