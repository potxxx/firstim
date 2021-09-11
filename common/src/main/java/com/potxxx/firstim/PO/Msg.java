package com.potxxx.firstim.PO;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("msg")
public class Msg implements Serializable {
    @TableId(value = "id")
    private int id;
    @TableField(value = "msg_id")
    private String msgId;
    @TableField(value = "msg_from")
    private String msgFrom;
    @TableField(value = "msg_to")
    private String msgTo;
    @TableField(value = "msg_type")
    private String msgType;
    @TableField(value = "group_id")
    private String groupId;
    @TableField(value = "msg_cid")
    private String msgCId;
    @TableField(value = "msg_content")
    private String msgContent;
    @TableField(value = "delivered")
    private String delivered;
}
