package br.com.student.portal.entity.zipcodestack;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ZipCodeStackLocation {

    private String postal_code;
    private String country_code;
    private double latitude;
    private double longitude;
    private String city;
    private String state;
    private String city_en;
    private String state_en;
    private String state_code;

}
