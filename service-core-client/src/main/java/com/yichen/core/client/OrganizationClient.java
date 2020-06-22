package com.yichen.core.client;

import com.yichen.core.dto.org.OrganizationDTO;
import com.yichen.core.param.org.OrganizationParam;
import com.yichen.response.CommonResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author dengbojing
 */
@FeignClient("service-core")
public interface OrganizationClient {

    /**
     * 添加或者更新组织机构
     * @param param 组织机构信息
     * @return {@link CommonResponse} 是否成功(200)
     */
    @RequestMapping("/save")
    CommonResponse<?> add(@RequestBody OrganizationParam param);

    /**
     * 删除组织机构--如果组织机构下有账户信息,则禁止删除
     * @param param 组织机构信息
     * @return {@link CommonResponse} 是否成功(200)
     */
    @RequestMapping("/delete")
    CommonResponse<?> delete(@RequestBody OrganizationParam param);

    /**
     * 查询所有机构
     * @param param 机构查询参数(机构名称模糊匹配)
     * @return 机构列表
     */
    @RequestMapping("/page")
    CommonResponse<Page<OrganizationDTO>> page(@RequestBody OrganizationParam param);
}
