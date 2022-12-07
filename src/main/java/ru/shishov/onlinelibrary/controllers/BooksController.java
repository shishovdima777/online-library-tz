package ru.shishov.onlinelibrary.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.shishov.onlinelibrary.dao.BooksDAO;
import ru.shishov.onlinelibrary.models.Book;


@Controller
@RequestMapping("/books")
public class BooksController {
    private final BooksDAO booksDAO;
    @Autowired
    public BooksController(BooksDAO booksDAO) {
        this.booksDAO = booksDAO;
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
    public String addBook(@ModelAttribute("book") Book book) {
        System.out.println(book);
        booksDAO.saveBook(book);
        return "redirect:/books";
    }
    @GetMapping("/{id}/edit")
    public String editPage(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", booksDAO.getBook(id));
        return "books/edit";
    }
    @PatchMapping("/{id}")
    public String editBook(@PathVariable("id") int id, @ModelAttribute("book") Book book) {
        booksDAO.editBook(id, book);
        return "redirect:/books";
    }
    @GetMapping("/{id}")
    public String showBook(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", booksDAO.getBook(id));
        return "books/book";
    }
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        booksDAO.delete(id);
        return "redirect:/books";
    }
}
