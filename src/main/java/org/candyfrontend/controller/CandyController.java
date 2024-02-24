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

    @GetMapping("/edit")
    public String edit_delete(Model model){

        ResponseEntity<ArrayList<Candy>> responseEntity = (ResponseEntity<ArrayList<Candy>>) candyService.getCandyList();

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            ArrayList<Candy> candyList = responseEntity.getBody();
            model.addAttribute("candy", candyList);
        } else {
            // Handle the error case if needed
            model.addAttribute("candy", null);
        }
        return "update.delete";
    }

    @GetMapping("/edit/{id}")
    public String edit(Model model,@PathVariable int id){

        ResponseEntity<?> candyInfo=candyService.getCandy(id);

        model.addAttribute("candy",candyInfo.getBody());
        System.out.println(candyInfo.getBody());
        return "edit";
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
         var candy= mapper.map(candyDto,Candy.class);
         ResponseEntity<?> addedCandy =candyService.addCandy(candy);
        System.out.println(addedCandy.getBody());

        return "redirect:/candy/index";
    }

    @GetMapping("edit/data/{id}")
    public String edit(Model model, @ModelAttribute @Validated Candy candy,@PathVariable String id){
        model.addAttribute("candy", candy);
        System.out.println(candy.getId()+candy.getName());
        candyService.updateCandy(candy);
        System.out.println(candy+"put");
        return "redirect:/candy/index" ;
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable String id){
        int id2 = Integer.parseInt(id);
        System.out.println(id2);
        candyService.deleteCandy(id2);
        return "redirect:/candy/index" ;
    }



}
