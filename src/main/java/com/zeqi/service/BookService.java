package com.zeqi.service;

import com.zeqi.json.BasicJson;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Max on 2016/12/3.
 */
public interface BookService {

    BasicJson addBookLoan(HttpServletRequest request);

    BasicJson deleteBookLoan(String[] bookLoanId);

    BasicJson getBookLoanList(String page, boolean isSpecific, String stuId);

    BasicJson addBook(HttpServletRequest request);

    BasicJson deleteBook(String[] bookId);

    BasicJson updateBook(int id, HttpServletRequest request);

    BasicJson getBookList(String page);

    int getBookLoanNum(boolean isSpecific, String stuId);

    int getBookNum();
}
