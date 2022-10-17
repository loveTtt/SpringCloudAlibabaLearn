package com.lovet.consumer.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author hys
 */
@Data
@TableName(value = "personnel")
public class Personnel {
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    private String name;

    private Integer age;

    @TableField(value = "dept_id")
    private Long deptId;

    private String gender;

    private String cardId;
}
