package com.zhang.storagestatisticsserve.web;

import com.zhang.storagestatisticsserve.config.ApiResponse;
import com.zhang.storagestatisticsserve.entity.WxJointRoom;
import com.zhang.storagestatisticsserve.entity.WxRoomUser;
import com.zhang.storagestatisticsserve.service.WxJointRoomService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/room", produces = "application/json")
public class WxJointRoomController {
    @Resource
    private WxJointRoomService wxJointRoomService;

    /**
     * 创建房间
     * @param wxJointRoom
     * @return
     */
    @PostMapping("/createRoom")
    private ApiResponse createRoom(@RequestBody WxJointRoom wxJointRoom, @RequestHeader("openid") String openId) {
        wxJointRoom.setHostUserOpenId(openId);
        return wxJointRoomService.createRoom(wxJointRoom);
    }

    /**
     * 编辑共享空间
     * @param wxJointRoom
     * @param openId
     * @return
     */
    @PostMapping("/update")
    public ApiResponse update(@RequestBody WxJointRoom wxJointRoom,@RequestHeader("openid") String openId) {
        return wxJointRoomService.update(wxJointRoom, openId);
    }

    /**
     * 加入共享空间
     * @param params
     * @param openId
     * @return
     */
    @PostMapping("/joinRoom")
    public ApiResponse joinRoom(@RequestBody Map<String, Object> params,@RequestHeader("openid") String openId) {
        return wxJointRoomService.joinRoom(params, openId);
    }

    /**
     * 解散共享空间
     * @param roomId
     * @param openId
     * @return
     */
    @DeleteMapping("/disbandRoom")
    public ApiResponse disbandRoom(@RequestParam("roomId") Integer roomId, @RequestHeader("openid") String openId) {
        return wxJointRoomService.disbandRoom(roomId, openId);
    }

    /**
     * 退出共享空间
     * @param wxRoomUser
     * @param openId
     * @return
     */
    @PostMapping("/quitRoom")
    public ApiResponse quitRoom(@RequestBody WxRoomUser wxRoomUser, @RequestHeader("openid") String openId) {
        return wxJointRoomService.quitRoom(wxRoomUser.getRoomId(), openId);
    }

    /**
     * 查询是否有共享空间
     */
    @GetMapping("/isRoom")
    public ApiResponse isRoom(@RequestHeader("openid") String openid) {
        return wxJointRoomService.isRoom(openid);
    }

    /**
     * 获取共享空间的用户列表
     * @param openid
     * @return
     */
    @GetMapping("/getRoomUser")
    public ApiResponse getRoomUser(@RequestHeader("openid") String openid) {
        return wxJointRoomService.getRoomUser(openid);
    }

    /**
     * 将成员移除共享空间
     * @param wxRoomUser
     * @param openId
     * @return
     */
    @PostMapping("/removeUserRoom")
    public ApiResponse removeUserRoom(@RequestBody WxRoomUser wxRoomUser, @RequestHeader("openid") String openId) {
        return wxJointRoomService.removeUserRoom(wxRoomUser.getId());
    }

    /**
     * 编辑成员权限
     * @param wxRoomUser
     * @param openId
     * @return
     */
    @PostMapping("/editMemberPer")
    public ApiResponse editMemberPer(@RequestBody WxRoomUser wxRoomUser, @RequestHeader("openid") String openId) {
        return wxJointRoomService.editMemberPer(wxRoomUser);
    }
}
