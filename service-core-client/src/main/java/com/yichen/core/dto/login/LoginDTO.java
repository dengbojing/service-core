package com.yichen.core.dto.login;

import com.yichen.core.enums.OrganizationTypeEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * @author dengbojing
 */
@Getter
@Setter
public class LoginDTO {

    private AccountBody customerBody;

    private String token;


    @Getter
    @Setter
    public static class AccountBody {

        private String userId;

        private String name;

        private String loginName;

        private String organizationId;

        private String organizationName;

        private OrganizationTypeEnum organizationType;
    }
}
