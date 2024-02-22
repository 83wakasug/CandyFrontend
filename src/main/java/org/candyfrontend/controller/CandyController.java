package org.candyfrontend.controller;

import lombok.RequiredArgsConstructor;
import org.candyfrontend.form.Candy;
import org.candyfrontend.form.CandyUpdate;
import org.candyfrontend.service.CandyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
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
    public String findAll(Model model) {

        ResponseEntity<ArrayList<Candy>> responseEntity = (ResponseEntity<ArrayList<Candy>>) candyService.getCandyList();

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            ArrayList<Candy> candyList = responseEntity.getBody();
            model.addAttribute("candy", candyList);
        } else {
            // Handle the error case if needed
            model.addAttribute("candy", null);
        }

        return "list";
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> addCandy(@RequestBody Candy candy ){
        return candyService.addCandy(candy);
    }

    @PutMapping("/update")
    public String update(Model model, Candy candy){
        model.addAttribute("candy", candy);
        candyService.updateCandy(candy);
        return "list" ;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable long id){
        candyService.deleteCandy(id);
        return "list" ;
    }

}
