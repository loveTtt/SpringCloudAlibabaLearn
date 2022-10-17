package com.lovet.nacosprovider.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.lovet.core.util.RedisUtil;
import com.lovet.nacosprovider.entity.Company;
import com.lovet.nacosprovider.service.CompanyService;
import io.netty.util.internal.StringUtil;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/company")
public class CompanyController {

    private final CompanyService companyService;

    private final RedisUtil redisUtil;

    @GetMapping("/list/{pageIndex}/{pageSize}")
    public List<Company> list(@PathVariable Integer pageIndex, @PathVariable Integer pageSize) {
        if (redisUtil.hasKey("companyKey")) {
            String companyJson = String.valueOf(redisUtil.get("companyKey"));
            if (StringUtils.isNotBlank(companyJson)) {
                return JSONUtil.toList(companyJson, Company.class);
            }
            redisUtil.del("companyKey");
        }
        Page<Company> companyPage = new Page<>(pageIndex, pageSize);
        IPage<Company> companyIPage = companyService.page(companyPage, Wrappers.<Company>lambdaQuery().lt(Company::getId, 200));
        redisUtil.set("companyKey", JSONUtil.toJsonStr(companyIPage.getRecords()));
        return companyIPage.getRecords();
    }

    @PostMapping("getCompanyByIds")
    public List<Company> getCompanyByIds(@RequestBody List<Long> ids){
        return companyService.listByIds(ids);
    }
}
