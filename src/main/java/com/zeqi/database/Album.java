package com.zeqi.database;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;


/**
 * Created by Max on 2016/10/18.
 */
@Entity
@Table(name = "album", schema = "", catalog = "zeqi")
public class Album {
    private int id;
    private String name;
    private String description;
    private String albumTime;

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
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "album_time")
    public String getAlbumTime() {
        return albumTime;
    }

    public void setAlbumTime(String albumTime) {
        this.albumTime = albumTime;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Album album = (Album) o;

        if (id != album.id) return false;
        if (albumTime != null ? !albumTime.equals(album.albumTime) : album.albumTime != null) return false;
        if (description != null ? !description.equals(album.description) : album.description != null) return false;
        if (name != null ? !name.equals(album.name) : album.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (albumTime != null ? albumTime.hashCode() : 0);
        return result;
    }
}
