package com.lovet.nacosprovider.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lovet.nacosprovider.entity.Company;
import com.lovet.nacosprovider.repository.CompanyRepository;
import com.lovet.nacosprovider.service.CompanyService;
import org.springframework.stereotype.Service;

@Service
public class CompanyServiceImpl extends ServiceImpl<CompanyRepository,Company> implements CompanyService {

}
