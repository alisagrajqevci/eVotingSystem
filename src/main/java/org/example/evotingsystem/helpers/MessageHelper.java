package org.example.evotingsystem.helpers;

import org.springframework.stereotype.Component;

import org.springframework.ui.Model;

@Component
public class MessageHelper {

    public static void addSuccessMessage(Model model, String message) {
        model.addAttribute("successMessage", message);
    }

    public static void addErrorMessage(Model model, String message) {
        model.addAttribute("errorMessage", message);
    }
}
