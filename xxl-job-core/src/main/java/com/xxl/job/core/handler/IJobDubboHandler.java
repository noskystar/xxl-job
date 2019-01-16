package com.xxl.job.core.handler;

import com.xxl.job.core.biz.model.ReturnT;

/**
 * @author: liqiang7
 * @date: 2019/1/3 18:35
 * @description:
 */
public interface IJobDubboHandler {
    /**
     * execute handler, invoked when executor receives a scheduling request
     *
     * @param param
     * @return
     * @throws Exception
     */
    public abstract ReturnT<String> execute(String param) throws Exception;
}
