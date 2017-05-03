package com.zeqi.database;

import javax.persistence.*;

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
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BookInfo bookInfo = (BookInfo) o;

        if (id != bookInfo.id) return false;
        if (status != bookInfo.status) return false;
        if (author != null ? !author.equals(bookInfo.author) : bookInfo.author != null) return false;
        if (name != null ? !name.equals(bookInfo.name) : bookInfo.name != null) return false;
        if (publisher != null ? !publisher.equals(bookInfo.publisher) : bookInfo.publisher != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (publisher != null ? publisher.hashCode() : 0);
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + status;
        return result;
    }
}
