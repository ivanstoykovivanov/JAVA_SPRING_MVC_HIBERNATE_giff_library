package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import model.Category;
import service.CategoryService;
import web.FlashMessage;

import javax.validation.Valid;
import java.util.List;
import web.Color;

@Controller
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    // Index of all categories
    @SuppressWarnings("unchecked")
    @RequestMapping("/categories")
    public String listCategories(Model model) {
        // TODO: Get all categories
        List<Category> categories = categoryService.findAll();

        model.addAttribute("categories",categories);
        return "category/index";
    }

    // Single category page
    @RequestMapping("/categories/{categoryId}")
    public String category(@PathVariable Long categoryId, Model model) {
        // TODO: Get the category given by categoryId
        Category category = null;

        model.addAttribute("category", category);
        return "category/details";
    }

    // Form for adding a new category
    @RequestMapping("categories/add")
    public String formNewCategory(Model model) {
    	
    	if(!model.containsAttribute("category")){
    		model.addAttribute("category" , new Category() ) ; 
    	}
        model.addAttribute("colors", Color.values()) ; 	
        
        return "category/form";
    }

    // Form for editing an existing category
    @RequestMapping("categories/{categoryId}/edit")
    public String formEditCategory( Model model) {
                 
    	model.addAttribute("category" , new Category()) ; 
    	model.addAttribute("colors" , Color.values()) ; 
    	
    	return "category/form";
    }

    // Update an existing category
    @RequestMapping(value = "/categories/{categoryId}", method = RequestMethod.POST)
    public String updateCategory(@Valid Category category, BindingResult result, RedirectAttributes redirectAttributes) {
        // TODO: Update category if valid data was received
        if(result.hasErrors()) {
            // Include validation errors upon redirect
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.category",result);

            // Add  category if invalid was received
            redirectAttributes.addFlashAttribute("category",category);

            // Redirect back to the form
            return String.format("redirect:/categories/%s/edit",category.getId());
        }

        categoryService.save(category);

        redirectAttributes.addFlashAttribute("flash",new FlashMessage("Category successfully updated!", FlashMessage.Status.SUCCESS));

        // TODO: Redirect browser to /categories
        return "redirect:/categories";
    }
    
    @RequestMapping(value = "/categories", method = RequestMethod.POST)
    public String addCategory(@Valid Category category, BindingResult result, RedirectAttributes redirectAttributes ) {
    	
    	if(result.hasErrors()){
    		// Include validation errors at redirect
        	redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.category" , result) ; 
    		//Add category if invalid data was received 
    		redirectAttributes.addFlashAttribute("category" , category) ; 
    		return "redirect:/categories/add" ; 
    	}
    	categoryService.save(category ) ; 
    	redirectAttributes.addFlashAttribute("flash", new FlashMessage("Category successfully added" , FlashMessage.Status.SUCCESS)); 
    	return "redirect:/categories";   //GET request !!
    }

    // Delete an existing category
    @RequestMapping(value = "/categories/{categoryId}/delete", method = RequestMethod.POST)
    public String deleteCategory(@PathVariable Long categoryId, RedirectAttributes redirectAttributes) {
        Category cat = categoryService.findById(categoryId);

        // TODO: Delete category if it contains no GIFs
        if(cat.getGifs().size() > 0) {
            redirectAttributes.addFlashAttribute("flash",new FlashMessage("Only empty categories can be deleted.", FlashMessage.Status.FAILURE));
            return String.format("redirect:/categories/%s/edit",categoryId);
        }
        categoryService.delete(cat);
        redirectAttributes.addFlashAttribute("flash",new FlashMessage("Category deleted!", FlashMessage.Status.SUCCESS));

        // TODO: Redirect browser to /categories
        return "redirect:/categories";
    }
}
