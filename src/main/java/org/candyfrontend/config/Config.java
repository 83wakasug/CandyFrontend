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
        String baseURL="http://candy-inlamning-env.eba-3xsigumx.us-east-1.elasticbeanstalk.com/candy";//Todo Change
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
