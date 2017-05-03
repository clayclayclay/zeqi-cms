package com.zeqi.database;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by Max on 2016/10/18.
 */
@Entity
@Table(name = "album_picture", schema = "", catalog = "zeqi")
public class AlbumPicture {
    private int id;
    private String picUrl;
    private String photoTime;
    private int albumId;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "pic_url")
    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    @Basic
    @Column(name = "photo_time")
    public String getPhotoTime() {
        return photoTime;
    }

    public void setPhotoTime(String photoTime) {
        this.photoTime = photoTime;
    }

    @Basic
    @Column(name = "album_id")
    public int getAlbumId() {
        return albumId;
    }

    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AlbumPicture that = (AlbumPicture) o;

        if (id != that.id) return false;
        if (photoTime != null ? !photoTime.equals(that.photoTime) : that.photoTime != null) return false;
        if (picUrl != null ? !picUrl.equals(that.picUrl) : that.picUrl != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (picUrl != null ? picUrl.hashCode() : 0);
        result = 31 * result + (photoTime != null ? photoTime.hashCode() : 0);
        return result;
    }
}
