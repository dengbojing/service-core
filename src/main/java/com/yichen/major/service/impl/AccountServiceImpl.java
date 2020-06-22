package com.yichen.major.service.impl;

import com.yichen.core.dto.account.AppkeyDTO;
import com.yichen.core.enums.ChargeTypeEnum;
import com.yichen.exception.BusinessException;
import com.yichen.major.entity.Account;
import com.yichen.major.entity.AppKey;
import com.yichen.major.entity.Organization;
import com.yichen.major.repo.AccountRepository;
import com.yichen.major.repo.AppKeyRepository;
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

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

    private final AppKeyRepository appKeyRepo;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository, OrganizationRepository organizationRepository, PasswordUtil passwordUtil, AppKeyRepository appKeyRepo) {
        this.accountRepository = accountRepository;
        this.organizationRepository = organizationRepository;
        this.passwordUtil = passwordUtil;
        this.appKeyRepo = appKeyRepo;
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

    @Override
    public Integer decrease(String userId, int size) {
        accountRepository.findById(userId).ifPresent(account -> {
            if(ChargeTypeEnum.COUNT.equals(account.getChargeType())){
                Long count = account.getCount() - size;
                account.setCount(count);
            }
        });
        return size;
    }

    @Override
    public void check(String userId) {
        accountRepository.findById(userId).filter(account -> {
            boolean open = account.getStatus().equals(AccountStatusEnum.OPEN);
            if(ChargeTypeEnum.TIME.equals(account.getChargeType())){
                return open && account.getEndDate() != null && account.getEndDate().compareTo(LocalDate.now()) >= 0;
            }else{
                return open && account.getCount() != null && account.getCount() > 0;
            }
        }).orElseThrow(() -> new BusinessException("使用期限已到,清联系管理人员购买!"));
    }

    @Override
    public Optional<AppkeyDTO> getKey(AccountParam accountParam) {
        AppkeyDTO dto = new AppkeyDTO();
        accountRepository.findById(accountParam.getUserId()).ifPresent(account -> {
            if(!account.getStatus().equals(AccountStatusEnum.CLOSE)) {
                AppKey appkey = appKeyRepo.findByAccount(account).orElseGet(()->{
                    AppKey appkeyTemp = new AppKey();
                    appkeyTemp.setAppKey(UUID.randomUUID().toString().replace("-",""));
                    appkeyTemp.setSecretKey(UUID.randomUUID().toString().replace("-",""));
                    appkeyTemp.setAccount(account);
                    return appkeyTemp;
                });
                appKeyRepo.save(appkey);
                dto.setAppKey(appkey.getAppKey());
                dto.setSecretKey(appkey.getSecretKey());
            }else{
                throw new BusinessException("账户已经被冻结!");
            }

        });
        return Optional.of(dto);
    }

    @Override
    public Optional<AppkeyDTO> generateKey(AccountParam accountParam) {
        AppkeyDTO dto = new AppkeyDTO();
        accountRepository.findById(accountParam.getUserId()).ifPresent(account -> {
            if(!account.getStatus().equals(AccountStatusEnum.CLOSE)) {
                appKeyRepo.deleteByAccount(account);
                AppKey appkey =  new AppKey();
                appkey.setAppKey(UUID.randomUUID().toString().replace("-",""));
                appkey.setSecretKey(UUID.randomUUID().toString().replace("-",""));
                appkey.setAccount(account);
                appKeyRepo.save(appkey);
            }else{
                throw new BusinessException("账户已经被冻结!");
            }
        });
        return Optional.of(dto);
    }
}
