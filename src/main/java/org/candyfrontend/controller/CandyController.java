package org.candyfrontend.controller;

import lombok.RequiredArgsConstructor;
import org.candyfrontend.form.Candy;
import org.candyfrontend.form.CandyUpdate;
import org.candyfrontend.service.CandyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/candy")
@RequiredArgsConstructor
public class CandyController {

    private final CandyService candyService;

    @GetMapping("")
    public String menu( ){
        return "menu";
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> findById( @PathVariable int id){
        return candyService.getCandy(id);
    }

    @GetMapping("/all")
    public ResponseEntity<?> findAll(){
        return candyService.getCandyList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> addCandy(@RequestBody Candy candy ){
        return candyService.addCandy(candy);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable long id,@RequestBody CandyUpdate candy){
     return  candyService.updateCandy(id,candy);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable long id){
        return  candyService.deleteCandy(id);
    }

}
