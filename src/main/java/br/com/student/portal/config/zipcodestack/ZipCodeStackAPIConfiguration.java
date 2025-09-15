package br.com.student.portal.config.zipcodestack;

import br.com.student.portal.service.ZipCodeStackAPI;
import feign.Feign;
import feign.codec.Encoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ZipCodeStackAPIConfiguration {
    @Value("${zipcodestack.api.url}")
    String url;

    @Bean
    public Encoder feignEncoder() {
        return new JacksonEncoder();
    }

    public ZipCodeStackAPI zipCodeStackAPI() {



        return Feign.builder()
                .decoder(new JacksonDecoder);

    }
}
