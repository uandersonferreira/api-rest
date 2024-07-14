package br.com.uanderson.api_rest.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PagingDataDTO implements Serializable {
    private int pageNumber; // current page number

    private int pageSize; // number of rows per page

    private int totalRows; // total number of rows
}
