package br.com.uanderson.api_rest.model.keys;


import br.com.uanderson.api_rest.model.entities.Order;
import br.com.uanderson.api_rest.model.entities.Product;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
public class OrderDetailsPK implements Serializable {

    private Order order;

    private Product product;


    @Override
    public boolean equals(Object object) {

        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        OrderDetailsPK that = (OrderDetailsPK) object;
        return Objects.equals(order, that.order) && Objects.equals(product, that.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(order, product);
    }
}//class
