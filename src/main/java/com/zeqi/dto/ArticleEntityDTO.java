package com.zeqi.dto;



/**
 * Created by Max on 2016/10/19.
 */
public class ArticleEntityDTO {
    private int articleId;
    private String title;
    private String writeTime;
    private String author;
    private int commentCount;
    private int readCount;
    private String content;


    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWriteTime() {
        return writeTime;
    }

    public void setWriteTime(String writeTime) {
        this.writeTime = writeTime;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public int getReadCount() {
        return readCount;
    }

    public void setReadCount(int readCount) {
        this.readCount = readCount;
    }

	public String getContent() {
	
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

    
    
}
