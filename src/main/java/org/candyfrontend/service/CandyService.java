package org.candyfrontend.service;

import lombok.AllArgsConstructor;
import org.candyfrontend.client.CandyClient;
import org.candyfrontend.form.Candy;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Objects;


@AllArgsConstructor
public class CandyService implements CandyClient {

    private RestClient restClient;
    public CandyService() {
        restClient = RestClient.builder().baseUrl("http://localhost:8080/candy").build();
    }


    @Override
    public ResponseEntity<?> getCandy(long id) {

        return restClient.get()
                .uri("/{id}",id)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(Candy.class);
    }

    @Override
    public ResponseEntity<?> getCandyList() {
        return restClient.get()
                .uri("/all")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(List.class);
    }

    @Override
    public ResponseEntity<?> addCandy(Candy candy) {
        return restClient.post()
                .uri("/",candy)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(candy)
                .retrieve()
                .toEntity(Candy.class);

    }

    @Override
    public  ResponseEntity<?> updateCandy(Candy candy) {
        return restClient.put()
                .uri("/{id}",candy.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Candy.class)
                .retrieve()
                .toEntity(Candy.class);


    }

    @Override
    public ResponseEntity<?> deleteCandy(long id) {
        return restClient.delete()
                .uri("/",id)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(Candy.class);
    }
}
