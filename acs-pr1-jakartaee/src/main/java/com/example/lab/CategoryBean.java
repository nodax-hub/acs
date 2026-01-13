package com.example.lab;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class CategoryBean implements Serializable {

    @EJB
    private CategoryService categoryService;

    private List<Category> categories;

    private Category form;

    // id приходит из URL: categories-form.xhtml?id=123
    private Long id;

    @PostConstruct
    public void init() {
        categories = categoryService.findAll();
        form = new Category();
    }

    // вызывается на categories-form.xhtml через f:viewAction
    public void load() {
        if (id != null) {
            Category existing = categoryService.find(id);
            form = (existing != null) ? existing : new Category();
        } else {
            form = new Category();
        }
    }

    public String createNew() {
        return "categories-form.xhtml?faces-redirect=true";
    }

    public String edit(Long id) {
        return "categories-form.xhtml?faces-redirect=true&id=" + id;
    }

    public String save() {
        if (form.getId() == null) {
            categoryService.create(form);
        } else {
            categoryService.update(form);
        }
        return "categories.xhtml?faces-redirect=true";
    }

    public String delete(Long id) {
        categoryService.delete(id);
        return "categories.xhtml?faces-redirect=true";
    }

    public List<Category> getCategories() { return categories; }

    public Category getForm() { return form; }
    public void setForm(Category form) { this.form = form; }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
}
