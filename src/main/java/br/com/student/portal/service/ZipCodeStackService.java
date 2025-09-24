package br.com.student.portal.service;

import br.com.student.portal.entity.zipcodestack.ZipCodeStackLocationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class ZipCodeStackService {
    @Value("${zipcodestack.api.key}")
    private String apiKey;
    @Autowired
    private ZipCodeStackAPI zipCodeStackAPI;

    public ZipCodeStackLocationResponse getLocation(String code, String country){
        try{
            return zipCodeStackAPI.getLocation(code, country, apiKey);
        }catch (RuntimeException runtimeException){
            throw new RuntimeException("Error trying to get location");

        }

    }
}
