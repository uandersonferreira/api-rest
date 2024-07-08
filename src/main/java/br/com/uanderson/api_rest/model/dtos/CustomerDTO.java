package br.com.uanderson.api_rest.model.dtos;

import br.com.uanderson.api_rest.model.entities.Customer;

import java.io.Serializable;

public record CustomerDTO (
    long id,
    String name,
    String email,
    String phone

) implements Serializable {

    public static CustomerDTO from(Customer customer) {
        return new CustomerDTO(
                customer.getId(),
                customer.getName(),
                customer.getEmail(),
                customer.getPhone()
        );
    }

}//class
