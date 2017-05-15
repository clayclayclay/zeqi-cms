package com.zeqi.database;

import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotNull;



/**
 * Created by Max on 2016/10/18.
 */
@Entity
@Table(name = "album", schema = "", catalog = "zeqi")
public class Album {
    private int id;
    private String name;
    private String description;
    private String createTime;
    private Set<AlbumPicture> albumPictureSet;

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
    @NotNull
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
    @Column(name = "create_time")
    @NotNull
    public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	@OneToMany
	@JoinColumn(name = "album_id")
	public Set<AlbumPicture> getAlbumPictureSet() {
		return albumPictureSet;
	}

	public void setAlbumPictureSet(Set<AlbumPicture> albumPictureSet) {
		this.albumPictureSet = albumPictureSet;
	}
	
	
}
