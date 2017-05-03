package com.zeqi.serviceImpl;

import com.zeqi.dao.BasicDao;
import com.zeqi.database.*;
import com.zeqi.json.BasicJson;
import com.zeqi.json.EntityJson;
import com.zeqi.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Max on 2016/12/3.
 */
@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BasicDao basicDao;


    /**
     * 借阅书籍
     * @param request
     * @return
     */
    @Override
    public BasicJson addBookLoan(HttpServletRequest request) {
        BasicJson basicJson = new BasicJson(false);
        BookLoan bookloan = new BookLoan();
        bookloan.setNote(request.getParameter("note"));
        bookloan.setStuId(((StudentInfo) request.getSession().getAttribute("student_info")).getStuId());
        bookloan.setStuName(((StudentInfo) request.getSession().getAttribute("student_info")).getName());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        bookloan.setBorrowTime(sdf.format(new Date()));
        BookInfo bookInfo = (BookInfo) basicDao.get(new BookInfo(), request.getParameter("id"));
        if (bookInfo != null) {
            bookloan.setBookId(bookInfo.getId());
            bookloan.setBookName(bookInfo.getName());
        } else {
            basicJson.setStatus(false);
            basicJson.getErrMsg().setCode("02001");
            basicJson.getErrMsg().setMessage("借阅失败");
        }

        if (basicDao.save(bookloan)) {
            basicJson.setStatus(true);
            basicJson.getErrMsg().setCode("200");
            basicJson.getErrMsg().setMessage("借阅成功");
            basicJson.setJsonStr(bookloan.getId());
        } else {
            basicJson.setStatus(false);
            basicJson.getErrMsg().setCode("02001");
            basicJson.getErrMsg().setMessage("借阅失败");
        }
        return basicJson;
    }


    /**
     * 归还图书
     * @param bookLoanId
     * @return
     */
    @Override
    public BasicJson deleteBookLoan(String[] bookLoanId) {
        BasicJson basicJson = new BasicJson(false);
        if (basicDao.delete(bookLoanId, "BookLoan")) {
            basicJson.setStatus(true);
            basicJson.getErrMsg().setCode("200");
            basicJson.getErrMsg().setMessage("归还成功");
        } else {
            basicJson.setStatus(false);
            basicJson.getErrMsg().setCode("02002");
            basicJson.getErrMsg().setMessage("归还失败");
        }
        return basicJson;
    }

    /**
     * 获取借阅记录（可直接查询当前用户的借阅记录）
     * @param pageInfo
     * @param isSpecific
     * @param stuId
     * @return
     */
    @Override
    @SuppressWarnings("unchecked")
    public BasicJson getBookLoanList(String[] pageInfo, boolean isSpecific, String stuId) {
        BasicJson basicJson = new BasicJson(false);
        EntityJson<BookLoan> bookLoanEntityJson = new EntityJson<BookLoan>();
        List<BookLoan> bookLoanList = (List<BookLoan>) basicDao.getAllByPage("BookLoan", pageInfo, true, "borrowTime");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (isSpecific) {
            List<BookLoan> bookLoanListByGuy = new ArrayList<BookLoan>();
            for (BookLoan bookLoan : bookLoanList) {
                if (bookLoan.getStuId().equals(stuId)) {
                    try {
                        bookLoan.setBorrowTime(sdf.format(sdf.parse(bookLoan.getBorrowTime())));
                        bookLoanListByGuy.add(bookLoan);
                    } catch (ParseException e) {
                        basicJson.setStatus(false);
                        basicJson.getErrMsg().setCode("02003");
                        basicJson.getErrMsg().setMessage("获取失败");
                        return basicJson;
                    }
                }
            }
            Double totalPageDouble = Double.valueOf(String.valueOf(getBookLoanNum(true, stuId)));
            Double requestPageNumDouble = Double.valueOf(pageInfo[1]);
            int pageNum = ((Double) Math.ceil(totalPageDouble / requestPageNumDouble)).intValue();
            bookLoanEntityJson.setEntityList(bookLoanListByGuy);
            bookLoanEntityJson.setPage(pageNum);
            basicJson.setStatus(true);
            basicJson.getErrMsg().setCode("200");
            basicJson.getErrMsg().setMessage("获取成功");
            basicJson.setJsonStr(bookLoanEntityJson);
            return basicJson;
        }
        for (BookLoan bookLoan : bookLoanList) {
            try {
                bookLoan.setBorrowTime(sdf.format(sdf.parse(bookLoan.getBorrowTime())));
            } catch (ParseException e) {
                return null;
            }
        }
        Double totalPageDouble = Double.valueOf(String.valueOf(getBookLoanNum(false, null)));
        Double requestPageNumDouble = Double.valueOf(pageInfo[1]);
        int pageNum = ((Double) Math.ceil(totalPageDouble / requestPageNumDouble)).intValue();
        bookLoanEntityJson.setEntityList(bookLoanList);
        bookLoanEntityJson.setPage(pageNum);
        basicJson.setJsonStr(bookLoanEntityJson);
        basicJson.setStatus(true);
        basicJson.getErrMsg().setCode("200");
        basicJson.getErrMsg().setMessage("查询成功");
        return basicJson;
    }


    /**
     * 添加图书记录信息
     * @param request
     * @return
     */
    @Override
    public BasicJson addBook(HttpServletRequest request) {
        BasicJson basicJson = new BasicJson(false);
        BookInfo bookInfo = new BookInfo();
        bookInfo.setName(request.getParameter("name"));
        bookInfo.setPublisher(request.getParameter("publisher"));
        bookInfo.setAuthor(request.getParameter("author"));
        bookInfo.setStatus(0);

        if (basicDao.save(bookInfo)) {
            basicJson.setStatus(true);
            basicJson.getErrMsg().setCode("200");
            basicJson.getErrMsg().setMessage("录入成功");
            basicJson.setJsonStr(bookInfo.getId());
        } else {
            basicJson.setStatus(false);
            basicJson.getErrMsg().setCode("02003");
            basicJson.getErrMsg().setMessage("录入失败");
        }
        return basicJson;
    }


    /**
     * 删除图书记录信息
     * @param bookId
     * @return
     */
    @Override
    public BasicJson deleteBook(String[] bookId) {
        BasicJson basicJson = new BasicJson(false);
        if (basicDao.delete(bookId, "BookLoan")) {
            basicJson.setStatus(true);
            basicJson.getErrMsg().setCode("200");
            basicJson.getErrMsg().setMessage("删除成功");
        } else {
            basicJson.setStatus(false);
            basicJson.getErrMsg().setCode("02005");
            basicJson.getErrMsg().setMessage("删除失败");
        }
        return basicJson;
    }


    /**
     * 更新图书记录信息
     * @param id
     * @param request
     * @return
     */
    @Override
    public BasicJson updateBook(int id, HttpServletRequest request) {
        BasicJson basicJson = new BasicJson(false);
        BookInfo bookInfo = (BookInfo) basicDao.get(new BookInfo(), id);
        bookInfo.setName(request.getParameter("name"));
        bookInfo.setPublisher(request.getParameter("publisher"));
        bookInfo.setAuthor(request.getParameter("author"));
        if (basicDao.save(bookInfo)) {
            basicJson.setStatus(true);
            basicJson.getErrMsg().setCode("200");
            basicJson.getErrMsg().setMessage("修改成功");
        } else {
            basicJson.setStatus(false);
            basicJson.getErrMsg().setCode("02004");
            basicJson.getErrMsg().setMessage("修改失败");
        }
        return basicJson;
    }


    /** 获取图书记录列表
     * @param pageInfo
     * @return
     */
    @Override
    @SuppressWarnings("unchecked")
    public BasicJson getBookList(String[] pageInfo) {
        BasicJson basicJson = new BasicJson(false);
        EntityJson<BookInfo> bookLoanEntityJson = new EntityJson<BookInfo>();
        List<BookInfo> bookInfoList = (List<BookInfo>) basicDao.getAllByPage("BookInfo", pageInfo, false, null);
        Double totalPageDouble = Double.valueOf(String.valueOf(getBookNum()));
        Double requestPageNumDouble = Double.valueOf(pageInfo[1]);
        int pageNum = ((Double) Math.ceil(totalPageDouble / requestPageNumDouble)).intValue();
        bookLoanEntityJson.setEntityList(bookInfoList);
        bookLoanEntityJson.setPage(pageNum);
        basicJson.setJsonStr(bookLoanEntityJson);
        basicJson.setStatus(true);
        basicJson.getErrMsg().setCode("200");
        basicJson.getErrMsg().setMessage("查询成功");
        return basicJson;
    }

    /** 获取借阅记录数
     * @param isSpecific
     * @param stuId
     * @return
     */
    @Override
    @SuppressWarnings("unchecked")
    public int getBookLoanNum(boolean isSpecific, String stuId) {
        if (isSpecific) {
            return ((List<BookLoan>) basicDao.getAllByForeignKey("BookLoan", "stu_id", stuId)).size();
        } else {
            return ((List<BookLoan>) basicDao.getAll("BookLoan")).size();
        }
    }


    /** 获取图书数
     * @return
     */
    @Override
    @SuppressWarnings("unchecked")
    public int getBookNum() {
        return ((List<BookInfo>) basicDao.getAll("BookInfo")).size();
    }
}
