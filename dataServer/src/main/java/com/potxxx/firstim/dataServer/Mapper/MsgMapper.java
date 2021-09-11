package com.potxxx.firstim.dataServer.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.potxxx.firstim.PO.Msg;
import com.potxxx.firstim.PO.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface MsgMapper extends BaseMapper<Msg> {

    @Select("SELECT * FROM msg WHERE msg_from = #{from} and msg_to = #{to} order by msg_cid desc limit 1")
    String findLatestCIdByFromAndTo(@Param("from") String from,@Param("to") String to);

}
