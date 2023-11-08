package com.thebizio.biziosalonms.config;

import com.thebizio.biziosalonms.filter.SwitchDBFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<SwitchDBFilter> customFilter() {
        FilterRegistrationBean<SwitchDBFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new SwitchDBFilter());
        registrationBean.addUrlPatterns("/api/*"); // Specify URL patterns to filter
        return registrationBean;
    }
}
