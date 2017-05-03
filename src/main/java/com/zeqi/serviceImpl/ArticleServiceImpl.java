package com.zeqi.serviceImpl;

import com.zeqi.dao.BasicDao;
import com.zeqi.database.Article;
import com.zeqi.database.StudentInfo;
import com.zeqi.json.EntityJson;
import com.zeqi.json.BasicJson;
import com.zeqi.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Max on 2016/10/14.
 */
@Service
public class ArticleServiceImpl implements ArticleService {


    @Autowired
    private BasicDao basicDao;

    /**
     * 作用：发布文章
     * @param request
     * @return
     */
    @Override
    public BasicJson addArticle(HttpServletRequest request) {
        BasicJson basicJson = new BasicJson(false);
        Article article = new Article();
        boolean isWrited;
        article.setTitle(request.getParameter("title"));
        article.setContent(request.getParameter("content"));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Timestamp timestamp = new Timestamp(new Date().getTime());
        article.setDateGmt(timestamp.toString());
        System.out.println("author_id is: " + ((StudentInfo) request.getSession().getAttribute("student_info")).getStuId());
        article.setStuId(((StudentInfo) request.getSession().getAttribute("student_info")).getStuId());
        article.setStuName(((StudentInfo) request.getSession().getAttribute("student_info")).getName());
        article.setCommentCount(0);
        isWrited = basicDao.save(article);
        if (isWrited) {
            basicJson.setStatus(true);
            basicJson.getErrMsg().setCode("200");
            basicJson.getErrMsg().setMessage("发布成功");
            basicJson.setJsonStr(article.getId());
        }
        else {
            basicJson.getErrMsg().setCode("04001");
            basicJson.getErrMsg().setMessage("发布文章失败");
        }
        return basicJson;
    }

    /**
     * 作用：删除文章
     * @param articleId
     * @return
     */
    @Override
    public BasicJson deleteArticle(String[] articleId) {
        BasicJson basicJson = new BasicJson(false);
        if (basicDao.delete(articleId, "Article")) {
            basicJson.setStatus(true);
            basicJson.getErrMsg().setCode("200");
            basicJson.getErrMsg().setMessage("删除成功");
        }
        else {
            basicJson.setStatus(false);
            basicJson.getErrMsg().setCode("04002");
            basicJson.getErrMsg().setMessage("删除失败");
        }
        return basicJson;
    }

    /**
     * 作用：更新文章
     * @param id
     * @param request
     * @return
     */
    @Override
    public BasicJson updateArticle(int id, HttpServletRequest request) {
        BasicJson basicJson = new BasicJson(false);
        Article article = (Article) basicDao.get(new Article(), id);
        article.setTitle(request.getParameter("title"));
        article.setContent(request.getParameter("content"));
        if (basicDao.save(article)) {
            basicJson.setStatus(true);
            basicJson.getErrMsg().setCode("200");
            basicJson.getErrMsg().setMessage("更新成功");
        }
        else {
            basicJson.setStatus(false);
            basicJson.getErrMsg().setCode("04003");
            basicJson.getErrMsg().setMessage("更新失败");
        }
        return basicJson;
    }


    /**
     * 阅读一篇文章
     * @param articleId
     * @return
     */
    @Override
    public BasicJson getArticle(int articleId) {
        BasicJson basicJson = new BasicJson(false);
        Article article;
        article = (Article)basicDao.get(new Article(), articleId);
        if (article != null) {
            basicJson.setStatus(true);
            basicJson.getErrMsg().setCode("200");
            basicJson.getErrMsg().setMessage("查询成功");
            basicJson.setJsonStr(article);
        }
        else {
            basicJson.setStatus(false);
            basicJson.getErrMsg().setCode("04004");
            basicJson.getErrMsg().setMessage("查询失败");
        }
        return basicJson;
    }

    /**
     * 获取文章列表
     * @return
     */
    @Override
    @SuppressWarnings("unchecked")
    public BasicJson getArticleList(String[] pageInfo, boolean isSpecific, String stuId) {
        BasicJson basicJson = new BasicJson(false);
        EntityJson<Article> articleEntityJson = new EntityJson<Article>();
        List<Article> articleList;
        articleList = (List<Article>)basicDao.getAllByPage("Article", pageInfo, true, "dateGmt");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (isSpecific) {
            List<Article> articleListByGuy = new ArrayList<Article>();
            for (Article article : articleList) {
                if (article.getStuId().equals(stuId)) {
                    try {
                        article.setDateGmt(sdf.format(sdf.parse(article.getDateGmt())));
                        articleListByGuy.add(article);
                    }  catch (ParseException e) {
                        basicJson.setStatus(false);
                        basicJson.getErrMsg().setCode("04006");
                        basicJson.getErrMsg().setMessage("查询失败");
                        return basicJson;
                    }
                }
                else {
                    continue;
                }
            }
            Double totalPageDouble = Double.valueOf(String.valueOf(getArticleNum(true, stuId)));
            Double requestPageNumDouble = Double.valueOf(pageInfo[1]);
            int pageNum = ((Double)Math.ceil(totalPageDouble / requestPageNumDouble)).intValue();
            articleEntityJson.setEntityList(articleListByGuy);
            articleEntityJson.setPage(pageNum);
            basicJson.setStatus(true);
            basicJson.getErrMsg().setCode("200");
            basicJson.getErrMsg().setMessage("获取成功");
            basicJson.setJsonStr(articleEntityJson);
            return basicJson;
        }
        for (Article article : articleList) {
            try {
                article.setDateGmt(sdf.format(sdf.parse(article.getDateGmt())));
            } catch (ParseException e) {
                basicJson.setStatus(false);
                basicJson.getErrMsg().setCode("04005");
                basicJson.getErrMsg().setMessage("查询失败");
                return basicJson;
            }
        }
        Double totalPageDouble = Double.valueOf(String.valueOf(getArticleNum(false, null)));
        Double requestPageNumDouble = Double.valueOf(pageInfo[1]);
        int pageNum = ((Double)Math.ceil(totalPageDouble / requestPageNumDouble)).intValue();
        articleEntityJson.setEntityList(articleList);
        articleEntityJson.setPage(pageNum);
        basicJson.setJsonStr(articleEntityJson);
        basicJson.setStatus(true);
        basicJson.getErrMsg().setCode("200");
        basicJson.getErrMsg().setMessage("查询成功");
        return basicJson;
    }

    /**
     *
     * @param isSpecific
     * @param stuId
     * @return
     */
    @Override
    @SuppressWarnings("unchecked")
    public int getArticleNum(boolean isSpecific, String stuId) {
        if (isSpecific) {
            return ((List<Article>) basicDao.getAllByForeignKey("Article", "stu_id", stuId)).size();
        }
        else {
            return ((List<Article>) basicDao.getAll("Article")).size();
        }
    }

}
