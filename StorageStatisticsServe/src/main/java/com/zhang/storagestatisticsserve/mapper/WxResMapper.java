package com.zhang.storagestatisticsserve.mapper;

import com.zhang.storagestatisticsserve.entity.WxRes;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @description wx_res
 * @author zhang
 * @date 2024-04-10
 */
@Mapper
public interface WxResMapper {

    /**
     * 新增
     * @author zhang
     * @date 2024/04/10
     **/
    int insert(WxRes wxRes);

    /**
     * 刪除
     * @author zhang
     * @date 2024/04/10
     **/
    int delete(int id);

    /**
     * 更新
     * @author zhang
     * @date 2024/04/10
     **/
    int update(WxRes wxRes);

    /**
     * 查询 根据主键 id 查询
     * @author zhang
     * @date 2024/04/10
     **/
    WxRes load(int id);

    /**
     * 查询 分页查询
     * @author zhang
     * @date 2024/04/10
     **/
    List<WxRes> pageList(int offset, int pagesize);

    /**
     * 查询 分页查询 count
     * @author zhang
     * @date 2024/04/10
     **/
    int pageListCount(int offset,int pagesize);

    /**
     * 根据用户openid获取用户所属的物件列表
     * @param openid
     * @return
     */
    List<WxRes> getResByOpenid(@Param("openid") String openid,@Param("name") String name);

    /**
     * 根据容器id获取物件列表
     * @param id
     * @return
     */
    List<WxRes> getResByContainerId(int id);
}
