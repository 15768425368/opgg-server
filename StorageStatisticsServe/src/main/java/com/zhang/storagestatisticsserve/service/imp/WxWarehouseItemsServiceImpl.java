package com.zhang.storagestatisticsserve.service.imp;

import com.zhang.storagestatisticsserve.config.ApiResponse;
import com.zhang.storagestatisticsserve.entity.WxRoomUser;
import com.zhang.storagestatisticsserve.entity.WxUser;
import com.zhang.storagestatisticsserve.entity.WxWarehouseItems;
import com.zhang.storagestatisticsserve.mapper.WxRoomUserMapper;
import com.zhang.storagestatisticsserve.mapper.WxUserMapper;
import com.zhang.storagestatisticsserve.mapper.WxWarehouseItemsMapper;
import com.zhang.storagestatisticsserve.service.UploadService;
import com.zhang.storagestatisticsserve.service.WxWarehouseItemsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class WxWarehouseItemsServiceImpl implements WxWarehouseItemsService {
    @Resource
    private WxWarehouseItemsMapper wxWarehouseItemsMapper;

    @Resource
    private WxUserMapper wxUserMapper;

    @Resource
    private WxRoomUserMapper wxRoomUserMapper;

    @Resource
    private UploadService uploadService;

    @Override
    public ApiResponse insert(WxWarehouseItems wxWarehouseItems, String openId) {

        // valid
        if (wxWarehouseItems == null) {
            return ApiResponse.error("必要参数缺失");
        }

        if(openId.isEmpty() || openId == null) {
            return ApiResponse.error("缺少openId");
        }

        Date timer = new Date();
        wxWarehouseItems.setCreateTime(timer);
        wxWarehouseItems.setUpdateTime(timer);
        wxWarehouseItems.setOpenId(openId);

        if(wxWarehouseItems.getId() == null) {
            // 添加模式
            wxWarehouseItems.setCreateUser(openId);
            wxWarehouseItemsMapper.insert(wxWarehouseItems);

            return ApiResponse.ok();
        } else {
            WxWarehouseItems update = new WxWarehouseItems();
            update.setId(wxWarehouseItems.getId());
            update.setName(wxWarehouseItems.getName());
            update.setCover(wxWarehouseItems.getCover());
            update.setIsShared(wxWarehouseItems.getIsShared());
            update.setPid(wxWarehouseItems.getPid());
            update.setState(wxWarehouseItems.getState());
            update.setPlace(wxWarehouseItems.getPlace());
            update.setUpdateTime(new Date());

            int ret = wxWarehouseItemsMapper.update(update);
            return ret>0?ApiResponse.ok():ApiResponse.error("系统错误!");
        }
    }


    @Override
    public ApiResponse delete(int id, String openid) {
        // 根据id获取物品表信息
        WxWarehouseItems wxWarehouseItems = wxWarehouseItemsMapper.load(id);
        // 判断物品是否存在表里
        if(wxWarehouseItems == null) {
            return ApiResponse.error("系统错误!");
        }
        // 根据桶名称、文件名称删除oss文件
        uploadService.deleteImage("zhangdaxingtest", wxWarehouseItems.getCover());
        // 根据id删除物品表数据
        int ret = wxWarehouseItemsMapper.delete(id);
        // 查询是否有子级，全部删除
        List<WxWarehouseItems> childList = wxWarehouseItemsMapper.getListByPid(openid,id, "1");
        for (WxWarehouseItems childItem : childList) {
            // 循环删除子级
            wxWarehouseItemsMapper.delete(childItem.getId());
            // 删除子集oss文件
            uploadService.deleteImage("zhangdaxingtest", childItem.getCover());
        }
        // result
        return ret>0?ApiResponse.ok():ApiResponse.error("系统错误!");
    }


    @Override
    public Object update(WxWarehouseItems wxWarehouseItems) {
        int ret = wxWarehouseItemsMapper.update(wxWarehouseItems);
        return ret>0?ApiResponse.ok():ApiResponse.error("系统错误!");
    }


    @Override
    public WxWarehouseItems load(int id, String openid) {
        WxWarehouseItems wxWarehouseItems = wxWarehouseItemsMapper.load(id);
        WxRoomUser wxRoomUser = wxRoomUserMapper.loadByOpenId(openid);

        if(wxRoomUser != null) {
            wxWarehouseItems.setIsDelete(wxRoomUser.getIsDelete());
            wxWarehouseItems.setIsEdit(wxRoomUser.getIsEdit());
        }
        return wxWarehouseItems;
    }


    @Override
    public Map<String,Object> pageList(int offset, int pagesize) {

        List<WxWarehouseItems> pageList = wxWarehouseItemsMapper.pageList(offset, pagesize);
        int totalCount = 0;

        // result
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("pageList", pageList);
        result.put("totalCount", totalCount);

        return result;
    }

    @Override
    public ApiResponse deleteItem(Integer id, String openId) {
        if(id == null) {
            return ApiResponse.error("缺少Id参数!");
        }
        WxWarehouseItems wxWarehouseItems = new WxWarehouseItems();
        wxWarehouseItems.setId(id);
        wxWarehouseItems.setOpenId(openId);
        wxWarehouseItems.setState("1"); // 软删除
        wxWarehouseItemsMapper.update(wxWarehouseItems); // 关闭父级状态

        // 查询子级
        List<WxWarehouseItems> child = wxWarehouseItemsMapper.getListByPid(openId, id, "0");

        if (child.size() > 0) {
            for (WxWarehouseItems item : child) {
                item.setState("1");
                wxWarehouseItemsMapper.update(item); // 关闭父级状态
            }
        }

        return ApiResponse.ok();
    }

    @Override
    public ApiResponse getRecycleBinByOpenId(String openid) {
        WxWarehouseItems wxWarehouseItems = new WxWarehouseItems();
        wxWarehouseItems.setOpenId(openid);
        wxWarehouseItems.setState("1");

        List<WxWarehouseItems> list = wxWarehouseItemsMapper.getRecycleBinByOpenId(wxWarehouseItems);
        for (WxWarehouseItems items : list) {
            items.setCreateUser(queryUserNickNameByOpenId(items.getOpenId()));
            if(items.getPid() != null) {
                // 有父级
                WxWarehouseItems pItem = wxWarehouseItemsMapper.load(items.getPid());
                if(pItem != null) {
                    items.setPidName(pItem.getName());
                }
            }
        }
        return ApiResponse.ok(list);
    }

    @Override
    public ApiResponse recoverRecycleBinById(Integer id, String openid) {
        WxWarehouseItems wxWarehouseItems = wxWarehouseItemsMapper.load(id);

        if(wxWarehouseItems.getPid() != null) {
            // 有父级，把父级状态也同步恢复
            WxWarehouseItems pItem = wxWarehouseItemsMapper.load(wxWarehouseItems.getPid());
            if(pItem != null) {
                pItem.setState("0");
                wxWarehouseItemsMapper.update(pItem);
            }

        } else {
            // 没父级，查询是否有子级，同步恢复子级状态
            List<WxWarehouseItems> list = wxWarehouseItemsMapper.getListByPid(openid, wxWarehouseItems.getId(), "1");
            for (WxWarehouseItems items : list) {
                items.setState("0");
                wxWarehouseItemsMapper.update(items);
            }
        }

        wxWarehouseItems.setState("0");
        wxWarehouseItemsMapper.update(wxWarehouseItems);

        return ApiResponse.ok();
    }

    @Override
    public ApiResponse getListAndRoom(Map<String, Object> params, String openid){
        String name = (String) params.get("name"); // 物品名称
        Integer pageNumber = (int) params.get("pageNumber"); // 页码
        Integer pageSize = (int) params.get("pageSize"); // 页数
        String type = (String) params.get("type"); // 查询类型：1查自己、2查共享空间

        if (name == null) {
            name = "";
        }

        if (pageNumber == null) {
            pageNumber = 1;
        }
        if (pageSize == null) {
            pageSize = 20;
        }
        pageNumber = pageNumber -1;

        if(type == null) {
            type = "1";
        }


        List<WxWarehouseItems> resultList = new ArrayList<>();
        List<Map<String, Object>> resultListTree = new ArrayList<>();

        // 查询共享空间物品列表

        // 获取用户共享空间信息
        List<String> openIds = new ArrayList<>();
        // 获取用户在房间内的信息
        WxRoomUser wxRoomUser = wxRoomUserMapper.loadByOpenId(openid);
        String isShared = "";

        if(type.equals("1")) {
            openIds.add(openid);
        }else {
            if(wxRoomUser == null) {
                return ApiResponse.error("您暂未加入如何共享空间!");
            }
            isShared = "0";
            // 根据用户房间内的信息获取房间成员
            List<WxRoomUser> roomUserList = wxRoomUserMapper.getListByRoomId(wxRoomUser.getRoomId());
            if(roomUserList.size() > 0) {
                for (WxRoomUser item : roomUserList) {
                    openIds.add(item.getUserOpenId());
                }
            }else {
                openIds.add(openid);
            }
        }
        Integer pid = null;
        int total = 0;
        if(name.equals("")) {
            resultList = wxWarehouseItemsMapper.getListForPidPage("0", isShared, pageNumber, pageSize, openIds);
            for (WxWarehouseItems wxWarehouseItems : resultList) {
                Map<String, Object> pushObj = new HashMap<>();
                pushObj.put("name", wxWarehouseItems.getName());
                pushObj.put("cover", wxWarehouseItems.getCover());
                pushObj.put("place", wxWarehouseItems.getPlace());
                pushObj.put("createUser", queryUserNickNameByOpenId(wxWarehouseItems.getOpenId()));
                pushObj.put("id", wxWarehouseItems.getId());
                pushObj.put("pid", wxWarehouseItems.getPid());
                if(wxRoomUser != null){
                    pushObj.put("isDel", wxRoomUser.getIsDelete());
                    pushObj.put("isEdit", wxRoomUser.getIsEdit());
                }

                pushObj.put("openId", wxWarehouseItems.getOpenId());

                List<WxWarehouseItems> childList = wxWarehouseItemsMapper.getListByPid("", wxWarehouseItems.getId(), "0");
                List<WxWarehouseItems> newChildList = new ArrayList<>();
                for (WxWarehouseItems childItem : childList) {
                    childItem.setPidName(wxWarehouseItems.getName());
                    if(wxRoomUser != null){
                        childItem.setIsEdit(wxRoomUser.getIsEdit());
                        childItem.setIsDelete(wxRoomUser.getIsDelete());
                    }

                    childItem.setCreateUser(queryUserNickNameByOpenId(childItem.getOpenId()));

                    if(type.equals("1")) {
                        if(Objects.equals(childItem.getOpenId(), openid)){
                            newChildList.add(childItem);
                        }
                    }else if(type.equals("2")) {
                        newChildList.add(childItem);
                    }
                }
                pushObj.put("child", newChildList);

                resultListTree.add(pushObj);
            }
            total = wxWarehouseItemsMapper.getListForPidCount("0", isShared, openIds, name, pid);
        }else {
            pid = 1;
            resultListTree = buildTreeByName(wxWarehouseItemsMapper.getListForPidPageName("0", isShared, openIds, name), wxRoomUser);
            total = -1;
        }




        Map<String, Object> resultBody = new HashMap<>();
        resultBody.put("list", resultListTree);
        resultBody.put("total", total);
        return ApiResponse.ok(resultBody);
    }

    public List<Map<String, Object>> buildTreeByName(List<WxWarehouseItems> nodeList, WxRoomUser wxRoomUser) {
        List<Map<String, Object>> newList = new ArrayList<>();

        for (WxWarehouseItems item : nodeList) {
            if(item.getPid() == null) {
                    Map<String, Object> newPid = new HashMap<>();
                newPid.put("id", item.getId());
                newPid.put("name", item.getName());
                newPid.put("cover", item.getCover());
                newPid.put("state", item.getState());
                newPid.put("pid", item.getPid());
                newPid.put("isShared", item.getIsShared());
                if(wxRoomUser != null) {
                    newPid.put("isDel", wxRoomUser.getIsDelete());
                    newPid.put("isEdit", wxRoomUser.getIsEdit());
                }
                newPid.put("place", item.getPlace());
                newPid.put("createUser", queryUserNickNameByOpenId(item.getCreateUser()));
                newPid.put("createTime", item.getCreateTime());
                newPid.put("child", new ArrayList<>());

                newList.add(newPid);
            }

            if(item.getPid() != null) {
                int isP = 1;
                for (Map<String, Object> child : newList) {
                    if(child.get("id") == item.getPid()) {
                        // 已经有父级
                        List<Map<String, Object>> list = (List<Map<String, Object>>) child.get("child");
                        Map<String, Object> newPid = new HashMap<>();
                        newPid.put("id", item.getId());
                        newPid.put("name", item.getName());
                        newPid.put("cover", item.getCover());
                        newPid.put("state", item.getState());
                        newPid.put("pid", item.getPid());
                        newPid.put("isShared", item.getIsShared());
                        newPid.put("pidName", child.get("name"));
                        newPid.put("place", item.getPlace());
                        if(wxRoomUser != null) {
                            newPid.put("isDel", wxRoomUser.getIsDelete());
                            newPid.put("isEdit", wxRoomUser.getIsEdit());
                        }
                        newPid.put("createUser", queryUserNickNameByOpenId(item.getCreateUser()));
                        newPid.put("createTime", item.getCreateTime());
                        list.add(newPid);
                        child.put("child", list);
                        isP = 2;
                    }
                }

                if(isP == 1) {
                    // 查询父级， 没有父级数据库查
                    WxWarehouseItems pData = wxWarehouseItemsMapper.load(item.getPid());
                    List<Map<String, Object>> list = new ArrayList<>();

                    Map<String, Object> childData = new HashMap<>();
                    childData.put("id", item.getId());
                    childData.put("name", item.getName());
                    childData.put("cover", item.getCover());
                    childData.put("state", item.getState());
                    childData.put("pid", item.getPid());
                    childData.put("pidName", pData.getName());
                    childData.put("isShared", item.getIsShared());
                    childData.put("place", item.getPlace());
                    if(wxRoomUser != null) {
                        childData.put("isDel", wxRoomUser.getIsDelete());
                        childData.put("isEdit", wxRoomUser.getIsEdit());
                    }
                    childData.put("createUser", queryUserNickNameByOpenId(item.getCreateUser()));
                    childData.put("createTime", item.getCreateTime());
                    list.add(childData);


                    Map<String, Object> newPid = new HashMap<>();
                    newPid.put("id", pData.getId());
                    newPid.put("name", pData.getName());
                    newPid.put("cover", pData.getCover());
                    newPid.put("state", pData.getState());
                    newPid.put("pid", pData.getPid());
                    newPid.put("isShared", pData.getIsShared());
                    newPid.put("place", pData.getPlace());
                    newPid.put("createUser", queryUserNickNameByOpenId(pData.getCreateUser()));
                    newPid.put("createTime", pData.getCreateTime());
                    if(wxRoomUser != null) {
                        newPid.put("isDel", wxRoomUser.getIsDelete());
                        newPid.put("isEdit", wxRoomUser.getIsEdit());
                    }
                    newPid.put("child", list);

                    newList.add(newPid);
                }
            }
        }

        return newList;
    }

    public String queryUserNickNameByOpenId(String openId) {
        WxUser wxUser = wxUserMapper.loadByOpenId(openId);
        if(wxUser == null) {
            return "未知";
        }
        return wxUser.getNickName();
    }

}
