package com.yichen.major.service.impl;

import com.yichen.core.enums.AccountStatusEnum;
import com.yichen.major.entity.Account;
import com.yichen.major.entity.LoginHistory;
import com.yichen.major.repo.AccountRepository;
import com.yichen.major.repo.LoginHistoryRepository;
import com.yichen.major.service.LoginService;
import com.yichen.core.dto.login.LoginDTO;
import com.yichen.core.enums.LoginStatusEnum;
import com.yichen.exception.BusinessException;
import com.yichen.core.param.login.LoginParam;
import com.yichen.response.CommonResponse;
import com.yichen.util.JsonUtil;
import com.yichen.util.JwtUtil;
import com.yichen.util.POJO.Subject;
import com.yichen.util.PasswordUtil;
import com.yichen.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author dengbojing
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class LoginServiceImpl implements LoginService {

    @Resource
    private AccountRepository customerRepo;

    @Resource
    private LoginHistoryRepository loginHistoryRepo;

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private PasswordUtil passwordUtil;

    @Resource
    private JsonUtil jsonUtil;

    @Resource
    private JwtUtil jwtUtil;

    @Override
    public CommonResponse login(LoginParam param) {
        CommonResponse result = new CommonResponse();
        LoginHistory loginHistory = new LoginHistory();
        loginHistory.setLoginName(param.getLoginName());
        // 用户是否存在
        Account account = customerRepo.findByLoginNameOrPhoneOrEmail(param.getLoginName(), param.getLoginName(), param.getLoginName()).orElse(new Account());
        if (StringUtils.isEmpty(account.getId())) {
            result.setCode(250);
            result.setMessage("用户名密码错误");
            loginHistory.setStatus(LoginStatusEnum.WRONG_LOGIN_NAME.name());
            loginHistoryRepo.save(loginHistory);
            return result;
        }

        loginHistory.setCustomerId(account.getId());
        loginHistory.setOrganizationId(account.getOrganization().getId());

        if (! AccountStatusEnum.OPEN.equals(account.getStatus())) {
            result.setCode(253);
            result.setMessage("账户被冻结，请联系管理员");
            loginHistory.setStatus(LoginStatusEnum.ACCOUNT_FREEZE.name());
        }

        // 校验用户密码
        String key = "PWD:ERRORTIME:" + account.getId();
        String errorTimeStr = redisUtil.get(key);
        int errorTime = StringUtils.isEmpty(errorTimeStr) ? 0 : Integer.parseInt(errorTimeStr);

        String encode = passwordUtil.encode(param.getPassword());
        if (!encode.equals(account.getPassword())) {
            result.setCode(250);
            result.setMessage("用户名密码错误");
            loginHistory.setStatus(LoginStatusEnum.WRONG_PASSWORD.name());

        }

        if (errorTime >= 5) {
            result.setCode(252);
            result.setMessage("账户因连续输入错误密码次数过多，请明日再试");
            loginHistory.setStatus(LoginStatusEnum.ACCOUNT_TEMP_FREEZE.name());
        }

        if (result.getCode() != 200) {
            loginHistoryRepo.save(loginHistory);
            return result;
        }

        LoginDTO response = setUserInfo(account);

        // 清除密码错误次数
        redisUtil.remove(key);


        Subject subject = new Subject();
        subject.setUserId(account.getId());
        if (StringUtils.isEmpty(account.getOrganization().getId())) {
            loginHistory.setStatus(LoginStatusEnum.ACCOUNT_ERROR.name());
            loginHistoryRepo.save(loginHistory);
            throw new BusinessException("用户信息有误，请联系管理员：组织ID");
        }
        subject.setOrganizationId(account.getOrganization().getId());
        if (null == (account.getOrganization().getType())) {
            loginHistory.setStatus(LoginStatusEnum.ACCOUNT_ERROR.name());
            loginHistoryRepo.save(loginHistory);
            throw new BusinessException("用户信息有误，请联系管理员：组织ID");
        }
        subject.setOrganizationType(Subject.OrganizationType.valueOf(account.getOrganization().getType().name()));
        log.info("UserService::login:" + jsonUtil.string(subject));
        String jws = jwtUtil.general(subject);
        log.info(jsonUtil.string(jws));

        response.setToken(jws);

        loginHistory.setStatus(LoginStatusEnum.SUCCESS.name());
        loginHistoryRepo.save(loginHistory);
        return CommonResponse.success(response);
    }

    private LoginDTO setUserInfo(Account customer) {
        LoginDTO loginDTO = new LoginDTO();
        LoginDTO.AccountBody body = new LoginDTO.AccountBody();
        BeanUtils.copyProperties(customer, body);
        body.setOrganizationId(customer.getOrganization().getId());
        body.setOrganizationName(customer.getOrganization().getName());
        body.setOrganizationType(customer.getOrganization().getType());
        loginDTO.setCustomerBody(body);
        return loginDTO;
    }

}
