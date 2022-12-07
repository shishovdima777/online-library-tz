package ru.shishov.onlinelibrary.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.shishov.onlinelibrary.models.Book;

import java.util.List;

@Component
public class BooksDAO {
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public BooksDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> getBooks() {
        return jdbcTemplate.query("SELECT * FROM Book", new BeanPropertyRowMapper<>(Book.class));
    }
    public void saveBook(Book book) {
        jdbcTemplate.update("INSERT INTO Book(book_name, author, year) VALUES(?, ?, ?)", book.getBookName(), book.getAuthor(), book.getYear());
    }
    public Book getBook(int id) {
        return jdbcTemplate.query("SELECT * FROM Book WHERE book_id=?", new BeanPropertyRowMapper<>(Book.class), id).stream().findAny().orElse(null);
    }
    public void editBook(int id, Book book) {
        jdbcTemplate.update("UPDATE Book SET book_name=?, author=?, year=? WHERE book_id=?", book.getBookName(), book.getAuthor(), book.getYear(), id);
    }
    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM Book WHERE book_id=?", id);
    }
}
