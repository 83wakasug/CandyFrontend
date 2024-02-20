package org.candyfrontend.service;

import lombok.AllArgsConstructor;
import org.candyfrontend.client.CandyClient;
import org.candyfrontend.form.Candy;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;


@AllArgsConstructor
public class CandyService implements CandyClient {

    private RestClient restClient;
    public CandyService() {
        restClient = RestClient.builder().baseUrl("http://localhost:8080/candy").build();
    }


    @Override
    public ResponseEntity<?> getCandy(long id) {

        restClient.get()
        return null;
    }

    @Override
    public ResponseEntity<?> getCandyList() {
        return null;
    }

    @Override
    public ResponseEntity<?> addCandy(Candy candy) {
        return restClient.post()
                .contentType(MediaType.APPLICATION_JSON)
                .body(candy)
                .retrieve()
                .body(?.class);

    }

    @Override
    public Candy updateCandy(Candy candy) {
        return restClient.put()
                .uri("/{id}",candy.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(Candy.class);


    }

    @Override
    public ResponseEntity<?> deleteCandy(long id) {
        return null;
    }
}
