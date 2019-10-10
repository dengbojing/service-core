package com.yichen.api;

import com.yichen.core.param.account.AccountIdsParam;
import com.yichen.major.service.AccountService;
import com.yichen.core.dto.account.AccountDTO;
import com.yichen.core.param.account.AccountParam;
import com.yichen.core.param.account.AccountUpdateParam;
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

    @PostMapping("/add")
    public CommonResponse<AccountDTO> add(@RequestBody AccountParam accountParam){
        return CommonResponse.success(accountService.add(accountParam).orElseGet(AccountDTO::new));
    }

    @PostMapping("/update")
    public CommonResponse<Integer> update(@RequestBody AccountUpdateParam accountUpdateParam){
        return CommonResponse.success(accountService.updateById(accountUpdateParam));
    }

    @PostMapping("/delete/{id}")
    public CommonResponse delete(@PathVariable("id") String id){
        accountService.deleteById(id);
        return CommonResponse.success();
    }

    @PostMapping("/batch/delete")
    public CommonResponse<Integer> delete(@RequestBody AccountIdsParam accountIdsParam){
        return CommonResponse.success(accountService.deleteById(accountIdsParam.getIds()));
    }

    @PostMapping("/close/{id}")
    public CommonResponse close(@PathVariable("id") String id){
        accountService.close(id);
        return CommonResponse.success();
    }

    @PostMapping("/batch/close")
    public CommonResponse<Integer> close(@RequestBody AccountIdsParam accountIdsParam){
        return CommonResponse.success(accountService.close(accountIdsParam.getIds()));
    }

    @PostMapping("/detail/{id}")
    public CommonResponse<AccountDTO> detail(@PathVariable("id") String id){
        return CommonResponse.success(accountService.getById(id).orElseGet(AccountDTO::new));
    }

    @PostMapping("/page")
    public CommonResponse<Page<AccountDTO>> getPage(@RequestBody AccountParam accountParam ){
        return CommonResponse.success(accountService.getPageByCondition(accountParam));
    }
}
