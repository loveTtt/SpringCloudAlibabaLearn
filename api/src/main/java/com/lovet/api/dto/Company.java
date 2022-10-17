package com.lovet.api.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
public class Company {
    private Long id;

    /**
     * 公司名称
     */
    private String name;
    /**
     * 公司地址
     */
    private String address;
    /**
     * 社会统一信用代码
     */
    private String creditCode;
}
