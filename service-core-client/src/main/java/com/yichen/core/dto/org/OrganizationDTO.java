package com.yichen.core.dto.org;

import com.yichen.core.enums.OrganizationTypeEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * @author dengbojing
 */
@Setter
@Getter
public class OrganizationDTO {

    private String name;

    private OrganizationTypeEnum type;
}
