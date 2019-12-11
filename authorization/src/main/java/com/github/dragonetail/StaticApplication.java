package com.github.dragonetail;

import org.springframework.context.*;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

/**
 * Spring全局静态参数辅助类
 *
 * @author sunyx
 */
@Component
public class StaticApplication implements
        ApplicationContextAware, MessageSourceAware, ResourceLoaderAware, EnvironmentAware {

    public static ApplicationContext applicationContext;
    public static MessageSource messageSource;
    public static ResourceLoader resourceLoader;
    public static Environment environment;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        StaticApplication.applicationContext = applicationContext;
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        StaticApplication.messageSource = messageSource;
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        StaticApplication.resourceLoader = resourceLoader;
    }

    @Override
    public void setEnvironment(Environment environment) {
        StaticApplication.environment = environment;
    }
}
