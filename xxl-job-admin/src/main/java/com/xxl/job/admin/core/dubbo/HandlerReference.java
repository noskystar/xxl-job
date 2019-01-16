package com.xxl.job.admin.core.dubbo;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobDubboHandler;
import com.xxl.job.core.handler.IJobHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author: liqiang7
 * @date: 2019/1/11 17:52
 * @description:
 */
public class HandlerReference extends IJobHandler implements IJobDubboHandler {
    private static Logger logger = LoggerFactory.getLogger(HandlerReference.class);

    private static boolean enableBadMap = false;

    @Value("${spring.dubbo.application.name}")
    private String applicationName;



    private volatile ReferenceConfig<IJobDubboHandler> referenceConfig;

    private volatile IJobDubboHandler jobHandler;


    public HandlerReference(String group, RegistryConfig registryConfig) {
        init(group, registryConfig);
    }

    //初始化配置
    synchronized void init(String group, RegistryConfig registryConfig) {
        this.referenceConfig = createReference(group, registryConfig);
        this.jobHandler = this.referenceConfig.get();
    }

    private ReferenceConfig<IJobDubboHandler> createReference(String group, RegistryConfig registryConfig) {
        ApplicationConfig application = new ApplicationConfig();
        application.setName("xxl-job-admin");

        ReferenceConfig<IJobDubboHandler> referenceConfig = new ReferenceConfig<>();
        referenceConfig.setApplication(application);
        referenceConfig.setRegistry(registryConfig);
        referenceConfig.setGroup(group);
        referenceConfig.setInterface(IJobDubboHandler.class);
        referenceConfig.setCheck(enableBadMap);
        referenceConfig.setTimeout(registryConfig.getTimeout());
        return referenceConfig;
    }

    @Override
    public ReturnT<String> execute(String param) throws Exception {
        if (this.jobHandler != null) {
            return this.jobHandler.execute(param);
        }
        throw new RuntimeException("handler instance is empty");
    }
}
