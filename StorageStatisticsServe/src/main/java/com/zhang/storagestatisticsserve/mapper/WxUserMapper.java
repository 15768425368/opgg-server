package com.zhang.storagestatisticsserve.mapper;

import com.zhang.storagestatisticsserve.entity.WxUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @description wx_user
 * @author BEJSON
 * @date 2024-04-09
 */
@Mapper
public interface WxUserMapper {

    /**
     * 新增
     * @author BEJSON
     * @date 2024/04/09
     **/
    int insert(WxUser wxUser);

    /**
     * 刪除
     * @author BEJSON
     * @date 2024/04/09
     **/
    int delete(int id);

    /**
     * 更新
     * @author BEJSON
     * @date 2024/04/09
     **/
    int update(WxUser wxUser);

    /**
     * 查询 根据主键 id 查询
     * @author BEJSON
     * @date 2024/04/09
     **/
    WxUser load(int id);

    WxUser loadByOpenId(String openid);

    /**
     * 查询 分页查询
     * @author BEJSON
     * @date 2024/04/09
     **/
    List<WxUser> pageList(@Param("offset") int offset,@Param("pageSize") int pageSize);

    /**
     * 查询 分页查询 count
     * @author BEJSON
     * @date 2024/04/09
     **/
    int pageListCount(@Param("offset") int offset,@Param("pageSize") int pageSize);

}