package com.lovet.consumer.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lovet.consumer.entity.Personnel;
import com.lovet.consumer.repository.PersonnelRepository;
import com.lovet.consumer.service.PersonnelService;
import org.springframework.stereotype.Service;

@Service
public class PersonnelServiceImpl extends ServiceImpl<PersonnelRepository, Personnel> implements PersonnelService {
}
