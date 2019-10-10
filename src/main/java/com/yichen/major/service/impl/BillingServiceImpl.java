package com.yichen.major.service.impl;

import com.yichen.major.service.BillingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author dengbojing
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class BillingServiceImpl implements BillingService {
}
