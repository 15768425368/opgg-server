package com.zhang.storagestatisticsserve.web;

import com.zhang.storagestatisticsserve.config.ApiResponse;
import com.zhang.storagestatisticsserve.entity.WxContainer;
import com.zhang.storagestatisticsserve.service.WxContainerService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/container", produces = "application/json")
public class WxContainerController {

    @Resource
    private WxContainerService wxContainerService;

    /**
     * 新增
     * @author zhang
     * @date 2024/04/09
     **/
    @PostMapping("/insert")
    public Object insert(@RequestBody WxContainer wxContainer, @RequestHeader("openid") String openid){
        wxContainer.setOpenid(openid);
        return wxContainerService.insert(wxContainer);
    }

    /**
     * 刪除
     * @author zhang
     * @date 2024/04/09
     **/
    @DeleteMapping("/delete")
    public Object delete(@RequestParam int id){
        return wxContainerService.delete(id);
    }

    /**
     * 更新
     * @author zhang
     * @date 2024/04/09
     **/
    @PostMapping("/update")
    public Object update(@RequestBody WxContainer wxContainer, @RequestHeader("openid") String openid){
        wxContainer.setOpenid(openid);
        return wxContainerService.update(wxContainer);
    }

    /**
     * 查询 根据主键 id 查询
     * @author zhang
     * @date 2024/04/09
     **/
    @RequestMapping("/load")
    public Object load(int id){
        return wxContainerService.load(id);
    }

    /**
     * 查询 根据主键 id 查询
     * @author zhang
     * @date 2024/04/09
     **/
    @RequestMapping("/getContainerById")
    public ApiResponse getContainerById(@RequestParam int id){
        return ApiResponse.ok(wxContainerService.load(id));
    }

    /**
     * 查询 分页查询
     * @author zhang
     * @date 2024/04/09
     **/
    @PostMapping("/pageList")
    public Object pageList(@RequestBody Map<String, Object> params, @RequestHeader("openid") String openid) {

        return wxContainerService.pageList(params, openid);
    }
}
