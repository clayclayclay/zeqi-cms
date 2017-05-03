package com.zeqi.controller;

import com.zeqi.database.Funding;
import com.zeqi.json.BasicJson;
import com.zeqi.service.FundingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Max on 2016/12/2.
 */
@RestController
@RequestMapping("/web")
public class FundingController {

    @Autowired
    private FundingService fundingService;

    /**
     * 作用：新增财务记录
     * @return
     */
    @RequestMapping(value = "/funding", method = RequestMethod.POST)
    public BasicJson addFunding(HttpServletRequest request) {
        BasicJson basicJson;
        basicJson = fundingService.addFunding(request);
        return basicJson;
    }


    /**
     * 作用：删除财务记录
     * @return
     */
    @RequestMapping(value = "/funding/{id}", method = RequestMethod.DELETE)
    public BasicJson deleteFunding(@PathVariable String id) {
        BasicJson basicJson;
        String[] fundingSId = id.split("&");
        basicJson = fundingService.deleteFunding(fundingSId);
        return basicJson;
    }

    /**
     * 作用：修改财务记录
     * @return
     */
    @RequestMapping(value = "/funding/{id}", method = RequestMethod.PUT)
    public BasicJson updateFunding(@PathVariable int id, HttpServletRequest request) {
        BasicJson basicJson;
        basicJson = fundingService.updateFunding(id, request);
        return basicJson;
    }

    /**
     * 作用：查看财务明细
     * @return
     */
    @RequestMapping(value = "/funding/{page}", method = RequestMethod.GET)
    public BasicJson getFunding(@PathVariable String page) {
        BasicJson basicJson;
        String[] pageInfo = page.split("&");
        basicJson = fundingService.getFundingList(pageInfo);
        return basicJson;
    }

//    /**
//     * 作用：查看财务明细(个人中心)
//     * @return
//     */
//    @RequestMapping(value = "/guy/funding/{page}", method = RequestMethod.GET)
//    public ModelAndView getFundingByGuy(@PathVariable String page) {
//        ModelAndView modelAndView = new ModelAndView("yyy");
//        String[] pageInfo = page.split("&");
//        List<Funding> fundingList = fundingService.getFundingList(pageInfo);
//        Double totalPageDouble = Double.valueOf(String.valueOf(fundingService.getDocumentNum()));
//        Double requestPageNumDouble = Double.valueOf(pageInfo[1]);
//        int pageNum = ((Double)Math.ceil(totalPageDouble / requestPageNumDouble)).intValue();
//        modelAndView.addObject("fundingList", fundingList);
//        modelAndView.addObject("page", pageNum);
//        return modelAndView;
//    }




}
