package org.strep.web;

import java.util.Optional;

import javax.validation.Valid;

import org.strep.domain.License;
import org.strep.repositories.LicenseRepository;
import org.strep.services.LicenseService;
import org.strep.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * This controller responds to all requests related to licenses
 */
@Controller
@RequestMapping("/license")
public class LicenseController
{

    @Autowired
    private LicenseRepository licenseRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private LicenseService licenseService;

    @GetMapping("/list")
    public String listLicenses(Authentication authentication, Model model, @RequestParam(name="searchInput", required=false)String searchInput)
    {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String username = userDetails.getUsername();
        String authority = userService.getPermissionsByUsername(username);

        model.addAttribute("authority", authority);
        model.addAttribute("username", username);

        Iterable<License> licenses;

        if(searchInput==null)
        {
            licenses = licenseRepository.findAll();
        }
        else
        {
            licenses = licenseRepository.findByName(searchInput);
        }

        model.addAttribute("licenses", licenses);

        return "list_licenses";
    }

    @GetMapping("/detailed")
    public String showDetailedLicense(Authentication authentication, Model model, @RequestParam("name")String name)
    {

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String username = userDetails.getUsername();
        String authority = userService.getPermissionsByUsername(username);

        model.addAttribute("authority", authority);
        model.addAttribute("username", username);

        Optional<License> licenseOpt = licenseRepository.findById(name);

        if(licenseOpt.isPresent())
        {
            License license = licenseOpt.get();
            String description = licenseService.formatLicenseDescription(license);
            model.addAttribute("license", license);
            model.addAttribute("description", description);
        }

        return "detailed_license";
    }

    @GetMapping("/add")
    public String addLicense(Authentication authentication, Model model, License license)
    {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String username = userDetails.getUsername();
        String authority = userService.getPermissionsByUsername(username);

        model.addAttribute("authority", authority);
        model.addAttribute("username", username);

        return "add_license";
    }

    @PostMapping("/add")
    public String addLicense(Authentication authentication, Model model, @Valid License license,BindingResult bindingResult, RedirectAttributes redirectAttributes)
    {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String username = userDetails.getUsername();
        String authority = userService.getPermissionsByUsername(username);

        if(bindingResult.hasErrors())
        {
            model.addAttribute("authority", authority);
            model.addAttribute("username", username);
            return "add_license";
        }
        else
        {
            Optional<License> licenseOpt = licenseRepository.findById(license.getName());
            if(licenseOpt.isPresent())
            {
                redirectAttributes.addFlashAttribute("message", "License already exists");
            }
            else
            {
                licenseRepository.save(license);
                redirectAttributes.addFlashAttribute("message", "License successfully uploaded");
            }
            return "redirect:/license/list";
        }

    }

    @GetMapping("/modify")
    public String modifyLicense(Authentication authentication, Model model, License license, @RequestParam("name")String name)
    {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String username = userDetails.getUsername();
        String authority = userService.getPermissionsByUsername(username);

        model.addAttribute("authority", authority);
        model.addAttribute("username", username);

        Optional<License> licenseOpt = licenseRepository.findById(name);
        if(licenseOpt.isPresent())
        {
            License licenseToModify = licenseOpt.get();
            String description = new String(licenseToModify.getDescription());
            model.addAttribute("licenseToModify", licenseToModify);
            model.addAttribute("description", description);
        }

        return "modify_license";
    }


    @PostMapping("/modify")
    public String modifyLicense(Authentication authentication, Model model, @Valid License license,BindingResult bindingResult, RedirectAttributes redirectAttributes)
    {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String username = userDetails.getUsername();
        String authority = userService.getPermissionsByUsername(username);

        if(bindingResult.hasErrors())
        {
            model.addAttribute("authority", authority);
            model.addAttribute("username", username);
            return "modify_license";
        }
        else
        {
            licenseRepository.save(license);
            redirectAttributes.addFlashAttribute("message", "License successfully modified");
            
            return "redirect:/license/list";
        }

    }

    @GetMapping("/delete")
    public String deleteLicense(Authentication authentication, Model model, @RequestParam("name")String name, RedirectAttributes redirectAttributes)
    {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String username = userDetails.getUsername();
        String authority = userService.getPermissionsByUsername(username);
        String message = "";

        model.addAttribute("authority", authority);
        model.addAttribute("username", username);

        Optional<License> licenseOpt = licenseRepository.findById(name);

        if(licenseOpt.isPresent())
        {
            licenseRepository.delete(licenseOpt.get());
            message="License successfully deleted";
            redirectAttributes.addFlashAttribute("message", message);
        }
        else
        {
            message="Cannot delete license";
        }

        return "redirect:/license/list";
    }

}