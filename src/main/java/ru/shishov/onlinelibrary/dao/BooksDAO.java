package ru.shishov.onlinelibrary.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.shishov.onlinelibrary.models.Book;

import java.util.List;
import java.util.Optional;

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
    public List<Book> getBooks(int id) {
        return jdbcTemplate.query("SELECT * FROM Book WHERE person_id=?", new BeanPropertyRowMapper<>(Book.class), id);
    }
    public void saveBook(Book book) {
        jdbcTemplate.update("INSERT INTO Book(book_name, author, year) VALUES(?, ?, ?)", book.getBookName(), book.getAuthor(), book.getYear());
    }
    public Book getBook(int id) {
        return jdbcTemplate.query("SELECT * FROM Book WHERE book_id=?", new BeanPropertyRowMapper<>(Book.class), id).stream().findAny().orElse(null);
    }
    public Optional<Book> getBook(String book_name) {
        return jdbcTemplate.query("SELECT * FROM Book WHERE book_name=?", new BeanPropertyRowMapper<>(Book.class), new Object[] {book_name}).stream().findAny();
    }
    public void editBook(int id, Book book) {
        jdbcTemplate.update("UPDATE Book SET book_name=?, author=?, year=? WHERE book_id=?", book.getBookName(), book.getAuthor(), book.getYear(), id);
    }
    public void editBook(int id, int personId) {
        jdbcTemplate.update("UPDATE Book SET person_id=? WHERE book_id=?", personId, id);
    }
    public void editBook(int id) {
        jdbcTemplate.update("UPDATE Book SET person_id=null WHERE book_id=?", id);
    }
    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM Book WHERE book_id=?", id);
    }
}
