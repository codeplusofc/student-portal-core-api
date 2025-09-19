package br.com.student.portal.service;


import br.com.student.portal.entity.zipcodestack.ZipCodeStackLocationResponse;
import feign.Headers;
import feign.Param;
import feign.RequestLine;
import org.springframework.stereotype.Service;

@Service
public interface ZipCodeStackAPI {
    @RequestLine("GET /v1/search?codes={codes}&country={country}&apiKey={apiKey}")
    @Headers("Content-Type:application/json")
    ZipCodeStackLocationResponse getLocation(@Param("codes") String code, @Param("country") String country, @Param("apiKey") String apiKey);


}
