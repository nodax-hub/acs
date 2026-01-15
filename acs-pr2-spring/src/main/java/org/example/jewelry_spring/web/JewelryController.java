package org.example.jewelry_spring.web;

import org.example.jewelry_spring.model.Category;
import org.example.jewelry_spring.model.Jewelry;
import org.example.jewelry_spring.service.CategoryService;
import org.example.jewelry_spring.service.JewelryService;
import org.example.jewelry_spring.web.form.JewelryForm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/jewelry")
public class JewelryController {

    private final JewelryService jewelryService;
    private final CategoryService categoryService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("jewelry", jewelryService.findAll());
        return "jewelry/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("jewelryForm", new JewelryForm());
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("pageTitle", "Добавить украшение");
        model.addAttribute("formAction", "/jewelry");
        return "jewelry/form";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute("jewelryForm") JewelryForm form,
                         BindingResult bindingResult,
                         Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryService.findAll());
            model.addAttribute("pageTitle", "Добавить украшение");
            model.addAttribute("formAction", "/jewelry");
            return "jewelry/form";
        }

        Category category = (form.getCategoryId() == null) ? null : categoryService.getById(form.getCategoryId());
        Jewelry jewelry = Jewelry.builder()
                .title(form.getTitle())
                .material(form.getMaterial())
                .price(form.getPrice())
                .category(category)
                .build();

        jewelryService.create(jewelry);
        return "redirect:/jewelry";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        Jewelry jewelry = jewelryService.getById(id);
        JewelryForm form = new JewelryForm();
        form.setTitle(jewelry.getTitle());
        form.setMaterial(jewelry.getMaterial());
        form.setPrice(jewelry.getPrice());
        form.setCategoryId(jewelry.getCategory() != null ? jewelry.getCategory().getId() : null);

        model.addAttribute("jewelryForm", form);
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("pageTitle", "Редактировать украшение");
        model.addAttribute("formAction", "/jewelry/" + id);
        return "jewelry/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id,
                         @Valid @ModelAttribute("jewelryForm") JewelryForm form,
                         BindingResult bindingResult,
                         Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryService.findAll());
            model.addAttribute("pageTitle", "Редактировать украшение");
            model.addAttribute("formAction", "/jewelry/" + id);
            return "jewelry/form";
        }

        Category category = (form.getCategoryId() == null) ? null : categoryService.getById(form.getCategoryId());
        Jewelry updated = Jewelry.builder()
                .title(form.getTitle())
                .material(form.getMaterial())
                .price(form.getPrice())
                .category(category)
                .build();

        jewelryService.update(id, updated);
        return "redirect:/jewelry";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        jewelryService.delete(id);
        return "redirect:/jewelry";
    }
}