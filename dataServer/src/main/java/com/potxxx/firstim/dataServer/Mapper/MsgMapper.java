package com.potxxx.firstim.dataServer.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.potxxx.firstim.PO.Msg;
import com.potxxx.firstim.PO.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface MsgMapper extends BaseMapper<Msg> {

    @Select("SELECT msg_cid FROM msg WHERE msg_from = #{from} and msg_to = #{to} order by msg_cid desc limit 1 for update")
    Long findLatestCIdByFromAndTo(@Param("from") String from,@Param("to") String to);

    @Select("SELECT * FROM msg WHERE msg_to = #{userId} and msg_id > #{maxMsgId}")
    List<Msg> getNewMsgByUserIdAndMaxMsgId(@Param("userId") String userId, @Param("maxMsgId") Long maxMsgId);

    @Update("update msg set delivered = 'true' WHERE msg_to = #{userId} and msg_id < #{maxMsgId} and delivered = 'false'")
    int updateDelivered(@Param("userId") String userId, @Param("maxMsgId") Long maxMsgId);
}
