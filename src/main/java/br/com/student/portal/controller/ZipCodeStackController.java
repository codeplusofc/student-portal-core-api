package br.com.student.portal.controller;

import br.com.student.portal.entity.zipcodestack.ZipCodeStackLocationResponse;
import br.com.student.portal.service.ZipCodeStackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api-zipcode")
public class ZipCodeStackController {
    @Autowired
    private ZipCodeStackService zipCodeStackService;

    @GetMapping("/get")
    public ZipCodeStackLocationResponse getLocation(@RequestParam(value = "code") String code, @RequestParam(value = "country") String country){
        return zipCodeStackService.getLocation(code,country);
    }

}
