package com.zhang.storagestatisticsserve.web;

import com.zhang.storagestatisticsserve.entity.WxFeedback;
import com.zhang.storagestatisticsserve.service.WxFeedbackService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/feedback", produces = "application/json")
public class WxFeedbackController {
    @Resource
    private WxFeedbackService wxFeedbackService;

    /**
     * 新增
     * @author zhang
     * @date 2024/04/18
     **/
    @PostMapping("/insert")
    public Object insert(@RequestBody WxFeedback wxFeedback, @RequestHeader("openId") String openId){
        return wxFeedbackService.insert(wxFeedback, openId);
    }

    /**
     * 刪除
     * @author zhang
     * @date 2024/04/18
     **/
    @RequestMapping("/delete")
    public Object delete(int id){
        return wxFeedbackService.delete(id);
    }

    /**
     * 更新
     * @author zhang
     * @date 2024/04/18
     **/
    @RequestMapping("/update")
    public Object update(WxFeedback wxFeedback){
        return wxFeedbackService.update(wxFeedback);
    }

    /**
     * 查询 根据主键 id 查询
     * @author zhang
     * @date 2024/04/18
     **/
    @RequestMapping("/load")
    public Object load(int id){
        return wxFeedbackService.load(id);
    }

    /**
     * 查询 分页查询
     * @author zhang
     * @date 2024/04/18
     **/
    @RequestMapping("/pageList")
    public Map<String, Object> pageList(@RequestParam(required = false, defaultValue = "0") int offset,
                                        @RequestParam(required = false, defaultValue = "10") int pagesize) {
        return wxFeedbackService.pageList(offset, pagesize);
    }
}
