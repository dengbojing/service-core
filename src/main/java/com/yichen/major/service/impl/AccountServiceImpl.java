package com.yichen.major.service.impl;

import com.yichen.exception.BusinessException;
import com.yichen.major.entity.Account;
import com.yichen.major.entity.Organization;
import com.yichen.major.repo.AccountRepository;
import com.yichen.major.repo.OrganizationRepository;
import com.yichen.major.service.AccountService;
import com.yichen.core.dto.account.AccountDTO;
import com.yichen.core.enums.AccountStatusEnum;
import com.yichen.core.param.account.AccountParam;
import com.yichen.core.param.account.AccountUpdateParam;
import com.yichen.util.PasswordUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.yichen.util.CustomBeanUtil.getNullPropertyNames;

/**
 * @author dengbojing
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    private final OrganizationRepository organizationRepository;

    private final PasswordUtil passwordUtil;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository, OrganizationRepository organizationRepository, PasswordUtil passwordUtil) {
        this.accountRepository = accountRepository;
        this.organizationRepository = organizationRepository;
        this.passwordUtil = passwordUtil;
    }

    @Override
    public Page<AccountDTO> getPageByCondition(AccountParam accountParam) {
        PageRequest request = PageRequest.of(accountParam.getPageParam().getNum(), accountParam.getPageParam().getSize());
        Page<Account> page = accountRepository.findByLoginNameLike(accountParam.getLoginName(), request);
        return page.map(account -> {
            AccountDTO dto = new AccountDTO();
            BeanUtils.copyProperties(account, dto, "password");
            return dto;
        });
    }

    @Override
    public Optional<AccountDTO> getById(String id) {
        return accountRepository.findById(id).map(account -> {
            checkNotNull(account);
            AccountDTO dto = new AccountDTO();
            BeanUtils.copyProperties(account, dto,"password");
            return dto;
        });
    }

    @Override
    public Integer deleteById(String id) {
        accountRepository.deleteById(id);
        return 1;
    }

    @Override
    public Integer deleteById(List<String> ids) {
        accountRepository.deleteAll(accountRepository.findAllById(ids));
        return ids.size();
    }

    @Override
    public Integer close(String id) {
        accountRepository.findById(id).ifPresent(account -> account.setStatus(AccountStatusEnum.CLOSE));
        return 1;
    }

    @Override
    public Integer close(List<String> ids) {
        accountRepository.findAllById(ids).forEach(account -> account.setStatus(AccountStatusEnum.CLOSE));
        return ids.size();
    }

    @Override
    public Integer updateById(@NonNull AccountUpdateParam accountUpdateParam) {
        accountRepository.findById(accountUpdateParam.getId()).ifPresent(account -> {
            BeanUtils.copyProperties(accountUpdateParam, account, (String[])getNullPropertyNames(accountUpdateParam,"id").toArray());
            Organization organization = organizationRepository.findById(accountUpdateParam.getOrgId()).orElseThrow(() -> new BusinessException("用户组织不正确!请检查!"));
            account.setOrganization(organization);
        });
        return 1;
    }

    @Override
    public Optional<AccountDTO> add(@NonNull AccountParam accountParam) {
        Account account = new Account();
        Organization organization = organizationRepository.findById(accountParam.getOrgId()).orElseThrow(() -> new BusinessException("用户组织不正确!请检查!"));
        BeanUtils.copyProperties(accountParam, account);
        account.setStatus(AccountStatusEnum.OPEN);
        account.setPassword(passwordUtil.encode(accountParam.getPassword()));
        account.setOrganization(organization);
        AccountDTO dto = new AccountDTO();
        accountRepository.save(account);
        BeanUtils.copyProperties(account, dto,"password");
        return Optional.of(dto);
    }
}
