package com.zhang.storagestatisticsserve.web;

import com.zhang.storagestatisticsserve.config.ApiResponse;
import com.zhang.storagestatisticsserve.entity.SysConfig;
import com.zhang.storagestatisticsserve.service.SysConfigService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/sysConfig", produces = "application/json")
public class SysConfigController {

    @Resource
    private SysConfigService sysConfigService;

    /**
     * 新增
     * @author zhang
     * @date 2024/04/18
     **/
    @RequestMapping("/insert")
    public Object insert(SysConfig sysConfig){
        return sysConfigService.insert(sysConfig);
    }

    /**
     * 刪除
     * @author zhang
     * @date 2024/04/18
     **/
    @RequestMapping("/delete")
    public Object delete(int id){
        return sysConfigService.delete(id);
    }

    /**
     * 更新
     * @author zhang
     * @date 2024/04/18
     **/
    @RequestMapping("/update")
    public Object update(SysConfig sysConfig){
        return sysConfigService.update(sysConfig);
    }

    /**
     * 查询 根据主键 id 查询
     * @author zhang
     * @date 2024/04/18
     **/
    @RequestMapping("/load")
    public Object load(int id){
        return sysConfigService.load(id);
    }

    /**
     * 查询 分页查询
     * @author zhang
     * @date 2024/04/18
     **/
    @RequestMapping("/pageList")
    public Map<String, Object> pageList(@RequestParam(required = false, defaultValue = "0") int offset,
                                        @RequestParam(required = false, defaultValue = "10") int pagesize) {
        return sysConfigService.pageList(offset, pagesize);
    }

    @GetMapping("/getSystemParams")
    public ApiResponse getSystemParams(){
        int id = 1;
        return ApiResponse.ok(sysConfigService.load(id));
    }
}
