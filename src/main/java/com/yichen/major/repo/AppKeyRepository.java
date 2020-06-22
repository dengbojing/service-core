package com.yichen.major.repo;

import com.yichen.major.entity.Account;
import com.yichen.major.entity.AppKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author dengbojing
 */
@Repository
public interface AppKeyRepository extends JpaRepository<AppKey, String> {
    /**
     * 根据密钥查询对象
     * @param key 密钥
     * @return 密钥对
     */
    Optional<AppKey> findByAppKey(String key);

    /**
     * 根据账户id获取密钥对
     * @param account 账户
     * @return 迷药对
     */
    Optional<AppKey> findByAccount(Account account);

    /**
     * 根据账户id删除密钥对
     * @param account 账户
     */
    void deleteByAccount(Account account);
}
