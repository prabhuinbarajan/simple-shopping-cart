package com.bhn.shoppingcart;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@Profile("test")
@EnableWebMvc

@ComponentScan("com.bhn.shoppingcart")
public class TestContextConfiguration implements WebMvcConfigurer {

}