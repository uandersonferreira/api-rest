package br.com.uanderson.api_rest.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BaseResultDTO {

    private String code = "0";
    private String message = "";

}//class
