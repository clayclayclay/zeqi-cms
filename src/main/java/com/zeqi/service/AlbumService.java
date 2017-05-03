package com.zeqi.service;

import com.zeqi.json.BasicJson;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Max on 2016/10/18.
 */
public interface AlbumService {


    BasicJson addAlbum(HttpServletRequest request);

    BasicJson deleteAlbum(String[] albumId);

    BasicJson getAlbumList(String[] pageInfo);

    BasicJson addPhoto(MultipartHttpServletRequest request, String albumId);

    BasicJson deletePhoto(String[] photoId);

    BasicJson getPhotoList(String albumId, String[] pageInfo);

    int getAlbumNum();

    int getPhotoNum(String albumId);
}
