package com.zhang.storagestatisticsserve.service.imp;

import com.zhang.storagestatisticsserve.config.ApiResponse;
import com.zhang.storagestatisticsserve.entity.WxJointRoom;
import com.zhang.storagestatisticsserve.entity.WxRoomUser;
import com.zhang.storagestatisticsserve.entity.WxUser;
import com.zhang.storagestatisticsserve.mapper.WxJointRoomMapper;
import com.zhang.storagestatisticsserve.mapper.WxRoomUserMapper;
import com.zhang.storagestatisticsserve.mapper.WxUserMapper;
import com.zhang.storagestatisticsserve.service.WxJointRoomService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @description 共享房间表
 * @author zhang
 * @date 2024-04-24
 */
@Service
public class WxJointRoomServiceImpl implements WxJointRoomService {
    @Resource
    private WxJointRoomMapper wxJointRoomMapper;

    @Resource
    private WxRoomUserMapper wxRoomUserMapper;

    @Resource
    private WxUserMapper wxUserMapper;


    @Override
    public Object insert(WxJointRoom wxJointRoom) {

        // valid
        if (wxJointRoom == null) {
            return ApiResponse.error("必要参数缺失");
        }

        wxJointRoomMapper.insert(wxJointRoom);
        return ApiResponse.ok();
    }


    @Override
    public Object delete(int id) {
        int ret = wxJointRoomMapper.delete(id);
        return ret>0?ApiResponse.ok():ApiResponse.error("系统错误!");
    }


    @Override
    public ApiResponse update(WxJointRoom wxJointRoom, String editUserOpenId) {
        // 判断修改者是否有权限修改共享空间信息
        if(wxJointRoom.getId() == null) {
            return ApiResponse.error("必要参数缺失");
        }

        WxJointRoom pendingEditItem = wxJointRoomMapper.load(wxJointRoom.getId());

        if(pendingEditItem.getHostUserOpenId() != editUserOpenId) {
            return ApiResponse.error("暂无权限修改共享空间!");
        }


        int ret = wxJointRoomMapper.update(wxJointRoom);
        return ret>0?ApiResponse.ok():ApiResponse.error("系统错误!");
    }


    @Override
    public WxJointRoom load(int id) {
        return wxJointRoomMapper.load(id);
    }


    @Override
    public Map<String,Object> pageList(int offset, int pagesize) {

        List<WxJointRoom> pageList = wxJointRoomMapper.pageList(offset, pagesize);
        int totalCount = wxJointRoomMapper.pageListCount(offset, pagesize);

        // result
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("pageList", pageList);
        result.put("totalCount", totalCount);

        return result;
    }

    @Override
    public ApiResponse createRoom(WxJointRoom wxJointRoom) {
        if(wxJointRoom == null) {
            return ApiResponse.error("缺少必要参数!");
        }

        if(wxJointRoom.getHostUserOpenId() == null || wxJointRoom.getHostUserOpenId() == "") {
            return ApiResponse.error("缺少必要参数!");
        }

        if(wxJointRoom.getRoomName() == null || wxJointRoom.getRoomName().isEmpty()) {
            return ApiResponse.error("缺少必要参数!");
        }

        WxRoomUser wxRoomUser = wxRoomUserMapper.loadByOpenId(wxJointRoom.getHostUserOpenId());

        if(wxRoomUser != null) {
            return ApiResponse.error("您已加入其他共享空间，不可创建!");
        }

        Date timer = new Date();
        wxJointRoom.setCreateTime(timer);
        wxJointRoom.setUpdateTime(timer);

        wxJointRoomMapper.insert(wxJointRoom);

        // 创建成功后，把自己加入共享空间
        List<WxJointRoom> userRoom = wxJointRoomMapper.getRoomListByOpenId(wxJointRoom.getHostUserOpenId());
        WxRoomUser pushNewRoomUser = new WxRoomUser();
        pushNewRoomUser.setRoomId(userRoom.get(0).getId());
        pushNewRoomUser.setUserOpenId(wxJointRoom.getHostUserOpenId());

        int boolen = 1;
        pushNewRoomUser.setIsDelete(boolen);
        pushNewRoomUser.setIsEdit(boolen);

        pushNewRoomUser.setCreateTime(timer);
        pushNewRoomUser.setUpdateTime(timer);

        wxRoomUserMapper.insert(pushNewRoomUser);

        return ApiResponse.ok();
    }

    @Override
    public ApiResponse joinRoom(Map<String, Object> params, String openId) {
        int roomId = (int) params.get("roomId");

        WxJointRoom wxJointRoom = wxJointRoomMapper.load(roomId);
        if(wxJointRoom.getHostUserOpenId().equals(openId)) {
            return ApiResponse.error("您是房主!");
        }

        WxRoomUser wxRoomUser = wxRoomUserMapper.loadByOpenIdAndId(openId, roomId);
        if(wxRoomUser != null) {
            return ApiResponse.error("您已经是共享空间成员!");
        }

        List<WxRoomUser> userRoomList = wxRoomUserMapper.getListByOpenId(openId);
        if(userRoomList.size() > 0) {
            return ApiResponse.error("您已加入其他共享空间，不可创建!");
        }

        WxRoomUser pendingPushItem = new WxRoomUser();
        pendingPushItem.setRoomId(roomId);
        pendingPushItem.setUserOpenId(openId);

        Date timer = new Date();
        pendingPushItem.setCreateTime(timer);
        pendingPushItem.setUpdateTime(timer);

        wxRoomUserMapper.insert(pendingPushItem);

        return ApiResponse.ok();
    }

    @Override
    public ApiResponse disbandRoom(Integer roomId, String openId) {
        if(roomId == null) {
            return ApiResponse.error("缺少必要参数!");
        }

        WxJointRoom wxJointRoom = wxJointRoomMapper.load(roomId);
        if(!Objects.equals(wxJointRoom.getHostUserOpenId(), openId)) {
            return ApiResponse.error("您不是房主，不能解散该空间!");
        }

        // 解散共享空间，解绑用户
        List<WxRoomUser> roomUserList = wxRoomUserMapper.getListByRoomId(roomId);
        List<Integer> roomUserListForInt = new ArrayList<>();
        for (WxRoomUser item : roomUserList) {
            roomUserListForInt.add(item.getId());
        }
        if(roomUserList.size() > 0) {
            wxRoomUserMapper.deleteItems(roomUserListForInt);
        }

        wxJointRoomMapper.delete(roomId);

        return ApiResponse.ok();
    }

    @Override
    public ApiResponse quitRoom(Integer roomId, String openId) {
        if(roomId == null) {
            return ApiResponse.error("缺少必要参数!");
        }

        WxJointRoom wxJointRoom = wxJointRoomMapper.load(roomId);
        if (wxJointRoom == null) {
            return ApiResponse.error("改共享空间已解散!");
        }
        if(Objects.equals(wxJointRoom.getHostUserOpenId(), openId)) {
            return ApiResponse.error("房主不能退出共享空间!");
        }

        WxRoomUser wxRoomUser = wxRoomUserMapper.loadByOpenIdAndId(openId, roomId);
        if(wxRoomUser == null) {
            return ApiResponse.error("你不是该空间的成员!");
        }

        wxRoomUserMapper.delete(wxRoomUser.getId());

        return ApiResponse.ok();
    }

    @Override
    public ApiResponse isRoom(String openid) {
        WxRoomUser wxRoomUser = wxRoomUserMapper.loadByOpenId(openid);

        if(wxRoomUser == null) {
            return  ApiResponse.error("false");
        }

        WxJointRoom wxJointRoom = wxJointRoomMapper.load(wxRoomUser.getRoomId());
        return ApiResponse.ok(wxJointRoom);
    }

    @Override
    public ApiResponse getRoomUser(String openid) {
        if(openid.equals("")) {
            return ApiResponse.error("缺少必要参数!");
        }

        WxRoomUser wxRoomUser = wxRoomUserMapper.loadByOpenId(openid);
        if(wxRoomUser == null) {
            return ApiResponse.error("您暂无共享空间或暂未加入共享空间!");
        }
        WxJointRoom wxJointRoom = wxJointRoomMapper.load(wxRoomUser.getRoomId());

        List<WxRoomUser> roomUserList = wxRoomUserMapper.getListByRoomId(wxRoomUser.getRoomId());
        List<Map<String, Object>> roomUserListMap = new ArrayList<>();

        for (WxRoomUser items : roomUserList) {
            Map<String, Object> itemMap = new HashMap<>();
            itemMap.put("userOpenId", items.getUserOpenId());
            itemMap.put("id", items.getId());
            itemMap.put("roomId", items.getRoomId());
            itemMap.put("isDelete", items.getIsDelete());
            itemMap.put("isEdit", items.getIsEdit());

            WxUser wxUser = wxUserMapper.loadByOpenId(items.getUserOpenId());
            if(wxUser != null){
                itemMap.put("nickName", wxUser.getNickName());
            }


            int master = 0; // 0不是房主、1是房主
            if(Objects.equals(wxJointRoom.getHostUserOpenId(), items.getUserOpenId())){
                master = 1;
            }

            itemMap.put("isMaster", master);
            roomUserListMap.add(itemMap);
        }

        return ApiResponse.ok(roomUserListMap);
    }

    @Override
    public ApiResponse removeUserRoom(int id) {
        // 查询用户房间内的信息
        WxRoomUser wxRoomUser = wxRoomUserMapper.load(id);
        if(wxRoomUser == null) {
            return ApiResponse.error("该空间查询不到用户");
        }

        int ret = wxRoomUserMapper.delete(id);
        return ret>0?ApiResponse.ok():ApiResponse.error("系统错误!");
    }

    @Override
    public ApiResponse editMemberPer(WxRoomUser wxRoomUser) {
        if(wxRoomUser.getId() == null) {
            return ApiResponse.error("缺少参数");
        }

        if(wxRoomUser.getIsEdit() == null && wxRoomUser.getIsDelete() == null) {
            return ApiResponse.error("缺少参数");
        }

        wxRoomUser.setUpdateTime(new Date());

        int ret = wxRoomUserMapper.update(wxRoomUser);
        return ret>0?ApiResponse.ok():ApiResponse.error("系统错误!");
    }
}
