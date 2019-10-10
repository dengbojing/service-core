package com.yichen.core.param.account;

import com.yichen.request.AbstractParam;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author dengbojing
 */
@Getter
@Setter
public class AccountIdsParam extends AbstractParam {

    private List<String> ids;

}
