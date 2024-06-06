package com.zhang.storagestatisticsserve.web;

import com.zhang.storagestatisticsserve.config.ApiResponse;
import com.zhang.storagestatisticsserve.entity.WxWarehouseItems;
import com.zhang.storagestatisticsserve.service.WxWarehouseItemsService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/warehouseItems", produces = "application/json")
public class WxWarehouseItemsController {
    @Resource
    private WxWarehouseItemsService wxWarehouseItemsService;

    /**
     * 新增 & 编辑
     * @author zhang
     * @date 2024/04/22
     **/
    @PostMapping("/save")
    public ApiResponse insert(@RequestBody WxWarehouseItems wxWarehouseItems, @RequestHeader("openid") String openId){
        return wxWarehouseItemsService.insert(wxWarehouseItems, openId);
    }

    /**
     * 根据id查询详情
     * @param id
     * @return
     */
    @GetMapping("/load")
    public ApiResponse load(@RequestParam int id, @RequestHeader("openid") String openid) {
        return ApiResponse.ok(wxWarehouseItemsService.load(id, openid));
    }

    /**
     * 根据id删除（真删除）
     * @param id
     * @return
     */
    @DeleteMapping("/delete")
    public ApiResponse delete(@RequestParam int id, @RequestHeader("openid") String openid) {
        return wxWarehouseItemsService.delete(id, openid);
    }



    /**
     * 软删除物品
     * @param id
     * @return
     */
    @DeleteMapping("/deleteItem")
    public ApiResponse deleteItem(@RequestParam Integer id, @RequestHeader("openid") String openId) {

        return wxWarehouseItemsService.deleteItem(id, openId);
    }

    /**
     * 根据oepnid获取已经被用户软删除的物品列表
     * @param openid
     * @return
     */
    @GetMapping("/getRecycleBinByOpenId")
    public ApiResponse getRecycleBinByOpenId(@RequestHeader("openid") String openid) {
        return wxWarehouseItemsService.getRecycleBinByOpenId(openid);
    }

    /**
     * 根据id恢复物品状态
     * @param id
     * @return
     */
    @PostMapping("/recoverRecycleBinById")
    public ApiResponse recoverRecycleBinById(@RequestBody Map<String, Object> params, @RequestHeader("openid") String openid) {
        int id = (int) params.get("id");

        return wxWarehouseItemsService.recoverRecycleBinById(id, openid);
    }

    /**
     * 兼容共享空间，分页查询物品列表
     * @param params
     * @param openid
     * @return
     */
    @PostMapping("/getListAndRoom")
    public ApiResponse getListAndRoom(@RequestBody Map<String, Object> params, @RequestHeader("openid") String openid) {
        return wxWarehouseItemsService.getListAndRoom(params, openid);
    }
}
