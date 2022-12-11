package ru.shishov.onlinelibrary.models;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

public class Book {
    @NotEmpty(message = "This field should not be empty")
    private String bookName;
    @NotEmpty(message = "This field should not be empty")
    private String author;
    @Min(value = 1, message = "This field should not be less than 0")
    private int year;
    private int book_id;
    private Integer person_id;

    public Book() {

    }

    public Book(Integer person_id, int book_id, String bookName, String author, int year) {
        this.person_id = person_id;
        this.book_id = book_id;
        this.bookName = bookName;
        this.author = author;
        this.year = year;
    }
    public Integer getPerson_id() {
        return person_id == null ? -1: person_id ;
    }
    public void setPerson_id(Integer person_id) {
        this.person_id = person_id;
    }
    public String getBookName() {
        return bookName;
    }

    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
    @Override
    public String toString() {
        return "Book{" +
                "bookName='" + bookName + '\'' +
                ", author='" + author + '\'' +
                ", year=" + year +
                ", book_id=" + book_id +
                ", person_id=" + person_id +
                '}';
    }
}
