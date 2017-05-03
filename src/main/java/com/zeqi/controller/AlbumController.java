package com.zeqi.controller;

import com.zeqi.database.Album;
import com.zeqi.database.AlbumPicture;
import com.zeqi.json.BasicJson;
import com.zeqi.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by Max on 2016/11/23.
 */
@RestController
@RequestMapping("/web")
public class AlbumController {


    @Autowired
    private AlbumService albumService;

    /**
     * 作用：创建相册
     * @param request
     * @return
     */
    @RequestMapping(value = "/album", method= RequestMethod.POST)
    public BasicJson addAlbum(HttpServletRequest request) {
        BasicJson basicJson;
        System.out.println("addAlbum is called");
        basicJson = albumService.addAlbum(request);
        return basicJson;
    }


    /**
     * 作用：删除相册
     * @param albumId
     * @return
     */
    @RequestMapping(value = "/album/{albumId}", method= RequestMethod.DELETE)
    public BasicJson deleteAlbum(@PathVariable String albumId) {
        BasicJson basicJson;
        String[] albumIdList = albumId.split("&");
        basicJson = albumService.deleteAlbum(albumIdList);
        return basicJson;
    }

    /**
     * 作用：获取相册列表
     * @return
     */
    @RequestMapping(value = "/album/{page}", method= RequestMethod.GET)
    public BasicJson getAlbumList(@PathVariable String page) {
        BasicJson basicJson;
        String[] pageInfo = page.split("&");
        basicJson = albumService.getAlbumList(pageInfo);
        return basicJson;
    }


    /**
     * 作用：上传照片
     * @param albumId
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "/album/{albumId}/photo", method= RequestMethod.POST)
    public BasicJson addPhoto(@PathVariable String albumId, HttpServletRequest httpServletRequest) {
        BasicJson basicJson;
        if (httpServletRequest instanceof MultipartHttpServletRequest) {
            MultipartHttpServletRequest request = (MultipartHttpServletRequest) httpServletRequest;
            basicJson = albumService.addPhoto(request, albumId);
            System.out.println("addPhoto json: " + basicJson.getJsonStr());
        }
        else {
            basicJson = new BasicJson(false);
            basicJson.getErrMsg().setCode("05002");
            basicJson.getErrMsg().setMessage("相册内上传照片失败");
        }
        return basicJson;
    }



    /**
     * 作用：删除照片
     * @param photoId
     * @return
     */
    @RequestMapping(value = "/album/photo/{photoId}", method= RequestMethod.DELETE)
    public BasicJson deletePhoto(@PathVariable String photoId) {
        BasicJson basicJson;
        String[] photoIdList = photoId.split("&");
        basicJson = albumService.deleteAlbum(photoIdList);
        return basicJson;
    }


    /**
     * 作用：获取照片列表
     * @param albumId
     * @return
     */
    @RequestMapping(value = "/album/{albumId}/photo/{page}", method= RequestMethod.GET)
    public BasicJson getAlbumPhoto(@PathVariable String albumId, @PathVariable String page) {
        BasicJson basicJson = new BasicJson(false);
        String[] pageInfo = page.split("&");
        basicJson = albumService.getPhotoList(albumId, pageInfo);
        return basicJson;
    }


}
