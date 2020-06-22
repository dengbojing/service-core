package com.yichen.core.param.org;

import com.yichen.core.enums.OrganizationTypeEnum;
import com.yichen.core.param.PageParam;
import com.yichen.request.AbstractParam;
import lombok.Getter;
import lombok.Setter;

/**
 * @author dengbojing
 */
@Setter
@Getter
public class OrganizationParam extends AbstractParam {

    private String id;

    private String name;

    private OrganizationTypeEnum type;

    private PageParam pageParam;
}
