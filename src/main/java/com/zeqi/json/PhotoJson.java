package com.zeqi.json;

/**
 * Created by Max on 2016/12/6.
 */
public class PhotoJson extends EntityJson{

    private String albumName;
    private String albumDescription;


    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public String getAlbumDescription() {
        return albumDescription;
    }

    public void setAlbumDescription(String albumDescription) {
        this.albumDescription = albumDescription;
    }
}
