package org.candyfrontend.controller;

import lombok.RequiredArgsConstructor;
import org.candyfrontend.form.Candy;
import org.candyfrontend.form.CandyDto;
import org.candyfrontend.service.CandyService;
import org.dozer.Mapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
@Controller
@RequestMapping("/candy")
@RequiredArgsConstructor
public class CandyController {

    private final CandyService candyService;
    private final Mapper mapper;
    @GetMapping("/index")
    public String menu( ){
        return "index";
    }

    @GetMapping("/add")
    public String create(Model model){
        model.addAttribute("candyDto",new CandyDto());
        return "create";
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

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public String addCandy(@ModelAttribute @Validated CandyDto candyDto, BindingResult result,Model model){
        System.out.println(candyDto.getName());
         var candy= mapper.map(candyDto,Candy.class);
         ResponseEntity<?> addedCandy =candyService.addCandy(candy);
        System.out.println(addedCandy.getBody());

        return "redirect:/candy/index";
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
