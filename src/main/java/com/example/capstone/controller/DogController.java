package com.example.capstone.controller;

import com.example.capstone.model.Dog;
import com.example.capstone.model.Shelter;
import com.example.capstone.service.DogService;
import com.example.capstone.service.ShelterService;
import com.example.capstone.validator.ValidImage;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.io.IOException;
import java.util.List;
/**
 * Controller class for managing dog-related operations.
 * Handles requests for adding, editing, deleting, and displaying dogs,
 * including assigning them to specific shelters.
 * Provides endpoints for both users and administrators to interact with dog data.
 *
 * @author Xiwen Chen
 * @date Dec 31, 2024
 */
@Controller
@RequestMapping("/dogs")
public class DogController {
    @Autowired
    private DogService dogService;
    @Autowired
    private ShelterService shelterService;
    /**
     * Display a list of dogs for regular users.
     * Retrieves all available dogs and passes them to the view.
     *
     * @param model Model to hold dog data for rendering.
     * @return Returns the dog list page (dog_list.html).
     */
    @GetMapping
    public String showDogsForUsers(Model model) {
        List<Dog> dogs = dogService.getAllDogs();
        model.addAttribute("dogs", dogs);
        return "dog_list";
    }
    /**
     * Display a list of dogs for administrators.
     * This view is used for managing dog records.
     *
     * @param model Model to hold dog data for rendering.
     * @return Returns the admin dog management page (dog.html).
     */
    @GetMapping("/list")
    public String listDogsForAdmin(Model model) {
        List<Dog>dogs = dogService.getAllDogs();
        model.addAttribute("dogs", dogs);
        return "dog";
    }
    /**
     * Display the form for adding a new dog.
     * Retrieves all shelters to allow assigning a dog to a specific shelter.
     *
     * @param model Model to pass shelter and dog data to the view.
     * @return Returns the dog form page (dog.html) for adding new dogs.
     */
    @GetMapping("/add")
    public String showAddDogForm(Model model) {

        List<Shelter> shelters = shelterService.getAllShelters();
        System.out.println("Shelters: " + shelters.size());
        shelters.forEach(s -> System.out.println(s.getName()));// 添加日志查看数量
        model.addAttribute("shelters", shelters);  // 加载所有 shelter
        model.addAttribute("dog", new Dog());
        return "dog";
    }
    /**
     * Handle the submission of the add dog form.
     * Validates input, processes the uploaded image, and assigns the dog to a shelter.
     *
     * @param dog Dog entity to be added.
     * @param file MultipartFile representing the uploaded image.
     * @param shelterId Shelter ID to associate with the dog.
     * @param model Model for error handling and form data.
     * @param redirectAttributes Redirect attributes for passing success or error messages.
     * @return Redirects to the dog list page after successful addition.
     * @throws IOException If file upload fails.
     */
    @PostMapping("/add")
    public String addDog(@ModelAttribute("dog") @Valid Dog dog,
                         @RequestParam("file") @ValidImage MultipartFile file,
                         @RequestParam("shelterId") Long shelterId,  // 直接使用 shelterId
                         Model model,
                         RedirectAttributes redirectAttributes) throws IOException {

        // Check if shelterId is valid
        if (shelterId == null) {
            redirectAttributes.addFlashAttribute("error", "Shelter ID is required.");
            return "redirect:/dogs/add";
        }

        // Query shelter
        Shelter shelter = shelterService.getShelterById(shelterId);
        if (shelter == null) {
            redirectAttributes.addFlashAttribute("error", "Shelter not found. Please check the ID.");
            return "redirect:/dogs/add";
        }
        // Set default image if the file is empty
        if (file == null || file.isEmpty()) {
            dog.setImageUrl("/static/uploads/default.png");
        }
        // Bind shelter to dog
        dog.setShelter(shelter);

        // Save dog and upload image
        dogService.saveDogWithImage(dog, file, shelterId);
        redirectAttributes.addFlashAttribute("success", "Dog added successfully!");

        return "redirect:/dogs/list";
    }

    /**
     * Display the edit form for updating dog information.
     * Retrieves the existing dog and shelter list to populate the form.
     *
     * @param id Dog ID to be edited.
     * @param model Model to pass dog and shelter data to the view.
     * @return Returns the dog form page (dog.html) for editing.
     */
    @GetMapping("/edit/{id}")
    public String showEditDogForm(@PathVariable Long id, Model model) {
        Dog dog = dogService.getDogById(id);
        List<Shelter> shelters = shelterService.getAllShelters();
        model.addAttribute("dog", dog);
        model.addAttribute("shelters", shelters);  // 加载所有 shelter
        return "dog";
    }
    /**
     * Handle the submission of the edit dog form.
     * Updates the dog record and processes the uploaded image.
     *
     * @param id Dog ID to be updated.
     * @param dog Updated dog entity.
     * @param file MultipartFile representing the uploaded image.
     * @param shelterId Shelter ID to associate with the dog.
     * @return Redirects to the dog list page after successful update.
     * @throws IOException If file upload fails.
     */
    @PostMapping("/edit/{id}")
    public String updateDog(@PathVariable Long id, @ModelAttribute("dog")@Valid Dog dog,@RequestParam("file")@ValidImage MultipartFile file,@RequestParam("shelterId") Long shelterId)throws IOException {
        dogService.updateDog(id,dog,file,shelterId);
        return "redirect:/dogs/list";
    }
    /**
     * Delete a dog record by ID.
     *
     * @param id Dog ID to be deleted.
     * @return Redirects to the dog list page after successful deletion.
     */
    @GetMapping("/delete/{id}")
    public String deleteDog(@PathVariable Long id) {
        dogService.deleteDog(id);
        return "redirect:/dogs/list";
    }
    /**
     * Search dogs by breed.
     * Filters dogs based on breed and returns matching results.
     *
     * @param breed Dog breed to search for.
     * @param model Model to hold the filtered list of dogs.
     * @return Returns the dog list page with search results.
     */
    @GetMapping("/search")
    public String searchDogsByBreed(@RequestParam("breed")String breed, Model model) {
        List<Dog> dogs = dogService.findDogByBreed(breed);
        model.addAttribute("dogs", dogs);
        return "dog_list";
    }
}
