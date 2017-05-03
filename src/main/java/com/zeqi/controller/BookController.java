package com.zeqi.controller;

import com.zeqi.database.*;
import com.zeqi.json.BasicJson;
import com.zeqi.service.BookService;
import com.zeqi.service.UserCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Max on 2016/11/23.
 */
@RestController
@RequestMapping("/web")
public class BookController {


    @Autowired
    private BookService bookService;


    /**
     * 作用：借阅书籍
     * @return
     */
    @RequestMapping(value = "/bookLoan", method = RequestMethod.POST)
    public BasicJson addBookLoan(HttpServletRequest request) {
        BasicJson basicJson;
        basicJson = bookService.addBookLoan(request);
        return basicJson;
    }


    /**
     * 作用：归还书籍
     * @return
     */
    @RequestMapping(value = "/bookLoan/{id}", method = RequestMethod.DELETE)
    public BasicJson deleteBookLoan(@PathVariable String id, HttpServletRequest request) {
        BasicJson basicJson;
        String[] bookLoanId = id.split("&");
        basicJson = bookService.deleteBookLoan(bookLoanId);
        return basicJson;
    }


    /**
     * 作用：查看所有书籍借阅情况
     * @return
     */
    @RequestMapping(value = "/bookLoan/{page}", method = RequestMethod.GET)
    public BasicJson getBookLoanList(@PathVariable String page) {
        BasicJson basicJson;
        String[] pageInfo = page.split("&");
        basicJson = bookService.getBookLoanList(pageInfo, false, null);
        return basicJson;
    }

    /**
     * 作用：查看个人已借书籍情况
     * @return
     */
    @RequestMapping(value = "/guy/bookLoan/{page}", method = RequestMethod.GET)
    public BasicJson getBookLoanListByGuy(@PathVariable String page, HttpServletRequest request) {
        BasicJson basicJson;
        String[] pageInfo = page.split("&");
        basicJson = bookService.getBookLoanList(pageInfo, true, ((StudentInfo) request.getSession().getAttribute("student_info")).getStuId());
        return basicJson;
    }

    /**
     * 作用：新书录入
     * @return
     */
    @RequestMapping(value = "/book", method = RequestMethod.POST)
    public BasicJson addBook(HttpServletRequest request) {
        BasicJson basicJson;
        basicJson = bookService.addBook(request);
        return basicJson;
    }

    /**
     * 作用：修改录入信息
     * @return
     */
    @RequestMapping(value = "/book/{id}", method = RequestMethod.PUT)
    public BasicJson updateBook(@PathVariable int id, HttpServletRequest request) {
        BasicJson basicJson;
        basicJson = bookService.updateBook(id, request);
        return basicJson;
    }


    /**
     * 作用：删除录入信息
     * @return
     */
    @RequestMapping(value = "/book/{id}", method = RequestMethod.DELETE)
    public BasicJson deleteBook(@PathVariable String id) {
        BasicJson basicJson;
        String[] bookId = id.split("&");
        basicJson = bookService.deleteBook(bookId);
        return basicJson;
    }

    /**
     * 作用：删除录入信息
     * @return
     */
    @RequestMapping(value = "/book/{page}", method = RequestMethod.GET)
    public BasicJson getBookList(@PathVariable String page) {
        BasicJson basicJson;
        String[] pageInfo = page.split("&");
        basicJson = bookService.getBookList(pageInfo);
        return basicJson;
    }
}
