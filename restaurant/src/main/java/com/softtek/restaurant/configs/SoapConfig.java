package com.softtek.restaurant.configs;

import com.softtek.restaurant.clients.LabServiceClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class SoapConfig {

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("com.restaurante.normal.client.generated");
        return marshaller;
    }

    @Bean
    public LabServiceClient labServiceClient(Jaxb2Marshaller marshaller) {
        LabServiceClient client = new LabServiceClient();
        client.setDefaultUri("http://localhost:8080/services/restaurante/labService");
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        return client;
    }
}
