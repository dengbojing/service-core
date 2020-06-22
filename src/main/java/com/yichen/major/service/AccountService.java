package com.yichen.major.service;

import com.yichen.core.dto.account.AccountDTO;
import com.yichen.core.dto.account.AppkeyDTO;
import com.yichen.core.param.account.AccountParam;
import com.yichen.core.param.account.AccountUpdateParam;
import com.yichen.response.CommonResponse;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

/**
 * @author dengbojing
 */
public interface AccountService {

    /**
     * 根据条件分页查询
     * @param accountParam 查询阐述
     * @return 分页结果
     */
    Page<AccountDTO> getPageByCondition(AccountParam accountParam);

    /**
     * 根据id查询账户信息
     * @param id 账户id
     * @return 账户信息
     */
    Optional<AccountDTO> getById(String id);

    /**
     * 根据id删除账户
     * @param id 账户id
     * @return 删除行数
     */
    Integer deleteById(String id);

    /**
     * 根据id删除账户
     * @param ids 账户id集合
     * @return 删除行数
     */
    Integer deleteById(List<String> ids);

    /**
     * 根据id更新账户信息
     * @param accountUpdateParam 待更新信息
     * @return 受影响行数
     */
    Integer updateById(AccountUpdateParam accountUpdateParam);

    /**
     * 关闭账户
     * @param id 账户id参数
     * @return 受影响行数
     */
    Integer close(String id);

    /**
     * 关闭账户
     * @param id 账户id参数
     * @return 受影响行数
     */
    Integer close(List<String> id);

    /**
     * 添加账户
     * @param accountParam 账户参数
     * @return 账户信息
     */
    Optional<AccountDTO> add(AccountParam accountParam);

    /**
     * 减少size账户使用次数
     * @param userId 用户id
     * @param size 减少次数
     * @return 减少次数
     */
    Integer decrease(String userId, int size);

    /**
     * 检查用户是否拥有使用权限
     * @param userId 用户id
     */
    void check(String userId);

    /**
     * 获取密钥对
     * @param accountParam 账户星系
     * @return 密钥队
     */
    Optional<AppkeyDTO> getKey(AccountParam accountParam);

    /**
     * 生成新的密钥对
     * @param accountParam 账户信息
     * @return 密钥对
     */
    Optional<AppkeyDTO> generateKey(AccountParam accountParam);
}
