package com.zeqi.serviceImpl;

import com.zeqi.dao.BasicDao;
import com.zeqi.database.Article;
import com.zeqi.database.StudentInfo;
import com.zeqi.dataconfig.ArticleConfig;
import com.zeqi.dto.ArticleEntityDTO;
import com.zeqi.dto.ArticleIndexDTO;
import com.zeqi.json.BasicJson;
import com.zeqi.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Max on 2016/10/14.
 */
@Service
public class ArticleServiceImpl implements ArticleService {

	@Autowired
	private BasicDao basicDao;
	@Autowired
	private ArticleConfig articleConfig;

	/**
	 * 作用：发布文章
	 * 
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
		Timestamp timestamp = new Timestamp(new Date().getTime());
		article.setCreateTime(timestamp.toString());
		article.setStudentInfo((StudentInfo)basicDao.get(StudentInfo.class, ((StudentInfo) request.getSession().getAttribute("student_info")).getStuId()));
		article.setCommentCount(0);
		isWrited = basicDao.save(article);
		if (isWrited) {
			basicJson.setStatus(true);
			basicJson.getErrMsg().setCode("200");
			basicJson.getErrMsg().setMessage("发布成功");
			basicJson.setJsonStr(article.getId());
		} else {
			basicJson.getErrMsg().setCode("04001");
			basicJson.getErrMsg().setMessage("发布文章失败");
		}
		return basicJson;
	}

	/**
	 * 作用：删除文章
	 * 
	 * @param articleId
	 * @return
	 */
	@Override
	public BasicJson deleteArticle(String[] articleId) {
		BasicJson basicJson = new BasicJson(false);
		if (basicDao.delete(articleId, Article.class)) {
			basicJson.setStatus(true);
			basicJson.getErrMsg().setCode("200");
			basicJson.getErrMsg().setMessage("删除成功");
		} else {
			basicJson.setStatus(false);
			basicJson.getErrMsg().setCode("04002");
			basicJson.getErrMsg().setMessage("删除失败");
		}
		return basicJson;
	}

	/**
	 * 作用：更新文章
	 * 
	 * @param id
	 * @param request
	 * @return
	 */
	@Override
	public BasicJson updateArticle(int id, HttpServletRequest request) {
		BasicJson basicJson = new BasicJson(false);
		Article article = (Article) basicDao.get(Article.class, id);
		article.setTitle(request.getParameter("title"));
		article.setContent(request.getParameter("content"));
		if (basicDao.save(article)) {
			basicJson.setStatus(true);
			basicJson.getErrMsg().setCode("200");
			basicJson.getErrMsg().setMessage("更新成功");
		} else {
			basicJson.setStatus(false);
			basicJson.getErrMsg().setCode("04003");
			basicJson.getErrMsg().setMessage("更新失败");
		}
		return basicJson;
	}

	/**
	 * 阅读一篇文章
	 * 
	 * @param articleId
	 * @return
	 */
	@Override
	public ArticleEntityDTO getArticle(int articleId) {
		Article article;
		ArticleEntityDTO articleEntityDTO;
		article = (Article) basicDao.get(Article.class, articleId);
		if (article != null) {
			articleEntityDTO = new ArticleEntityDTO();
			articleEntityDTO.setArticleId(article.getId());
			articleEntityDTO.setTitle(article.getTitle());
			articleEntityDTO.setCommentCount(article.getCommentCount());
			articleEntityDTO.setWriteTime(article.getCreateTime());
			articleEntityDTO.setContent(article.getContent());
			return articleEntityDTO;
		} else {
			return null;
		}
	}

	/**
	 * 获取文章列表
	 * 
	 * @return
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Map<String, Object> getArticleList(String page) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Article> articleList;
		int maxResults = Integer.valueOf(articleConfig.getPaging().get("perPageArticleNum"));
		int firstResult = (Integer.valueOf(page) - 1) * maxResults;
		articleList = (List<Article>) basicDao.paginationQuery(firstResult, maxResults, Article.class);
		List<ArticleIndexDTO> articleDTOList = new ArrayList<ArticleIndexDTO>();
		for (Article article : articleList) {
			ArticleIndexDTO articleDTO = new ArticleIndexDTO();
			articleDTO.setArticleId(article.getId());
			articleDTO.setTitle(article.getTitle());
			articleDTO.setAuthor(article.getStudentInfo().getName());
			articleDTO.setCommentCount(article.getCommentCount());
			articleDTO.setWriteTime(article.getCreateTime());
			articleDTOList.add(articleDTO);
		}
		Double articleNum = Double.valueOf(basicDao.getTotalCount(Article.class));
		Double perPageArticleNum = Double.valueOf(maxResults);
		int totalPages = ((Double) Math.ceil(articleNum / perPageArticleNum)).intValue();
		
		Map<String, String> articleConfig = new HashMap<String, String>();
		articleConfig.put("totalPages", String.valueOf(totalPages));
		articleConfig.put("currentPage", page);
		articleConfig.put("getArticleUrl", this.articleConfig.getApiConfig().get("getArticleUrl"));
		articleConfig.put("getArticlesUrl", this.articleConfig.getApiConfig().get("getArticlesUrl"));		
		map.put("articleDTOList", articleDTOList);
		map.put("articleConfig", articleConfig);
		return map;
	}

	/**
	 * 获取文章列表
	 * 
	 * @return
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Map<String, Object> getArticleList(String page, String stuId) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Article> articleList;
		int maxResults = Integer.valueOf(articleConfig.getPaging().get("perPageArticleNum"));
		int firstResult = (Integer.valueOf(page) - 1) * maxResults;
		articleList = (List<Article>) basicDao.paginationQuery(firstResult, maxResults, Article.class, "studentInfo.stuId", stuId);
		List<ArticleIndexDTO> articleDTOList = new ArrayList<ArticleIndexDTO>();
		for (Article article : articleList) {
			ArticleIndexDTO articleDTO = new ArticleIndexDTO();
			articleDTO.setArticleId(article.getId());
			articleDTO.setTitle(article.getTitle());
			articleDTO.setAuthor(article.getStudentInfo().getName());
			articleDTO.setCommentCount(article.getCommentCount());
			articleDTO.setWriteTime(article.getCreateTime());
			articleDTOList.add(articleDTO);
		}
		Double articleNum = Double.valueOf(basicDao.getTotalCount("studentInfo.stuId", stuId, Article.class));
		Double perPageArticleNum = Double.valueOf(maxResults);
		int totalPages = ((Double) Math.ceil(articleNum / perPageArticleNum)).intValue();
		
		Map<String, String> articleConfig = new HashMap<String, String>();
		articleConfig.put("totalPages", String.valueOf(totalPages));
		articleConfig.put("currentPage", page);
		articleConfig.put("getArticleUrl", this.articleConfig.getApiConfig().get("getArticleUrl"));
		articleConfig.put("getArticlesUrl", this.articleConfig.getApiConfig().get("getArticlesUrl"));
		map.put("articleDTOList", articleDTOList);
		map.put("articleConfig", articleConfig);
		return map;
	}
}
