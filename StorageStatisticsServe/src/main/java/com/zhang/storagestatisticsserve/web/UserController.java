package com.zhang.storagestatisticsserve.web;

import com.zhang.storagestatisticsserve.config.ApiResponse;
import com.zhang.storagestatisticsserve.entity.WxUser;
import com.zhang.storagestatisticsserve.service.WxUserService;
import com.zhang.storagestatisticsserve.utils.ThrowError;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
@RestController
@RequestMapping(value = "/api/user", produces = "application/json")
public class UserController {
    @Resource
    private WxUserService wxUserService;

    /**
     * 新增
     * @author BEJSON
     * @date 2024/04/09
     **/
    @RequestMapping("/insert")
    public Object insert(WxUser wxUser){
        try {
            return wxUserService.insert(wxUser);
        } catch (ThrowError e) {
            return ApiResponse.error(e.getMessage());
        }
    }

    /**
     * 刪除
     * @author BEJSON
     * @date 2024/04/09
     **/
    @RequestMapping("/delete")
    public Object delete(int id){
        return wxUserService.delete(id);
    }

    /**
     * 更新
     * @author BEJSON
     * @date 2024/04/09
     **/
    @PostMapping("/update")
    public Object update(@RequestBody WxUser wxUser){
        return wxUserService.update(wxUser);
    }

    /**
     * 查询 根据主键 id 查询
     * @author BEJSON
     * @date 2024/04/09
     **/
    @RequestMapping("/load")
    public Object load(int id){
        return wxUserService.load(id);
    }

    /**
     * 查询 分页查询
     * @author BEJSON
     * @date 2024/04/09
     **/
    @RequestMapping("/pageList")
    public ApiResponse pageList(@RequestParam(required = false, defaultValue = "0") int offset,
                                        @RequestParam(required = false, defaultValue = "10") int pagesize) {

        return ApiResponse.ok(wxUserService.pageList(offset, pagesize));
    }
}
