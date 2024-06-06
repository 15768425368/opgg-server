package com.zhang.storagestatisticsserve.service.imp;

import com.zhang.storagestatisticsserve.config.ApiResponse;
import com.zhang.storagestatisticsserve.entity.SysConfig;
import com.zhang.storagestatisticsserve.mapper.SysConfigMapper;
import com.zhang.storagestatisticsserve.service.SysConfigService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SysConfigServiceImpl implements SysConfigService {
    @Resource
    private SysConfigMapper sysConfigMapper;


    @Override
    public Object insert(SysConfig sysConfig) {

        // valid
        if (sysConfig == null) {
            return ApiResponse.error("必要参数缺失");
        }

        sysConfigMapper.insert(sysConfig);
        return ApiResponse.ok();
    }


    @Override
    public Object delete(int id) {
        int ret = sysConfigMapper.delete(id);
        return ret>0?ApiResponse.ok():ApiResponse.error("系统错误!");
    }


    @Override
    public Object update(SysConfig sysConfig) {
        int ret = sysConfigMapper.update(sysConfig);
        return ret>0?ApiResponse.ok():ApiResponse.error("系统错误!");
    }


    @Override
    public SysConfig load(int id) {
        return sysConfigMapper.load(id);
    }


    @Override
    public Map<String,Object> pageList(int offset, int pagesize) {

        List<SysConfig> pageList = sysConfigMapper.pageList(offset, pagesize);
        int totalCount = sysConfigMapper.pageListCount(offset, pagesize);

        // result
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("pageList", pageList);
        result.put("totalCount", totalCount);

        return result;
    }

}
