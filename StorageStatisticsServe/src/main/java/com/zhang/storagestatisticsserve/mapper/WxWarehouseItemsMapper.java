package com.zhang.storagestatisticsserve.mapper;

import com.zhang.storagestatisticsserve.entity.WxWarehouseItems;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface WxWarehouseItemsMapper {
    /**
     * 新增
     * @author zhang
     * @date 2024/04/22
     **/
    int insert(WxWarehouseItems wxWarehouseItems);

    /**
     * 刪除
     * @author zhang
     * @date 2024/04/22
     **/
    int delete(int id);

    /**
     * 更新
     * @author zhang
     * @date 2024/04/22
     **/
    int update(WxWarehouseItems wxWarehouseItems);

    /**
     * 查询 根据主键 id 查询
     * @author zhang
     * @date 2024/04/22
     **/
    WxWarehouseItems load(int id);

    /**
     * 查询 分页查询
     * @author zhang
     * @date 2024/04/22
     **/
    List<WxWarehouseItems> pageList(int offset, int pagesize);

    /**
     * 查询 分页查询 count
     * @author zhang
     * @date 2024/04/22
     **/
    int pageListCount(@Param("openid") String openid, @Param("name") String name);

    /**
     * 根据openid获取用户物品树形列表
     * @param openid
     * @param name
     * @return
     */
    List<WxWarehouseItems> getListByOpenId(WxWarehouseItems wxWarehouseItems);

    /**
     * 根据pid查询列表
     * @param openid
     * @param pid
     * @return
     */
    List<WxWarehouseItems> getListByPid(@Param("openid") String openid, @Param("pid") Integer pid, @Param("state") String state);

    /**
     * 根据oepnid获取已经被用户软删除的物品列表
     * @param wxWarehouseItems
     * @return
     */
    List<WxWarehouseItems> getRecycleBinByOpenId(WxWarehouseItems wxWarehouseItems);

    /**
     * 兼容共享空间，分页查询物品列表
     * @param
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<WxWarehouseItems> getListForPidPage(@Param("state") String state, @Param("isShared") String isShared, @Param("pageNumber") int pageNumber, @Param("pageSize") int pageSize, @Param("openIds") List<String> openIds);

    /**
     * 兼容共享空间，分页查询物品列表 带name模糊查询
     * @param
     * @return
     */
    List<WxWarehouseItems> getListForPidPageName(@Param("state") String state, @Param("isShared") String isShared, @Param("openIds") List<String> openIds, @Param("name") String name);

    /**
     *
     * @param
     * @return
     */
    int getListForPidCount(@Param("state") String state, @Param("isShared") String isShared, @Param("openIds") List<String> openIds, @Param("name") String name, @Param("pid") Integer pid);
}
