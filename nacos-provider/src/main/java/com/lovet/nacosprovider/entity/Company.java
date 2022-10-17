package com.lovet.nacosprovider.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author huangys2
 * @date 2022/9/23 16:57
 */
@Data
@TableName(value = "company")
public class Company {

	@TableId(value = "id",type = IdType.AUTO)
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
	@TableField(value = "credit_code")
	private String creditCode;

}
