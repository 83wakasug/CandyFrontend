package org.candyfrontend.controller;

import lombok.RequiredArgsConstructor;
import org.candyfrontend.form.Candy;
import org.candyfrontend.form.CandyDto;
import org.candyfrontend.service.CandyService;
import org.dozer.Mapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @GetMapping("/create")
    public String create(Model model){
        model.addAttribute("candy", new Candy());
        return "create";
    }

    @GetMapping("/edit")
    public String edit_delete(Model model){

        ResponseEntity<ArrayList<Candy>> responseEntity = (ResponseEntity<ArrayList<Candy>>) candyService.getCandyList();


        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            ArrayList<Candy> candyList = responseEntity.getBody();

           if(! candyList.isEmpty()) model.addAttribute("candy", candyList);
            else model.addAttribute("candy", null);
        } else if (responseEntity.getStatusCode().is5xxServerError()) {
            return "error";

        } else if (responseEntity.getStatusCode().is4xxClientError()) {
            return "error";

        } else {
            // Handle the error case if needed
            model.addAttribute("errorMessage", "An unexpected error occurred.");
            return "error";
        }
        return "update.delete";
    }

    @GetMapping("/searchEntry")
    public String searchEntry(Model model){

        ResponseEntity<ArrayList<Candy>> responseEntity = (ResponseEntity<ArrayList<Candy>>) candyService.getCandyList();

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            ArrayList<Candy> candyList = responseEntity.getBody();
            model.addAttribute("candy", candyList);
        } else if (responseEntity.getStatusCode().is5xxServerError()) {
            return "error";

        } else if (responseEntity.getStatusCode().is4xxClientError()) {
            return "error";

        } else {
            // Handle the error case if needed
            model.addAttribute("errorMessage", "An unexpected error occurred.");
            return "error";
        }
        return "search";
    }

    @GetMapping("/searchEntry/{id}")
    public String searchEntryById(Model model, @PathVariable Long id){
        ResponseEntity<?> candyInfo= candyService.getCandy(id);
        if(candyInfo.getStatusCode().is2xxSuccessful())
        model.addAttribute("candy",candyInfo.getBody());
        else if (candyInfo.getStatusCode().is5xxServerError()) {
            return "error";

        } else if (candyInfo.getStatusCode().is4xxClientError()) {
            return "error";

        } else {
            // Handle the error case if needed
            model.addAttribute("errorMessage", "An unexpected error occurred.");
            return "error";
        }
        return "candyEntry";
    }

    @GetMapping("/edit/{id}")
    public String edit(Model model,@PathVariable int id){

        ResponseEntity<?> candyInfo=candyService.getCandy(id);
        if(candyInfo.getStatusCode().is2xxSuccessful()) {
            model.addAttribute("candy", candyInfo.getBody());
        }
        else if (candyInfo.getStatusCode().is5xxServerError()) {
            return "error";

        } else if (candyInfo.getStatusCode().is4xxClientError()) {
            return "error";

        } else {
            // Handle the error case if needed
            model.addAttribute("errorMessage", "An unexpected error occurred.");
            return "error";
        }
        return "edit";
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> findById( @PathVariable long id){
        return candyService.getCandy(id);
    }

    @GetMapping("/all")
    public String findAll(Model model) {

        ResponseEntity<ArrayList<Candy>> responseEntity = (ResponseEntity<ArrayList<Candy>>) candyService.getCandyList();
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            ArrayList<Candy> candyList = responseEntity.getBody();
            model.addAttribute("candy", candyList);
        }  else if (responseEntity.getStatusCode().is5xxServerError()) {
            return "error";

        } else if (responseEntity.getStatusCode().is4xxClientError()) {
            return "error";

        } else {
            // Handle the error case if needed
            model.addAttribute("errorMessage", "An unexpected error occurred.");
            return "error";
        }

        return "list";
    }

    @PostMapping("/create/add")
    public String createData(@ModelAttribute @Validated CandyDto candyDto,Model model ){
         var candy= mapper.map(candyDto,Candy.class);
         ResponseEntity<?> addedCandy =candyService.addCandy(candy);
        if (addedCandy.getStatusCode().is2xxSuccessful()){
            return "redirect:/candy/create";
        }
         else if (addedCandy.getStatusCode().is5xxServerError()) {
            return "error";

        } else if (addedCandy.getStatusCode().is4xxClientError()) {
            return "error";

        } else {
            // Handle the error case if needed
            model.addAttribute("errorMessage", "An unexpected error occurred.");
            return "error";
        }

    }

    @PostMapping("edit/data/{id}")
    public String edit(Model model, @ModelAttribute @Validated Candy candy){
        model.addAttribute("candy", candy);

        ResponseEntity<?> updateCandy = candyService.updateCandy(candy);
        if (updateCandy.getStatusCode().is2xxSuccessful()){
        return "redirect:/candy/edit" ;
        }else if (updateCandy.getStatusCode().is5xxServerError()) {
            return "error";

        } else if (updateCandy.getStatusCode().is4xxClientError()) {
            return "error";

        } else {
            // Handle the error case if needed
            model.addAttribute("errorMessage", "An unexpected error occurred.");
            return "error";
        }

    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable String id,Model model){
        int id2 = Integer.parseInt(id);
        ResponseEntity<?> deleteCandy=candyService.deleteCandy(id2);
       if(deleteCandy.getStatusCode().is2xxSuccessful())
        return "redirect:/candy/edit";
         else if (deleteCandy.getStatusCode().is5xxServerError()) {
            return "error";

        } else if (deleteCandy.getStatusCode().is4xxClientError()) {
            return "error";

        } else {
            // Handle the error case if needed
            model.addAttribute("errorMessage", "An unexpected error occurred.");
            return "error";
        }
    }



}
