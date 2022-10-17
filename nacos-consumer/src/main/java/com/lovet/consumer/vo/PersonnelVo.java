package com.lovet.consumer.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
public class PersonnelVo {
    private Long id;

    private String name;

    private Integer age;

    private Long deptId;

    private String deptName;

    private String deptAddress;

    private String gender;

    private String cardId;
}
