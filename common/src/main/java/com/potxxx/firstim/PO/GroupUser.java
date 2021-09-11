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
@TableName("group_user")
public class GroupUser implements Serializable {

    @TableId(value = "id")
    private int id;
    @TableField(value = "group_id")
    private String groupId;

    @TableField(value = "use_id")
    private String useId;

}
