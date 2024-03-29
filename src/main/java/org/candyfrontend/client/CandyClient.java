package org.candyfrontend.client;

import org.candyfrontend.form.Candy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.DeleteExchange;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.PostExchange;
import org.springframework.web.service.annotation.PutExchange;

public interface CandyClient {

    @GetExchange("/{id}")
    ResponseEntity<?> getCandy(@PathVariable long id);

    @GetExchange("/all")
    ResponseEntity<?>getCandyList();


    @PostExchange("/")
    ResponseEntity<?> addCandy(@RequestBody Candy candy);

    @PutExchange("/{id}")
    ResponseEntity<?> updateCandy(@RequestBody Candy update);



    @DeleteExchange("/{id}")
    ResponseEntity<?> deleteCandy(@PathVariable long id);


}
