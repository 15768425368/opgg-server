package com.zhang.storagestatisticsserve.service.imp;

import com.zhang.storagestatisticsserve.config.ApiResponse;
import com.zhang.storagestatisticsserve.entity.WxFeedback;
import com.zhang.storagestatisticsserve.mapper.WxFeedbackMapper;
import com.zhang.storagestatisticsserve.service.WxFeedbackService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WxFeedbackServiceImpl implements WxFeedbackService {
    @Resource
    private WxFeedbackMapper wxFeedbackMapper;


    @Override
    public Object insert(WxFeedback wxFeedback, String openId) {

        // valid
        if (wxFeedback == null) {
            return ApiResponse.error("必要参数缺失");
        }

        if(openId == null || openId == "") {
            return ApiResponse.error("必要参数缺失");
        }

        wxFeedback.setCreateTime(new Date());
        wxFeedback.setUploadTime(new Date());

        wxFeedback.setOpenid(openId);

        wxFeedbackMapper.insert(wxFeedback);
        return ApiResponse.ok();
    }


    @Override
    public Object delete(int id) {
        int ret = wxFeedbackMapper.delete(id);
        return ret>0?ApiResponse.ok():ApiResponse.error("系统错误!");
    }


    @Override
    public Object update(WxFeedback wxFeedback) {
        int ret = wxFeedbackMapper.update(wxFeedback);
        return ret>0?ApiResponse.ok():ApiResponse.error("系统错误!");
    }


    @Override
    public WxFeedback load(int id) {
        return wxFeedbackMapper.load(id);
    }


    @Override
    public Map<String,Object> pageList(int offset, int pagesize) {

        List<WxFeedback> pageList = wxFeedbackMapper.pageList(offset, pagesize);
        int totalCount = wxFeedbackMapper.pageListCount(offset, pagesize);

        // result
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("pageList", pageList);
        result.put("totalCount", totalCount);

        return result;
    }
}
