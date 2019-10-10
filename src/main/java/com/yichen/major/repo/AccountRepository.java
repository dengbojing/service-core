package com.yichen.major.repo;

import com.yichen.major.entity.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author dengbojing
 */
public interface AccountRepository extends JpaRepository<Account, String> {

    /**
     * 根据邮箱,电话,登录名登录
     * @param loginName 登录名
     * @param phone 电话
     * @param email 邮箱
     * @return 账户
     */
    Optional<Account> findByLoginNameOrPhoneOrEmail(String loginName,String phone,String email);

    /**
     * 根据邮箱查询
     * @param email 邮箱
     * @return 客户
     */
    Optional<Account> findByEmail(String email);

    /**
     * 根据用户登录名称分页查找用户
     * @param loginName 登录名称
     * @param pageable 分页参数
     * @return 分页结果
     */
    Page<Account> findByLoginNameLike(String loginName, Pageable pageable);

}
