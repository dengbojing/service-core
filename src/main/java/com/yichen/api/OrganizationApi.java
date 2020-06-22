package com.yichen.api;

import com.yichen.core.dto.org.OrganizationDTO;
import com.yichen.core.param.org.OrganizationParam;
import com.yichen.major.service.OrganizationService;
import com.yichen.response.CommonResponse;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dengbojing
 */
@RestController
@RequestMapping("/org")
public class OrganizationApi {

    private final OrganizationService orgService;


    public OrganizationApi(OrganizationService orgService) {
        this.orgService = orgService;
    }

    /**
     * 添加或者更新组织机构
     * @param param 组织机构信息
     * @return {@link CommonResponse} 是否成功(200)
     */
    @RequestMapping("/save")
    public CommonResponse<?> add(@RequestBody OrganizationParam param){
        orgService.saveOrUpdate(param);
        return CommonResponse.success();
    }

    /**
     * 删除组织机构--如果组织机构下有账户信息,则禁止删除
     * @param param 组织机构信息
     * @return {@link CommonResponse} 是否成功(200)
     */
    @RequestMapping("/delete")
    public CommonResponse<?> delete(@RequestBody OrganizationParam param){
        orgService.deleteById(param.getId());
        return CommonResponse.success();
    }

    /**
     * 查询所有机构
     * @param param 机构查询参数(机构名称模糊匹配)
     * @return 机构列表
     */
    @RequestMapping("/page")
    public CommonResponse<Page<OrganizationDTO>> page(@RequestBody OrganizationParam param){
        return CommonResponse.success(orgService.getByOrgName(param));
    }


}
