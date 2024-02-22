package org.candyfrontend.config;

import lombok.RequiredArgsConstructor;
import org.candyfrontend.client.CandyClient;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
@RequiredArgsConstructor
public class Config {

    @Bean
    public CandyClient productClient(){
        String baseURL="localhost:8080/Candy";//Todo Change
        RestClient restClient = RestClient.create(baseURL);

        HttpServiceProxyFactory factory = HttpServiceProxyFactory
                .builderFor(RestClientAdapter.create(restClient)).build();

        return factory.createClient(CandyClient.class);
    }


    @Bean
    Mapper mappar(){
        return new DozerBeanMapper();
    }

}
