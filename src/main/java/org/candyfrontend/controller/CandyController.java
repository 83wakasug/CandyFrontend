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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.function.RequestPredicate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.net.URI;
import java.nio.file.Path;
import java.util.ArrayList;

import static org.springframework.web.servlet.function.RequestPredicates.path;

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
            model.addAttribute("candy", candyList);
        } else {
            // Handle the error case if needed
            model.addAttribute("candy", null);
        }
        return "update.delete";
    }

    @GetMapping("/searchEntry")
    public String searchEntry(Model model){

        ResponseEntity<ArrayList<Candy>> responseEntity = (ResponseEntity<ArrayList<Candy>>) candyService.getCandyList();

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            ArrayList<Candy> candyList = responseEntity.getBody();
            model.addAttribute("candy", candyList);
        } else {
            // Handle the error case if needed
            model.addAttribute("candy", null);
        }
        return "search";
    }

    @GetMapping("/searchEntry/{id}")
    public String searchEntryById(Model model, @PathVariable Long id){


        ResponseEntity<?> candyInfo= candyService.getCandy( id);
        model.addAttribute("candy",candyInfo.getBody());

        return "candyEntry";
    }

    @GetMapping("/edit/{id}")
    public String edit(Model model,@PathVariable int id){

        ResponseEntity<?> candyInfo=candyService.getCandy(id);

        model.addAttribute("candy",candyInfo.getBody());
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
        } else {
            // Handle the error case if needed
            model.addAttribute("candy", null);
        }

        return "list";
    }

    @PostMapping("/create/add")
    public String createData(@ModelAttribute @Validated CandyDto candyDto){
         var candy= mapper.map(candyDto,Candy.class);
         ResponseEntity<?> addedCandy =candyService.addCandy(candy);

        // Redirect to /candy/create
        return "redirect:/candy/create";
    }

    @PostMapping("edit/data/{id}")
    public String edit(Model model, @ModelAttribute @Validated Candy candy){
        model.addAttribute("candy", candy);
        System.out.println(candy.getId()+candy.getName());
        candyService.updateCandy(candy);
        System.out.println(candy+"put");
        return "redirect:/candy/edit" ;
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable String id){
        int id2 = Integer.parseInt(id);
        candyService.deleteCandy(id2);
        return "redirect:/candy/edit" ;
    }



}
