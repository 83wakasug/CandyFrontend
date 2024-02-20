package org.candyfrontend.controller;

import lombok.RequiredArgsConstructor;
import org.candyfrontend.form.Candy;
import org.candyfrontend.service.CandyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/candy")
@RequiredArgsConstructor
public class CandyController {

    private final CandyService candyService;
    @GetMapping("/{id}")
    public ResponseEntity<?> findById( @PathVariable int id){
        return candyService.getCandy(id);
    }

    @GetMapping("/")
    public ResponseEntity<?> findAll(){
        return candyService.getCandyList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> addCandy(@RequestBody Candy candy ){
        return candyService.addCandy(candy);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable long id,@RequestBody Candy candy){
     return  candyService.updateCandy(id,candy);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable long id){
        return  candyService.deleteCandy(id);
    }

}