package ru.shishov.onlinelibrary.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.shishov.onlinelibrary.dao.BooksDAO;

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
        System.out.println(booksDAO.getBooks());
        model.addAttribute("books", booksDAO.getBooks());
        return "books/books";
    }
}
