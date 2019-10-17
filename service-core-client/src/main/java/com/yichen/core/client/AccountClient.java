package com.yichen.core.client;

import com.yichen.core.dto.account.AccountDTO;
import com.yichen.core.param.account.AccountIdsParam;
import com.yichen.core.param.account.AccountParam;
import com.yichen.core.param.account.AccountUpdateParam;
import com.yichen.response.CommonResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author dengbojing
 */
@FeignClient("service-core")
public interface AccountClient {

    /**
     * 添加账号
     * @param accountParam 账号参数
     * @return {@link CommonResponse}; {@link AccountDTO}账号基本信息
     */
    @PostMapping("/add")
    CommonResponse<AccountDTO> add(@RequestBody AccountParam accountParam);

    /**
     * 更行账号信息
     * @param accountUpdateParam 账号信息
     * @return {@link CommonResponse}
     */
    @PostMapping("/update")
    CommonResponse<Integer> update(@RequestBody AccountUpdateParam accountUpdateParam);

    /**
     * 根据id删除账号
     * @param id 账号id
     * @return {@link CommonResponse}
     */
    @PostMapping("/delete/{id}")
    CommonResponse delete(@PathVariable("id") String id);

    /**
     * 批量删除账号
     * @param accountIdsParam 账号id参数
     * @return {@link CommonResponse}
     */
    @PostMapping("/batch/delete")
    CommonResponse<Integer> delete(@RequestBody AccountIdsParam accountIdsParam);

    /**
     * 挂壁账号
     * @param id 账号id
     * @return {@link CommonResponse}
     */
    @PostMapping("/close/{id}")
    CommonResponse close(@PathVariable("id") String id);

    /**
     * 批量关闭
     * @param accountIdsParam id参数
     * @return {@link CommonResponse}
     */
    @PostMapping("/batch/close")
    CommonResponse<Integer> close(@RequestBody AccountIdsParam accountIdsParam);

    /**
     * 账号详情
     * @param id 账号id
     * @return {@link CommonResponse}
     */
    @PostMapping("/detail/{id}")
    CommonResponse<AccountDTO> detail(@PathVariable("id") String id);

    /**
     * 分页获取账号列表
     * @param accountParam 账号查询参数
     * @return {@link CommonResponse}; {@link Page }分页参数; {@link AccountDTO}账号信息
     */
    @PostMapping("/page")
    CommonResponse<Page<AccountDTO>> getPage(@RequestBody AccountParam accountParam );

}
