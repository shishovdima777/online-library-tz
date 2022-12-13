package ru.shishov.onlinelibrary.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.shishov.onlinelibrary.dao.BooksDAO;
import ru.shishov.onlinelibrary.dao.PeopleDAO;
import ru.shishov.onlinelibrary.models.Book;
import ru.shishov.onlinelibrary.models.Person;
import ru.shishov.onlinelibrary.util.BooksValidator;

import javax.validation.Valid;


@Controller
@RequestMapping("/books")
public class BooksController {
    private final BooksValidator booksValidator;
    private final BooksDAO booksDAO;
    private final PeopleDAO peopleDAO;
    @Autowired
    public BooksController(BooksValidator booksValidator, BooksDAO booksDAO, PeopleDAO peopleDAO) {
        this.booksValidator = booksValidator;
        this.booksDAO = booksDAO;
        this.peopleDAO = peopleDAO;
    }
    @GetMapping()
    public String getBooks(Model model) {
        model.addAttribute("books", booksDAO.getBooks());
        return "books/books";
    }
    @GetMapping("/new")
    public String newBook(Model model) {
        model.addAttribute("book", new Book());
        return "books/new";
    }
    @PostMapping()
    public String addBook(@ModelAttribute("book") @Valid Book book,
                          BindingResult bindingResult) {
        booksValidator.validate(book, bindingResult);
        if(bindingResult.hasErrors())
            return "books/new";

        booksDAO.saveBook(book);
        return "redirect:/books";
    }
    @GetMapping("/{id}/edit")
    public String editPage(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", booksDAO.getBook(id));
        return "books/edit";
    }
    @PatchMapping("/{id}")
    public String editBook(@PathVariable("id") int id,
                           @ModelAttribute("book") Book book,
                           BindingResult bindingResult){
        booksValidator.validate(book, bindingResult);
        booksDAO.editBook(id, book);
        return "redirect:/books";
    }
    @GetMapping("/{id}")
    public String showBook(@PathVariable("id") int id, Model model,
                           @ModelAttribute("person")Person person) {
        model.addAttribute("book", booksDAO.getBook(id));
        model.addAttribute("people", peopleDAO.getPeople());
        model.addAttribute("personWithBook", peopleDAO.getPerson(booksDAO.getBook(id).getPerson_id()));
        return "books/book";
    }
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        booksDAO.delete(id);
        return "redirect:/books";
    }
    @PatchMapping("/{id}/assign")
    public String assignBook(@PathVariable("id") int id,
                             @ModelAttribute("person") Person person) {
        booksDAO.editBook(id, person.getPerson_id());
        return "redirect:/books/{id}";
    }

    @PatchMapping("/{id}/release")
    public String release(@PathVariable("id") int id) {
        booksDAO.editBook(id);
        return "redirect:/books/{id}";
    }
}
