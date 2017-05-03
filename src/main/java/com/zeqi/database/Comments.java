package com.zeqi.database;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by Max on 2016/10/11.
 */
@Entity
@Table(name = "comments", schema = "", catalog = "zeqi")
public class Comments {
    private int id;
    private Timestamp commentDate;
    private String commentContent;
    private int commentParent;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "comment_date")
    public Timestamp getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(Timestamp commentDate) {
        this.commentDate = commentDate;
    }

    @Basic
    @Column(name = "comment_content")
    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    @Basic
    @Column(name = "comment_parent")
    public int getCommentParent() {
        return commentParent;
    }

    public void setCommentParent(int commentParent) {
        this.commentParent = commentParent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Comments comments = (Comments) o;

        if (commentParent != comments.commentParent) return false;
        if (id != comments.id) return false;
        if (commentContent != null ? !commentContent.equals(comments.commentContent) : comments.commentContent != null)
            return false;
        if (commentDate != null ? !commentDate.equals(comments.commentDate) : comments.commentDate != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (commentDate != null ? commentDate.hashCode() : 0);
        result = 31 * result + (commentContent != null ? commentContent.hashCode() : 0);
        result = 31 * result + commentParent;
        return result;
    }
}
