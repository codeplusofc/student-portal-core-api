package br.com.student.portal.config.zipcodestack;

import br.com.student.portal.service.ZipCodeStackAPI;
import feign.Feign;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ZipCodeStackAPIConfiguration {
    @Value("${zipcodestack.api.url}")
    String url;

    public ZipCodeStackAPI zipCodeStackAPI() {
        return Feign.builder()
//                .decoder(new JacksonDecoder)

    }
}
