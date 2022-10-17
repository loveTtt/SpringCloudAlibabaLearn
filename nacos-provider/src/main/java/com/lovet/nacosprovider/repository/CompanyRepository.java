package com.lovet.nacosprovider.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lovet.nacosprovider.entity.Company;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CompanyRepository extends BaseMapper<Company> {
}
