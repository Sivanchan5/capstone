package com.example.capstone.controller;

import com.example.capstone.model.Dog;
import com.example.capstone.model.Shelter;
import com.example.capstone.service.DogService;
import com.example.capstone.service.ShelterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;
/**
 * HomeController is responsible for handling requests to the root URL ("/").
 * It fetches data about dogs and shelters from the services and prepares the model for the home page view.
 * This controller integrates dog and shelter data to display on the main page of the application.
 *
 * The home page shows available dogs for adoption and lists participating shelters.
 *
 * @author Xiwen Chen
 * @date Jan 1, 2025
 */
@Controller
@RequestMapping("/")
public class HomeController {
    private final DogService dogService;
    private final ShelterService shelterService;

    @Autowired
    public HomeController(DogService dogService, ShelterService shelterService) {
        this.dogService = dogService;
        this.shelterService = shelterService;
    }

    @GetMapping
    public String home(Model model) {
        List<Dog> dogs = dogService.getAllDogs();
        List<Shelter> shelters = shelterService.getAllShelters();
        model.addAttribute("dogs", dogs);
        model.addAttribute("shelters", shelters);
        return "index";  // Renders the index.html (home page)
    }
    @GetMapping("/403")
    public String accessDenied() {
        return "403";
    }

}
