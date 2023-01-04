package ru.shishov.onlinelibrary.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.shishov.onlinelibrary.dao.BookDAO;
import ru.shishov.onlinelibrary.models.Book;

@Component
public class BooksValidator implements Validator {
    private final BookDAO bookDAO;

    public BooksValidator(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Book.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Book book = (Book) target;
        if(bookDAO.getBook(book.getBookName()).isPresent()) {
            errors.rejectValue("bookName", "", "This book is already exists.");
        }
    }
}
