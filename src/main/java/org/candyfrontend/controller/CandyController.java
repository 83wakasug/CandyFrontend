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
import java.util.List;

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
        try{
        ResponseEntity<?> responseEntity = candyService.getCandyList();


        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            ArrayList<Candy> candyList = (ArrayList<Candy>) responseEntity.getBody();
            model.addAttribute("candy", candyList);
        } else {
            handleErrorResponse(model,responseEntity);
            return "error";
        }
        return "update.delete";}catch (Exception e){

            return tryCatch(e);
        }
    }

    @GetMapping("/all")
    public String searchEntry(Model model){
        try {
            ResponseEntity<?> responseEntity =candyService.getCandyList();

            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                ArrayList<Candy> candyList = (ArrayList<Candy>) responseEntity.getBody();
                model.addAttribute("candy", candyList);
            } else {
                handleErrorResponse(model,responseEntity);
                return "error";
            }
            return "search";
        }catch (Exception e){

            return tryCatch(e);

        }

    }

    @GetMapping("/all/{id}")
    public String searchEntryById(Model model, @PathVariable Long id){
       try{ ResponseEntity<?> candyInfo= candyService.getCandy(id);
        if(candyInfo.getStatusCode().is2xxSuccessful())
            model.addAttribute("candy",candyInfo.getBody());
        else {
            handleErrorResponse(model,candyInfo);
            return "error";
        }
        return "candyEntry";
       }catch (Exception e){

           return tryCatch(e);
       }
    }

    @GetMapping("/edit/{id}")
    public String edit(Model model,@PathVariable int id){
        try{
        ResponseEntity<?> candyInfo=candyService.getCandy(id);
        if(candyInfo.getStatusCode().is2xxSuccessful()) {
            model.addAttribute("candy", candyInfo.getBody());
        }
        else {
            handleErrorResponse(model,candyInfo);
            return "error";
        }
        return "edit";}catch (Exception e){
            return tryCatch(e);
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> findById( @PathVariable long id){

        return candyService.getCandy(id);
    }

   /* @GetMapping("/all")
    public String findAll(Model model) {
        try {
            ResponseEntity<List> responseEntity = candyService.getCandyList();
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                ArrayList<Candy> candyList = (ArrayList<Candy>) responseEntity.getBody();
                model.addAttribute("candy", candyList);
            }  else {
                handleErrorResponse(model,responseEntity);
                return "error";
            }

            return "list";
        }catch (Exception e){
           return tryCatch(e);
        }

    }*/

    @PostMapping("/create/add")
    public String createData(@ModelAttribute @Validated CandyDto candyDto,Model model ){
       try {
           var candy= mapper.map(candyDto,Candy.class);
           ResponseEntity<?> addedCandy =candyService.addCandy(candy);
           if (addedCandy.getStatusCode().is2xxSuccessful()){
               return "redirect:/candy/create";
           }
           else {
               handleErrorResponse(model,addedCandy);
               return "error";
           }
       }catch(Exception e){
           return tryCatch(e);
       }


    }

    @PostMapping("edit/data/{id}")
    public String edit(Model model, @ModelAttribute @Validated Candy candy){
       try {

           model.addAttribute("candy", candy);

         ResponseEntity<?> updateCandy = candyService.updateCandy(candy);
            if (updateCandy.getStatusCode().is2xxSuccessful()){
            return "redirect:/candy/edit" ;
            }else {
            handleErrorResponse(model,updateCandy);
            return "error";
            }
       }catch (Exception e){
           return tryCatch(e);
       }

    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable String id,Model model){
       try {
           int id2 = Integer.parseInt(id);
           ResponseEntity<?> deleteCandy=candyService.deleteCandy(id2);
           if(deleteCandy.getStatusCode().is2xxSuccessful())
               return "redirect:/candy/edit";
           else {
               handleErrorResponse(model,deleteCandy);
               return "error";
           }
       }catch (Exception e){
           return tryCatch(e);
       }

    }

    private void handleErrorResponse(Model model, ResponseEntity<?> responseEntity) {

        if (responseEntity.getStatusCode().is5xxServerError()) {
            model.addAttribute("message", "Server Error");
        } else if (responseEntity.getStatusCode().is4xxClientError()) {
            model.addAttribute("message", "Client Error");
        } else {
            model.addAttribute("message", "An unexpected error occurred. Try Again!");
        }
    }

    public String tryCatch(Exception e){
        System.out.println(e.getMessage());

        return "error";
    }

}
