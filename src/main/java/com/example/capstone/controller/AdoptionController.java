package com.example.capstone.controller;

import com.example.capstone.model.Adoption;
import com.example.capstone.model.Dog;
import com.example.capstone.model.User;
import com.example.capstone.repository.UserRepository;
import com.example.capstone.service.AdoptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Controller class for managing adoption-related operations.
 * This class handles adoption requests, listing, approvals, and cancellations.
 * Both user and admin roles can interact with adoption features, but some operations
 * are restricted to admin users only.
 *
 * @author Xiwen Chen
 * @date Dec 30, 2024
 */
@Controller
@RequestMapping("/adoptions")
public class AdoptionController {

    private final AdoptionService adoptionService;
    private final UserRepository userRepository;

    @Autowired
    public AdoptionController(AdoptionService adoptionService, UserRepository userRepository) {
        this.adoptionService = adoptionService;
        this.userRepository = userRepository;
    }
    /**
     * View adoption details by ID.
     * Retrieves adoption information by its ID and displays it in the view.
     *
     * @param id    The ID of the adoption record.
     * @param model Model to pass data to the view.
     * @return Returns the adoption detail page.
     */
    @GetMapping("/{id}")
    public String viewAdoption(@PathVariable Long id, Model model) {
        // Find adoption record by ID
        Adoption adoption = adoptionService.getAdoptionById(id);

        // If no adoption record is found, throw an exception or return an error page
        if (adoption == null) {
            throw new RuntimeException("Adoption record not found");
        }

        // Add adoption record to model to pass to the Thymeleaf page
        model.addAttribute("adoption", adoption);

        // Return the adoption detail page
        return "adoption_detail";
    }


    /**
     * List logged-in user's adoption records.
     * Displays all adoption records associated with the currently logged-in user.
     *
     * @param model Model to pass data to the view.
     * @param auth  Authentication object to get the logged-in user's details.
     * @return Returns the user adoption records page.
     */
    @GetMapping("/list")
    public String listUserAdoptions(Model model, Authentication auth) {
        // 1. Get the currently logged-in user
        UserDetails springUser = (UserDetails) auth.getPrincipal();
        // 2. Retrieve the full User entity
        com.example.capstone.model.User user = userRepository.findByUserName(springUser.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
        // 3. Get user's adoption records
        List<Adoption> adoptions = adoptionService.findByUserId(user.getId());
        model.addAttribute("adoptions", adoptions);
        return "adoption";  // Return the user adoption records page
    }

    /**
     * Submit a new adoption request.
     * Allows the user to apply for adopting a specific dog.
     *
     * @param dogId The ID of the dog to adopt.
     * @param auth  Authentication object to get the logged-in user's details.
     * @return Redirects to the user's adoption list if successful, otherwise redirects to the dog list with an error.
     */
    @PostMapping("/apply")
    public String applyForAdoption(@RequestParam Long dogId, Authentication auth) {
        try{
        UserDetails springUser = (UserDetails) auth.getPrincipal();
        com.example.capstone.model.User user = userRepository.findByUserName(springUser.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Adoption adoption = new Adoption();
        adoption.setDog(new Dog(dogId));
        adoption.setUser(user);  // Associate full user
        adoptionService.requestAdoption(adoption);

        return "redirect:/adoptions/list?success=true";
    }catch (Exception e){
            return "redirect:/dogs?error=true";
        }
    }

    /**
     * Cancel an adoption request.
     * Allows users to cancel their own pending adoption requests.
     *
     * @param id   The ID of the adoption record to cancel.
     * @param auth Authentication object to verify user permissions.
     * @return Redirects to the user's adoption list after cancellation.
     */
    @PostMapping("/cancel/{id}")
    public String cancelAdoption(@PathVariable Long id, Authentication auth) {
        UserDetails springUser = (UserDetails) auth.getPrincipal();
        User user = userRepository.findByUserName(springUser.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        adoptionService.cancelAdoption(id, user.getId());
        return "redirect:/adoptions/list";
    }
    /**
     * List all adoption requests (admin only).
     * Displays all adoption requests for administrators to review.
     *
     * @param model Model to pass data to the view.
     * @param auth  Authentication object for role verification.
     * @return Returns the admin adoption management page.
     */
    @GetMapping("/admin/list")
    public String listAllAdoptions(Model model, Authentication auth) {
        System.out.println(auth.getAuthorities());  // 打印用户角色
        List<Adoption> adoptions = adoptionService.getAllAdoptions();
        model.addAttribute("adoptions", adoptions);
        return "adoption_manage";
    }

    /**
     * Approve an adoption request (admin only).
     * Admin can approve a pending adoption request.
     *
     * @param id The ID of the adoption record to approve.
     * @return Redirects to the admin adoption management page.
     */
    @PostMapping("/admin/approve/{id}")
    public String approveAdoption(@PathVariable Long id) {
        adoptionService.updateAdoptionStatus(id, "APPROVED");
        return "redirect:/adoptions/admin/list";
    }

    /**
     * Reject an adoption request (admin only).
     * Admin can reject a pending adoption request.
     *
     * @param id The ID of the adoption record to reject.
     * @return Redirects to the admin adoption management page.
     */
    @PostMapping("/admin/reject/{id}")
    public String rejectAdoption(@PathVariable Long id) {
        adoptionService.updateAdoptionStatus(id, "REJECTED");
        return "redirect:/adoptions/admin/list";
    }

}
