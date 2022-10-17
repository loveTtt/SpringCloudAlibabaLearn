package com.lovet.consumer.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lovet.api.dto.Company;
import com.lovet.api.feign.CompanyApi;
import com.lovet.consumer.entity.Personnel;
import com.lovet.consumer.service.PersonnelService;
import com.lovet.consumer.vo.PersonnelVo;
import com.lovet.core.util.RedisUtil;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/personnel")
@AllArgsConstructor
public class PersonnelController {

    private final RedisUtil redisUtil;

    private final PersonnelService personnelService;

    private final CompanyApi companyApi;

    @GetMapping("list/{pageIndex}/{pageSize}")
    public List<PersonnelVo> list(@PathVariable Integer pageIndex, @PathVariable Integer pageSize){
        if (redisUtil.hasKey("personnelKey")) {
            String companyJson = String.valueOf(redisUtil.get("personnelKey"));
            if (StringUtils.isNotBlank(companyJson)) {
                return JSONUtil.toList(companyJson, PersonnelVo.class);
            }
            redisUtil.del("personnelKey");
        }
        Page<Personnel> companyPage = new Page<>(pageIndex, pageSize);
        IPage<Personnel> companyIPage = personnelService.page(companyPage, Wrappers.<Personnel>lambdaQuery().lt(Personnel::getId, 200));
        List<Personnel> personnelList = companyIPage.getRecords();
        List<Long> deptIds = personnelList.stream().map(Personnel::getDeptId).collect(Collectors.toList());
        List<Company> companyList = companyApi.getCompanyByIds(deptIds);
        List<PersonnelVo> personnelVos = personnelList.stream().map(personnel -> {
            PersonnelVo personnelVo = new PersonnelVo();
            BeanUtil.copyProperties(personnel,personnelVo);
            Company company = companyList.stream().filter(company1 -> personnel.getDeptId().equals(company1.getId())).findFirst().orElse(new Company());
            personnelVo.setDeptName(company.getName());
            personnelVo.setDeptAddress(company.getAddress());
            return personnelVo;
        }).collect(Collectors.toList());
        redisUtil.set("personnelKey", JSONUtil.toJsonStr(personnelVos));
        return personnelVos;
    }
}
