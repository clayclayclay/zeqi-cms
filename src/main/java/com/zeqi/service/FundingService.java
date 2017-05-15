package com.zeqi.service;

import com.zeqi.database.Funding;
import com.zeqi.json.BasicJson;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Max on 2016/12/2.
 */
public interface FundingService {

    BasicJson addFunding(HttpServletRequest request);

    BasicJson deleteFunding(String[] fundingSId);

    BasicJson updateFunding(int id, HttpServletRequest request);

    BasicJson getFundingList(String page);

    int getFundingNum();

}
