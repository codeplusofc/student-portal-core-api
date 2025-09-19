package br.com.student.portal.config.zipcodestack;

import br.com.student.portal.service.ZipCodeStackAPI;
import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ZipCodeStackAPIConfiguration {
    @Value("${zipcodestack.api.url}")
    String url;
    @Bean
    public ZipCodeStackAPI zipCodeStackAPI() {
        return Feign.builder()
                .decoder(new JacksonDecoder())
                .encoder(new JacksonEncoder())
                .target(ZipCodeStackAPI.class, url);

    }
}
