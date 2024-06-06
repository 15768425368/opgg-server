package com.zhang.storagestatisticsserve.service.imp;

import com.zhang.storagestatisticsserve.config.ApiResponse;
import com.zhang.storagestatisticsserve.entity.PromoteApplication;
import com.zhang.storagestatisticsserve.mapper.PromoteApplicationMapper;
import com.zhang.storagestatisticsserve.service.PromoteApplicationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PromoteApplicationServiceImpl implements PromoteApplicationService {
    @Resource
    private PromoteApplicationMapper promoteApplicationMapper;


    @Override
    public Object insert(PromoteApplication promoteApplication) {

        // valid
        if (promoteApplication == null) {
            return ApiResponse.error("必要参数缺失");
        }

        promoteApplicationMapper.insert(promoteApplication);
        return ApiResponse.ok();
    }


    @Override
    public Object delete(int id) {
        int ret = promoteApplicationMapper.delete(id);
        return ret>0?ApiResponse.ok():ApiResponse.error("系统错误!");
    }


    @Override
    public Object update(PromoteApplication promoteApplication) {
        int ret = promoteApplicationMapper.update(promoteApplication);
        return ret>0?ApiResponse.ok():ApiResponse.error("系统错误!");
    }


    @Override
    public PromoteApplication load(int id) {
        return promoteApplicationMapper.load(id);
    }


    @Override
    public Map<String,Object> pageList(Map<String, Object> params) {

        int offset = (int) params.get("offset");
        offset = offset -1;
        int pagesize = (int) params.get("pagesize");
        int state = 0;

        List<PromoteApplication> pageList = promoteApplicationMapper.pageList(offset, pagesize, state);
        int totalCount = promoteApplicationMapper.pageListCount(offset, pagesize, state);

        // result
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("pageList", pageList);
        result.put("totalCount", totalCount);

        return result;
    }
}
