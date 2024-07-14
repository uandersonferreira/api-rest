package br.com.uanderson.api_rest.model.dtos;

import lombok.Getter;

import java.io.Serializable;
import java.util.List;
@Getter
public class OrderResultDTO extends BaseResultDTO implements Serializable {
    private List<OrderDTO> orders;
    private PagingDataDTO pagingData;


    public OrderResultDTO(List<OrderDTO> orders, PagingDataDTO pagingData) {
        this.orders = orders;
        this.pagingData = pagingData;
    }

    public OrderResultDTO(String code, String message) {
        super(code, message);
    }

}//class
