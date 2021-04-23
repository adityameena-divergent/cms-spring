package com.divergentsl.cms.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.divergentsl.cms","com.divergentsl.cms.database","com.divergentsl.cms.dao"})
public class JavaConfiguration {

}
