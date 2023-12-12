package com.flow.app.controller;

import com.flow.app.model.Extension;
import com.flow.app.service.ExtensionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ExtensionController {
    @Autowired
    private ExtensionService extensionService;

    @GetMapping("/")
    public String home(Model model) {

        return "index";
    }


    @PostMapping("/addExtension")
    public String addExtension(String extension) {
        extensionService.addExtension(extension);
        return "redirect:/";
    }
}
