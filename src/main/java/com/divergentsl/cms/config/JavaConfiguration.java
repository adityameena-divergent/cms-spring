package com.divergentsl.cms.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(basePackages = {"com.divergentsl.cms","com.divergentsl.cms.database","com.divergentsl.cms.dao"})
@Profile("dev")
@PropertySource("classpath:/application.properties")
public class JavaConfiguration {

}
