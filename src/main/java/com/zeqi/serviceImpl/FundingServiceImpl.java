package com.zeqi.serviceImpl;

import com.zeqi.dao.BasicDao;
import com.zeqi.daoImpl.BasicDaoImpl;
import com.zeqi.database.DocumentInfo;
import com.zeqi.database.Funding;
import com.zeqi.database.StudentInfo;
import com.zeqi.json.BasicJson;
import com.zeqi.json.EntityJson;
import com.zeqi.service.FundingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Max on 2016/12/2.
 */
@Service
public class FundingServiceImpl implements FundingService {

    @Autowired
    private BasicDao basicDao;

    /**
     * 作用：查看财务明细
     * @return
     */
    @Override
    @SuppressWarnings("unchecked")
    public BasicJson getFundingList(String[] pageInfo) {
        BasicJson basicJson = new BasicJson(false);
        List<Funding> fundingList = (List<Funding>) basicDao.getAllByPage("Funding", pageInfo, true, "updateDate");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (Funding funding : fundingList) {
            try {
                funding.setUpdateDate(sdf.format(sdf.parse(funding.getUpdateDate())));
            } catch (ParseException e) {
                basicJson.setStatus(false);
                basicJson.getErrMsg().setCode("07004");
                basicJson.setJsonStr("获取失败");
                return basicJson;
            }
        }
        basicJson.setStatus(true);
        basicJson.getErrMsg().setCode("200");
        basicJson.getErrMsg().setMessage("获取成功");
        EntityJson<Funding> fundingEntityJson = new EntityJson<Funding>();
        fundingEntityJson.setEntityList(fundingList);
        Double totalPageDouble = Double.valueOf(String.valueOf(getFundingNum()));
        Double requestPageNumDouble = Double.valueOf(pageInfo[1]);
        int pageNum = ((Double)Math.ceil(totalPageDouble / requestPageNumDouble)).intValue();
        fundingEntityJson.setPage(pageNum);
        basicJson.setJsonStr(fundingEntityJson);
        return basicJson;
    }


    /**
     * 作用：新增财务记录
     * @param request
     * @return
     */
    @Override
    public BasicJson addFunding(HttpServletRequest request) {
        BasicJson basicJson = new BasicJson(false);
        Funding funding = new Funding();
        funding.setSpentMoney(request.getParameter("spentMoney"));
        funding.setCostUse(request.getParameter("costUse"));
        funding.setLeftMoney(request.getParameter("leftMoney"));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        funding.setUpdateDate(sdf.format(new Date()));
        funding.setStudentInfo((StudentInfo)request.getSession().getAttribute("student_info"));
        if (basicDao.save(funding)) {
            basicJson.setStatus(true);
            basicJson.getErrMsg().setCode("200");
            basicJson.getErrMsg().setMessage("新增财务记录成功");
            basicJson.setJsonStr(funding.getId());
        }
        else {
            basicJson.setStatus(false);
            basicJson.getErrMsg().setCode("07001");
            basicJson.getErrMsg().setMessage("新增财务记录失败");
        }
        return basicJson;
    }

    /**
     * 作用：更新财务记录
     * @param id
     * @param request
     * @return
     */
    @Override
    public BasicJson updateFunding(int id, HttpServletRequest request) {
        BasicJson basicJson = new BasicJson(false);
        Funding funding = (Funding) basicDao.get(new Funding(), id);
        funding.setSpentMoney(request.getParameter("spentMoney"));
        funding.setCostUse(request.getParameter("costUse"));
        if (basicDao.save(funding)) {
            basicJson.setStatus(true);
            basicJson.getErrMsg().setCode("200");
            basicJson.getErrMsg().setMessage("更新成功");
        }
        else {
            basicJson.setStatus(false);
            basicJson.getErrMsg().setCode("07002");
            basicJson.getErrMsg().setMessage("更新失败");
        }
        return basicJson;
    }

    /**
     * 作用：删除财务记录
     * @param fundingSId
     * @return
     */
    @Override
    public BasicJson deleteFunding(String[] fundingSId) {
        BasicJson basicJson = new BasicJson(false);
        if (basicDao.delete(fundingSId, "Funding")) {
            basicJson.setStatus(true);
            basicJson.getErrMsg().setCode("200");
            basicJson.getErrMsg().setMessage("删除成功");
        }
        else {
            basicJson.setStatus(false);
            basicJson.getErrMsg().setCode("07003");
            basicJson.getErrMsg().setMessage("删除失败");
        }
        return basicJson;
    }

    @Override
    @SuppressWarnings("unchecked")
    public int getFundingNum() {
        return ((List<Funding>) basicDao.getAll("Funding")).size();
    }
}
