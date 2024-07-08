package br.com.uanderson.api_rest.model.dtos;

import br.com.uanderson.api_rest.model.entities.Customer;

import java.io.Serializable;
import java.time.LocalDateTime;

public record OrderDTO (
        long id,
        String orderNumber,
        LocalDateTime orderDate,
        LocalDateTime deliveryDate,
        String status,
        CustomerDTO customerDTO

) implements Serializable {
    public static Object fromFields(Object[] tuple, String[] aliases) {
        return new OrderDTO(
                (Long) tuple[0], // id
                (String) tuple[1], // orderNumber
                (LocalDateTime) tuple[2], // orderDate
                (LocalDateTime) tuple[3], // deliveryDate
                (String) tuple[4], // status
                CustomerDTO.from((Customer) tuple[5])

        );
    }//method



}//class
