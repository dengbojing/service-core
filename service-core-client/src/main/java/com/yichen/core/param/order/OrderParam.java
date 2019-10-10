package com.yichen.core.param.order;

import com.yichen.core.enums.OrderStatusEnum;
import com.yichen.core.param.PageParam;
import com.yichen.request.AbstractParam;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author dengbojing
 */
@Getter
@Setter
public class OrderParam extends AbstractParam {

    /**
     * 账号id
     */
    private String accountId;

    /**
     * 完成时间
     */
    private LocalDateTime completeTime;

    /**
     * 创建时间
     */
    private LocalDateTime startTime;

    /**
     * 订单状态
     */
    private OrderStatusEnum status;

    /**
     * 分页参数
     */
    private PageParam pageParam;

    /**
     * 文件列表
     */
    private List<String> fileList;

}
