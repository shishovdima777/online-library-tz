package ru.shishov.onlinelibrary.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.shishov.onlinelibrary.dao.BooksDAO;
import ru.shishov.onlinelibrary.dao.PeopleDAO;
import ru.shishov.onlinelibrary.models.Person;

import javax.validation.Valid;


@Controller
@RequestMapping("/people")
public class PeopleController {
    private final PeopleDAO peopleDAO;
    private final BooksDAO booksDAO;
    @Autowired
    public PeopleController(PeopleDAO peopleDAO, BooksDAO booksDAO) {
        this.peopleDAO = peopleDAO;
        this.booksDAO = booksDAO;
    }

    @GetMapping()
    public String getPeople(Model model) {
        model.addAttribute("people", peopleDAO.getPeople());
        return "people/people";
    }
    @GetMapping("/new")
    public String addPerson(Model model) {
        model.addAttribute("person", new Person());
        return "people/new";
    }
    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult) {
        if(bindingResult.hasErrors())
            return "people/new";

        peopleDAO.save(person);
        return "redirect:/people";
    }
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", peopleDAO.getPerson(id));
        return "people/edit";
    }
    @PatchMapping("/{id}")
    public String update(@PathVariable("id") int id ,@ModelAttribute() @Valid Person person,
                         BindingResult bindingResult) {
        if(bindingResult.hasErrors())
            return "people/edit";
        peopleDAO.update(person, id);
        return "redirect:/people";
    }
    @GetMapping("/{id}")
    public String showPerson(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", peopleDAO.getPerson(id));
        model.addAttribute("books", booksDAO.getBooks(id));
        System.out.println(booksDAO.getBooks(id));
        return "people/person";
    }
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        peopleDAO.delete(id);
        return "redirect:/people";
    }

}
