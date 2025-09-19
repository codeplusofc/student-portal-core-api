package br.com.student.portal.entity.zipcodestack;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;
import java.util.Map;
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ZipCodeStackLocationResponse {

    private ZipCodeStackPostalCodeQuary zipCodeStackPostalCodeQuary;
    private Map<String, List<ZipCodeStackLocation>> results;

}
