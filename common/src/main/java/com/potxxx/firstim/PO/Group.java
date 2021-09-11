package com.potxxx.firstim.PO;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("chatgroup")
public class Group {

    @TableId(value = "id")
    private int id;

    @TableField(value = "group_id")
    private String groupId;

    @TableField(value = "state")
    private String state;

}
