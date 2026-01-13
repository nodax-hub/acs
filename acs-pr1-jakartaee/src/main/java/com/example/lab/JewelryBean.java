package com.example.lab;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class JewelryBean implements Serializable {

    @EJB
    private JewelryService jewelryService;

    @EJB
    private CategoryService categoryService;

    private List<Jewelry> jewelry;
    private List<Category> categories;

    private Jewelry form;

    // id украшения из URL: jewelry-form.xhtml?id=5
    private Long id;

    // выбранная категория в форме
    private Long authorId;

    @PostConstruct
    public void init() {
        jewelry = jewelryService.findAll();
        categories = categoryService.findAll();
        form = new Jewelry();
    }

    // вызывается на jewelry-form.xhtml через f:viewAction
    public void load() {
        // categories нужны для выпадающего списка
        categories = categoryService.findAll();

        if (id != null) {
            Jewelry existing = jewelryService.find(id);
            form = (existing != null) ? existing : new Jewelry();
            authorId = (form.getAuthor() != null) ? form.getAuthor().getId() : null;
        } else {
            form = new Jewelry();
            authorId = null;
        }
    }

    public String createNew() {
        return "jewelry-form.xhtml?faces-redirect=true";
    }

    public String edit(Long id) {
        return "jewelry-form.xhtml?faces-redirect=true&id=" + id;
    }

    public String save() {
        if (authorId != null) {
            Category a = categoryService.find(authorId);
            form.setAuthor(a);
        }

        if (form.getId() == null) {
            jewelryService.create(form);
        } else {
            jewelryService.update(form);
        }

        return "jewelry.xhtml?faces-redirect=true";
    }

    public String delete(Long id) {
        jewelryService.delete(id);
        return "jewelry.xhtml?faces-redirect=true";
    }

    public List<Jewelry> getJewelry() { return jewelry; }
    public List<Category> getCategories() { return categories; }

    public Jewelry getForm() { return form; }
    public void setForm(Jewelry form) { this.form = form; }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getAuthorId() { return authorId; }
    public void setAuthorId(Long authorId) { this.authorId = authorId; }
}
