package com.xxl.job.executor.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.xxl.job.core.biz.ExecutorBiz;
import com.xxl.job.core.biz.impl.ExecutorBizImpl;

/**
 * @author: liqiang7
 * @date: 2019/1/16 17:34
 * @description:
 */
@Service(group = "xxl-job-executor",token = "harvestJob")
public class ExecutorCustomImpl extends ExecutorBizImpl implements ExecutorBiz {
}
