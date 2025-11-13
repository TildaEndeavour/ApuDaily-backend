package com.example.ApuDaily.testconfig;

import com.example.ApuDaily.testutil.DtoUtil;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class BeanConfig {
    @Bean
    public DtoUtil dtoUtil(){
        return new DtoUtil();
    }
}
