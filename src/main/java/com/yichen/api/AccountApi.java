package com.yichen.api;

import com.yichen.core.dto.account.AppkeyDTO;
import com.yichen.core.param.account.AccountIdsParam;
import com.yichen.exception.BusinessException;
import com.yichen.major.service.AccountService;
import com.yichen.core.dto.account.AccountDTO;
import com.yichen.core.param.account.AccountParam;
import com.yichen.core.param.account.AccountUpdateParam;
import com.yichen.request.RequestHolder;
import com.yichen.response.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 * @author dengbojing
 */

@RestController
@RequestMapping("/account")
public class AccountApi {

    private final AccountService accountService;

    @Autowired
    public AccountApi(AccountService accountService) {
        this.accountService = accountService;
    }

    /**
     * 查询当前登录用户信息
     * @return 用户信息
     */
    @PostMapping("/info")
    public CommonResponse<AccountDTO> info(){
        String userId = RequestHolder.info().getUser().getUserId();
        return CommonResponse.success(accountService.getById(userId).orElseGet(AccountDTO::new));
    }

    /**
     * 添加用户
     * @param accountParam 待添加用户信息
     * @return 添加完成后用户信息
     */
    @PostMapping("/add")
    public CommonResponse<AccountDTO> add(@RequestBody AccountParam accountParam){
        return CommonResponse.success(accountService.add(accountParam).orElseGet(AccountDTO::new));
    }

    /**
     * 更新用户信息
     * @param accountUpdateParam 用户信息
     * @return 是否成功
     */
    @PostMapping("/update")
    public CommonResponse<?> update(@RequestBody AccountUpdateParam accountUpdateParam){
        return CommonResponse.success(accountService.updateById(accountUpdateParam));
    }

    /**
     * 删除用户
     * @param id 用户id
     * @return 通用返回格式 @see {@link CommonResponse}
     */
    @PostMapping("/delete/{id}")
    public CommonResponse<?> delete(@PathVariable("id") String id){
        accountService.deleteById(id);
        return CommonResponse.success();
    }

    /**
     * 批量删除用户
     * @param accountIdsParam 用户id集合参数
     * @return 通用返回格式 @see {@link CommonResponse}
     */
    @PostMapping("/batch/delete")
    public CommonResponse<?> delete(@RequestBody AccountIdsParam accountIdsParam){
        return CommonResponse.success(accountService.deleteById(accountIdsParam.getIds()));
    }

    /**
     * 关闭用户
     * @param id 用户id
     * @return 通用返回格式 @see {@link CommonResponse}
     */
    @PostMapping("/close/{id}")
    public CommonResponse<?> close(@PathVariable("id") String id){
        accountService.close(id);
        return CommonResponse.success();
    }

    /**
     * 批量关闭用户
     * @param accountIdsParam 用户
     * @return 通用返回格式 @see {@link CommonResponse}
     */
    @PostMapping("/batch/close")
    public CommonResponse<?> close(@RequestBody AccountIdsParam accountIdsParam){
        return CommonResponse.success(accountService.close(accountIdsParam.getIds()));
    }

    /**
     * 根据用户id查询用户
     * @param id 用户id
     * @return 账户信息
     */
    @PostMapping("/detail/{id}")
    public CommonResponse<AccountDTO> detail(@PathVariable("id") String id){
        return CommonResponse.success(accountService.getById(id).orElseGet(AccountDTO::new));
    }

    /**
     * 分页查询用户列表
     * @param accountParam 分页参数
     * @return 分页列表
     */
    @PostMapping("/page")
    public CommonResponse<Page<AccountDTO>> getPage(@RequestBody AccountParam accountParam ){
        return CommonResponse.success(accountService.getPageByCondition(accountParam));
    }

    /**
     * 密钥生成
     * @param accountParam 账户信息
     * @return 密钥对
     */
    @PostMapping("/key/get")
    public CommonResponse<AppkeyDTO> getKey(@RequestBody AccountParam accountParam){
        return CommonResponse.success(accountService.getKey(accountParam).orElseThrow(() -> new BusinessException("获取失败!")));
    }

    /**
     * 生成新的密钥对
     * @param accountParam 账户信息
     * @return 密钥对
     */
    @PostMapping("/key/generate")
    public CommonResponse<AppkeyDTO> generateKey(@RequestBody AccountParam accountParam){
        return CommonResponse.success(accountService.generateKey(accountParam).orElseThrow(() -> new BusinessException("生成失败!")));
    }

}
