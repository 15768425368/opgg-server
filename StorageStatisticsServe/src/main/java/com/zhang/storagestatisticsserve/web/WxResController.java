package com.zhang.storagestatisticsserve.web;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.zhang.storagestatisticsserve.config.ApiResponse;
import com.zhang.storagestatisticsserve.entity.WxRes;
import com.zhang.storagestatisticsserve.service.WxResService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/res", produces = "application/json")
public class WxResController {
    @Resource
    private WxResService wxResService;

    /**
     * 新增
     * @author zhang
     * @date 2024/04/10
     **/
    @PostMapping("/insert")
    public ApiResponse insert(@RequestBody WxRes wxRes,@RequestHeader("openid") String openid ){
        return wxResService.insert(wxRes, openid);
    }

    /**
     * 刪除
     * @author zhang
     * @date 2024/04/10
     **/
    @DeleteMapping("/delete")
    public Object delete(int id){
        return wxResService.delete(id);
    }

    /**
     * 更新
     * @author zhang
     * @date 2024/04/10
     **/
    @PostMapping("/update")
    public Object update(@RequestBody WxRes wxRes){
        return wxResService.update(wxRes);
    }

    /**
     * 查询 根据主键 id 查询
     * @author zhang
     * @date 2024/04/10
     **/
    @RequestMapping("/load")
    public Object load(int id){
        return wxResService.load(id);
    }

    /**
     * 查询 根据主键 id 查询
     * @author zhang
     * @date 2024/04/10
     **/
    @GetMapping("/getResById")
    public ApiResponse getResById(@RequestParam Integer id) {
        return ApiResponse.ok(wxResService.load(id));
    }

    /**
     * 查询 分页查询
     * @author zhang
     * @date 2024/04/10
     **/
    @RequestMapping("/pageList")
    public Map<String, Object> pageList(@RequestParam(required = false, defaultValue = "0") int offset,
                                        @RequestParam(required = false, defaultValue = "10") int pagesize) {
        return wxResService.pageList(offset, pagesize);
    }

    /**
     * 根据容器id查询物件列表
     * @param id
     * @return
     */
    @GetMapping("/getResByContainerId")
    public ApiResponse getResByContainerId(@RequestParam Integer id) {
        return wxResService.getResByContainerId(id);
    }

    /**
     * 获取res合并Container树形列表
     * @param params
     * @return
     */
    @PostMapping("/getResByNameTree")
    public ApiResponse getResByNameTree(@RequestBody Map<String, Object> params, @RequestHeader("openid") String openid) {
        return wxResService.getResByNameTree(params, openid);
    }
}
