package br.com.uanderson.api_rest.services;

import br.com.uanderson.api_rest.dao.repositories.CustomerRepository;
import br.com.uanderson.api_rest.dao.repositories.OrderRepository;
import br.com.uanderson.api_rest.model.dtos.OrderDTO;
import br.com.uanderson.api_rest.model.dtos.OrderResultDTO;
import br.com.uanderson.api_rest.model.dtos.PagingDataDTO;
import br.com.uanderson.api_rest.model.entities.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;

    public OrderResultDTO getOrdersByFilter(int filter, String orderNumber,
                                            String since, String until, String status, String customerEmail,
                                            int page, int size) {

        //1-orderNumber, 2-order date, 3-delivery date, 4-status
        try {
            // Check if customer exists
            Customer customer = customerRepository.findByEmail(customerEmail).orElseThrow(
                    () -> new RuntimeException("Customer not found")
            );

            LocalDateTime sinceData = null;
            LocalDateTime untilData = null;

            try {
                if (filter == 2 || filter == 3) {
                    sinceData = LocalDateTime.parse(since, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                    untilData = LocalDateTime.parse(until, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                }
            } catch (DateTimeParseException | NullPointerException ex) {
                throw new RuntimeException("Invalid date format -> " + ex.getMessage());
            }

            List<OrderDTO> orders = orderRepository.findByFilter(filter, orderNumber, sinceData, untilData,
                    status, customer.getId(), page, size);

            int totalRows = orderRepository.countFindByFilter(filter, orderNumber, sinceData, untilData,
                    status, customer.getId());

            PagingDataDTO pagingDataDTO = new PagingDataDTO(page, size, totalRows);

            OrderResultDTO orderResultDTO = new OrderResultDTO(orders, pagingDataDTO);
            return orderResultDTO;


        } catch (RuntimeException ex) {
            return new OrderResultDTO("L001", ex.getMessage());
        }
    }//method

}//class
