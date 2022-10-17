package com.lovet.api.feign;

import com.lovet.api.dto.Company;
import com.lovet.api.feign.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(value = "nacos-provider",configuration = FeignConfig.class)
public interface CompanyApi {

    @PostMapping("/company/getCompanyByIds")
    List<Company> getCompanyByIds(@RequestBody List<Long> ids);

}
