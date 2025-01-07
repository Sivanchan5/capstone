package com.example.capstone.controller;

import com.example.capstone.model.Shelter;
import com.example.capstone.service.ShelterService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
/**
 * ShelterController handles HTTP requests related to shelter management.
 * This controller supports operations for both regular users and administrators.
 *
 * - Regular users can view the list of shelters.
 * - Administrators can add, edit, and delete shelters.
 *
 * The controller manages rendering pages for shelter listings, forms, and search results.
 *
 * @author Xiwen Chen
 * @date Dec 28, 2024
 */
@Controller
@RequestMapping("/shelters")
public class ShelterController {
    @Autowired
    private ShelterService shelterService;
    /**
     * Displays a list of shelters for regular users.
     *
     * @param model The model object to pass shelter data to the view.
     * @return The name of the view to display the list of shelters.
     */
    @GetMapping
    public String listShelters(Model model) {
        List<Shelter> shelters = shelterService.getAllShelters();
        model.addAttribute("shelters", shelters);
        return "shelter";
    }
    /**
     * Displays a list of all shelters for administrative management.
     *
     * @param model The model object to pass shelter data to the view.
     * @return The name of the view for managing shelters.
     */
    @GetMapping("/list")
    public String manageShelters(Model model) {
        List<Shelter> shelters = shelterService.getAllShelters();
        model.addAttribute("shelters", shelters);
        return "shelter_manage";  // 直接返回 shelter 页面，列表和表单共用
    }

    /**
     * Displays the add/edit form for shelters.
     *
     * This method is restricted to administrators.
     *
     * @param id    Optional ID of the shelter to edit. If not provided, a new shelter form is displayed.
     * @param model The model to pass shelter data to the view.
     * @return The view for the add/edit shelter form.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping({"/add", "/edit/{id}"})
    public String showShelterForm(@PathVariable(required = false) Long id, Model model) {
        Shelter shelter = (id != null) ? shelterService.getShelterById(id) : new Shelter();
        model.addAttribute("shelter", shelter);
        return "shelter_form";  // 复用 shelter 页面处理添加和编辑逻辑
    }
    /**
     * Handles the logic for saving or updating shelter information.
     *
     * If an ID is provided, the existing shelter is updated; otherwise, a new shelter is created.
     *
     * @param id      Optional ID of the shelter to update.
     * @param shelter The shelter data to save or update.
     * @return Redirects to the shelter management list after saving.
     */
    @PostMapping({"/add", "/edit/{id}"})
    public String saveOrUpdateShelter(@PathVariable(required = false) Long id,
                                      @Valid @ModelAttribute Shelter shelter) {
        if (id != null) {
            shelterService.updateShelter(id, shelter);
        } else {
            shelterService.createShelters(shelter);
        }
        return "redirect:/shelters/list";
    }

    /**
     * Deletes a shelter by its ID.
     *
     * This method is restricted to administrators.
     *
     * @param id The ID of the shelter to delete.
     * @return Redirects to the shelter list after deletion.
     */
    @GetMapping("/delete/{id}")
    public String deleteShelter(@PathVariable Long id) {
        shelterService.deleteShelter(id);
        return "redirect:/shelters/list";
    }
    /**
     * Searches for a shelter by name and displays the result.
     *
     * If a shelter is found, it is passed to the view. Otherwise, an error message is displayed.
     *
     * @param name  The name of the shelter to search for.
     * @param model The model to pass the shelter data or error message to the view.
     * @return The shelter management view, regardless of whether the shelter is found.
     */
    @GetMapping("/search")
    public String searchShelterByName(@RequestParam("name") String name, Model model) {
        Shelter shelter = shelterService.findByName(name);
        if (shelter != null) {
            model.addAttribute("shelter", shelter);
        } else {
            model.addAttribute("error", "No shelter found with name: " + name);
        }
        return "shelter_manage";
    }

}
