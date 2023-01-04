package ru.shishov.onlinelibrary.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.shishov.onlinelibrary.dao.BookDAO;
import ru.shishov.onlinelibrary.dao.PersonDAO;
import ru.shishov.onlinelibrary.models.Book;
import ru.shishov.onlinelibrary.models.Person;
import ru.shishov.onlinelibrary.util.BooksValidator;

import javax.validation.Valid;
import java.util.Optional;


@Controller
@RequestMapping("/books")
public class BooksController {
    private final BooksValidator booksValidator;
    private final BookDAO bookDAO;
    private final PersonDAO personDAO;
    @Autowired
    public BooksController(BooksValidator booksValidator, BookDAO bookDAO, PersonDAO personDAO) {
        this.booksValidator = booksValidator;
        this.bookDAO = bookDAO;
        this.personDAO = personDAO;
    }
    @GetMapping()
    public String getBooks(Model model) {
        model.addAttribute("books", bookDAO.getBooks());
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

        bookDAO.saveBook(book);
        return "redirect:/books";
    }
    @GetMapping("/{id}/edit")
    public String editPage(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", bookDAO.getBook(id));
        return "books/edit";
    }
    @PatchMapping("/{id}")
    public String editBook(@PathVariable("id") int id,
                           @ModelAttribute("book") Book book,
                           BindingResult bindingResult){
        booksValidator.validate(book, bindingResult);
        bookDAO.editBook(id, book);
        return "redirect:/books";
    }
    @GetMapping("/{id}")
    public String showBook(@PathVariable("id") int id, Model model,
                           @ModelAttribute("person")Person person) {
        model.addAttribute("book", bookDAO.getBook(id));

        Optional<Person> bookOwner = bookDAO.getBookOwner(id);

        if(bookOwner.isPresent()) {
            model.addAttribute("owner", bookOwner.get());
        } else {
            model.addAttribute("people", personDAO.getPeople());
        }
        return "books/book";
    }
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        bookDAO.delete(id);
        return "redirect:/books";
    }
    @PatchMapping("/{id}/assign")
    public String assignBook(@PathVariable("id") int id,
                             @ModelAttribute("person") Person person) {
        bookDAO.editBook(id, person.getPerson_id());
        return "redirect:/books/{id}";
    }
    @PatchMapping("/{id}/release")
    public String release(@PathVariable("id") int id) {
        bookDAO.editBook(id);
        return "redirect:/books/{id}";
    }
}
