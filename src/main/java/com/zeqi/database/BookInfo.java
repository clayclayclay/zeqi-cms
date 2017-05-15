package com.zeqi.database;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by Max on 2016/10/11.
 */
@Entity
@Table(name = "book_info", schema = "", catalog = "zeqi")
public class BookInfo {
    private int id;
    private String name;
    private String publisher;
    private String author;
    private int status;
    private BookLoan bookLoan;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name")
    @NotNull
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "publisher")
    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    @Basic
    @Column(name = "author")
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Basic
    @Column(name = "status")
    @NotNull
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @OneToOne
    @JoinColumn(name = "book_load_id")
	public BookLoan getBookLoan() {
		return bookLoan;
	}

	public void setBookLoan(BookLoan bookLoan) {
		this.bookLoan = bookLoan;
	}
    
}
