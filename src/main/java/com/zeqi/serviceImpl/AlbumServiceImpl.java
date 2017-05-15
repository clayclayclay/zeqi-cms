package com.zeqi.serviceImpl;

import com.zeqi.dao.BasicDao;
import com.zeqi.database.Album;
import com.zeqi.database.AlbumPicture;
import com.zeqi.dataconfig.AlbumConfig;
import com.zeqi.dataconfig.ConstantPath;
import com.zeqi.json.BasicJson;
import com.zeqi.json.EntityJson;
import com.zeqi.json.PhotoJson;
import com.zeqi.service.AlbumService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Max on 2016/10/18.
 */
@Service
public class AlbumServiceImpl implements AlbumService {


    @Autowired
    private BasicDao basicDao;
    @Autowired
    private AlbumConfig albumConfig;

    /**
     * 创建相册
     *
     * @param request
     * @return
     */
    @Override
    public BasicJson addAlbum(HttpServletRequest request) {
        BasicJson basicJson = new BasicJson(false);
        Album album = new Album();
        album.setName(request.getParameter("albumName"));
        album.setDescription(request.getParameter("description"));
        Timestamp timestamp = new Timestamp(new Date().getTime());
        album.setCreateTime(timestamp.toString());
        boolean isCreated;
        isCreated = basicDao.save(album);
        if (isCreated) {
            basicJson.setStatus(true);
            basicJson.getErrMsg().setCode("200");
            basicJson.getErrMsg().setMessage("创建成功");
            basicJson.setJsonStr(album.getId());
        } else {
            basicJson.getErrMsg().setCode("05001");
            basicJson.getErrMsg().setMessage("创建失败");
        }
        return basicJson;
    }

    /**
     * 删除相册
     *
     * @param albumId
     * @return
     */
    @Override
    public BasicJson deleteAlbum(String[] albumId) {
        BasicJson basicJson = new BasicJson(false);
        boolean isDelete = basicDao.delete(albumId, Album.class);
        boolean isPhotoDelete = basicDao.deleteById("AlbumPicture", "albumId", albumId);
        System.out.println("删除照片状态" + isPhotoDelete);
        if (isDelete && isPhotoDelete) {
            basicJson.setStatus(true);
            basicJson.getErrMsg().setCode("200");
            basicJson.getErrMsg().setMessage("删除成功");
        } else {
            basicJson.getErrMsg().setCode("05002");
            basicJson.getErrMsg().setMessage("删除失败");
        }
        return basicJson;
    }


    /**
     * 获取相册列表
     *
     * @return
     */
    @Override
    @SuppressWarnings("unchecked")
    public BasicJson getAlbumList(String page) {
        BasicJson basicJson = new BasicJson(false);
        List<Album> albumList = (List<Album>) basicDao.getAllByPage("Album", page, albumConfig.getPerPageAlbumNum(), true, "albumTime");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (Album album : albumList) {
            try {
                album.setCreateTime(sdf.format(sdf.parse(album.getCreateTime())));
            } catch (ParseException e) {
                basicJson.setStatus(false);
                basicJson.getErrMsg().setCode("05003");
                basicJson.setJsonStr("获取失败");
                return basicJson;
            }
        }
        basicJson.setStatus(true);
        basicJson.getErrMsg().setCode("200");
        basicJson.getErrMsg().setMessage("获取成功");
        EntityJson<Album> documentInfoEntityJson = new EntityJson<Album>();
        documentInfoEntityJson.setEntityList(albumList);
        Double totalPageDouble = Double.valueOf(String.valueOf(getAlbumNum()));
        Double requestPageNumDouble = Double.valueOf(page);
        int pageNum = ((Double) Math.ceil(totalPageDouble / requestPageNumDouble)).intValue();
        documentInfoEntityJson.setPage(pageNum);
        basicJson.setJsonStr(documentInfoEntityJson);
        return basicJson;
    }

    /**
     * 上传图片
     *
     * @param request
     * @return
     */
    @Override
    public BasicJson addPhoto(MultipartHttpServletRequest request, String albumId) {
        BasicJson basicJson = new BasicJson(false);
        List<String> photoUrlList = new ArrayList<String>();
        Map<String, MultipartFile> fileMap = request.getFileMap();
        for (String key : fileMap.keySet()) {
            //本地存储
            MultipartFile multipartFile = fileMap.get(key);
            String fileName = multipartFile.getOriginalFilename();
            String photoPath = ConstantPath.ALBUM_PHOTO_PHYSICAL_PATH + fileName;
            try {
                FileUtils.copyInputStreamToFile(multipartFile.getInputStream(), new File(photoPath));
                AlbumPicture albumPicture = new AlbumPicture();
//                albumPicture.setPicUrl(ConstantPath.ALBUM_PHOTO_URL + fileName);
                Timestamp timestamp = new Timestamp(new Date().getTime());
//                albumPicture.setPhotoTime(timestamp.toString());
//                albumPicture.setAlbumId(Integer.valueOf(albumId));
                basicDao.save(albumPicture);
                photoUrlList.add(ConstantPath.ALBUM_PHOTO_URL + fileName);
                basicJson.setStatus(true);
                basicJson.getErrMsg().setCode("200");
                basicJson.getErrMsg().setMessage("上传成功");
                basicJson.setJsonStr(photoUrlList);
            } catch (IOException e) {
                e.printStackTrace();
                basicJson.getErrMsg().setCode("05004");
                basicJson.getErrMsg().setMessage("上传失败");
                basicJson.setJsonStr(photoUrlList);
            }
        }
        return basicJson;
    }


    /**
     * 删除图片
     *
     * @param photoId
     * @return
     */
    @Override
    public BasicJson deletePhoto(String[] photoId) {
        BasicJson basicJson = new BasicJson(false);
        boolean isDelete = basicDao.delete(photoId, AlbumPicture.class);
        if (isDelete) {
            basicJson.setStatus(true);
            basicJson.getErrMsg().setCode("200");
            basicJson.getErrMsg().setMessage("删除成功");
        } else {
            basicJson.getErrMsg().setCode("05005");
            basicJson.getErrMsg().setMessage("删除失败");
        }
        return basicJson;
    }

    /**
     * 获取图片列表
     *
     * @param albumId
     * @return
     */
    @Override
    @SuppressWarnings("unchecked")
    public BasicJson getPhotoList(String albumId, String page) {
        BasicJson basicJson = new BasicJson(false);
        List<AlbumPicture> albumPictureList;
        albumPictureList = (List<AlbumPicture>) basicDao.getAllByPageById("AlbumPicture", page, albumConfig.getPerPagePhotoNum(), true, "photoTime", "albumId", albumId);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (AlbumPicture albumPicture : albumPictureList) {
            try {
                albumPicture.setCreateTime(sdf.format(sdf.parse(albumPicture.getCreateTime())));
            } catch (ParseException e) {
                basicJson.setStatus(false);
                basicJson.getErrMsg().setCode("05006");
                basicJson.setJsonStr("获取失败");
                return null;
            }
        }
        basicJson.setStatus(true);
        basicJson.getErrMsg().setCode("200");
        basicJson.getErrMsg().setMessage("获取成功");
        PhotoJson photoJson = new PhotoJson();
        photoJson.setAlbumName(((Album) basicDao.get(Album.class, Integer.valueOf(albumId))).getName());
        photoJson.setAlbumDescription(((Album) basicDao.get(Album.class, Integer.valueOf(albumId))).getDescription());
        photoJson.setEntityList(albumPictureList);
        Double totalPageDouble = Double.valueOf(String.valueOf(getPhotoNum(albumId)));
        Double requestPageNumDouble = Double.valueOf(page);
        int pageNum = ((Double) Math.ceil(totalPageDouble / requestPageNumDouble)).intValue();
        photoJson.setPage(pageNum);
        basicJson.setJsonStr(photoJson);
        return basicJson;
    }

    @Override
    @SuppressWarnings("unchecked")
    public int getAlbumNum() {
        return ((List<Album>) basicDao.getAll("Album")).size();
    }

    @Override
    @SuppressWarnings("unchecked")
    public int getPhotoNum(String albumId) {
        return (((List<AlbumPicture>) basicDao.getAllByForeignKey("AlbumPicture", "albumId", Integer.valueOf(albumId)))).size();
    }
}
