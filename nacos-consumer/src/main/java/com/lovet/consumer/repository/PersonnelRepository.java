package com.lovet.consumer.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lovet.consumer.entity.Personnel;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PersonnelRepository extends BaseMapper<Personnel> {
}
