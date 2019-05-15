package com.spring.store.config;

import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;

public class WebConfig {

    private static Configuration configuration;

    static {
        configuration = new Configuration(Configuration.VERSION_2_3_28);
        ClassTemplateLoader loader = new ClassTemplateLoader(new WebConfig().getClass(), "/templates");
        configuration.setTemplateLoader(loader);
        configuration.setDefaultEncoding("UTF-8");
        configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
    }

    public static Configuration getConfiguration() {
        return configuration;
    }

}
