package org.candyfrontend.config;

import org.candyfrontend.client.CandyClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class Config {

    @Bean
    public CandyClient productClient(){
        String baseURL="localhost:8080/Candy";//Todo Change
        RestClient restClient = RestClient.create(baseURL);

        HttpServiceProxyFactory factory = HttpServiceProxyFactory
                .builderFor(RestClientAdapter.create(restClient)).build();

        return factory.createClient(CandyClient.class);
    }

}
