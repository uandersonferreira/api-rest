package br.com.uanderson.api_rest.dao.repositories;

import br.com.uanderson.api_rest.model.dtos.OrderDTO;
import br.com.uanderson.api_rest.model.entities.Order;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DisplayName("Tests for Order Repository")
class OrderRepositoryTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgreSQLContainer
            = new PostgreSQLContainer<>("postgres:latest");

    @Autowired
    private OrderRepository orderRepository;


    @Test
    @DisplayName("verify connection with database postgres")
    void connectionTest() {
        assertTrue(postgreSQLContainer.isCreated());
        assertTrue(postgreSQLContainer.isRunning());
    }

    @Test
    void shouldFindOrderByOrderNumber() {
        // given
        String orderNumber = "ORD-0002";
        // when
        Optional<Order> order = orderRepository.findByOrderNumber(orderNumber);
        // then
        assertTrue(order.isPresent());
        assertEquals(order.get().getOrderNumber(), orderNumber);
    }

    @Test
    void shouldNotFindOrderByOrderNumber() {
        // given
        String orderNumber = "ORD-00048752";
        // when
        Optional<Order> order = orderRepository.findByOrderNumber(orderNumber);
        // then
        assertTrue(order.isEmpty());

    }

    @Test
    void shouldFindTwoOrdersByDeliveryStatus() {
        //given
        String deliveryStatus = "DELIVERED";

        // when
        //filter, orderNumber, since, until, status, customerId, page, size
        List<OrderDTO> orders = orderRepository.findByFilter(
                4, // filter
                null, // orderNumber
                null, // since
                null, // until
                deliveryStatus, // status
                null, // customerId
                0, // page
                10 // size
        );

        // then
        assertEquals(2, orders.size());
    }

    @Test
    void shouldCountByDeliveryStatus() {
        //given
        String deliveryStatus = "DELIVERED";

        // when
        //filter, orderNumber, since, until, status, customerId
        int total = orderRepository.countFindByFilter(
                4, // filter
                null, // orderNumber
                null, // since
                null, // until
                deliveryStatus, // status
                null // customerId
        );

        // then
        assertEquals(2, total);
    }


}//class