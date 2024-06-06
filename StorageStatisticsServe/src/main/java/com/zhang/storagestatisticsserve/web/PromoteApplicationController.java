package com.zhang.storagestatisticsserve.web;

import com.zhang.storagestatisticsserve.config.ApiResponse;
import com.zhang.storagestatisticsserve.entity.PromoteApplication;
import com.zhang.storagestatisticsserve.service.PromoteApplicationService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/promoteApp", produces = "application/json")
public class PromoteApplicationController {
    @Resource
    private PromoteApplicationService promoteApplicationService;

    /**
     * 新增
     * @author zhang
     * @date 2024/04/18
     **/
    @RequestMapping("/insert")
    public Object insert(PromoteApplication promoteApplication){
        return promoteApplicationService.insert(promoteApplication);
    }

    /**
     * 刪除
     * @author zhang
     * @date 2024/04/18
     **/
    @RequestMapping("/delete")
    public Object delete(int id){
        return promoteApplicationService.delete(id);
    }

    /**
     * 更新
     * @author zhang
     * @date 2024/04/18
     **/
    @RequestMapping("/update")
    public Object update(PromoteApplication promoteApplication){
        return promoteApplicationService.update(promoteApplication);
    }

    /**
     * 查询 根据主键 id 查询
     * @author zhang
     * @date 2024/04/18
     **/
    @RequestMapping("/load")
    public Object load(int id){
        return promoteApplicationService.load(id);
    }

    /**
     * 查询 分页查询
     * @author zhang
     * @date 2024/04/18
     **/
    @RequestMapping("/pageList")
    public ApiResponse pageList(@RequestBody Map<String, Object> params) {
        return ApiResponse.ok(promoteApplicationService.pageList(params));
    }
}
