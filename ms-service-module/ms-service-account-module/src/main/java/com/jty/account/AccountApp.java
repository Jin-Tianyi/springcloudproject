package com.jty.account;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author :jty
 * @date :20-9-10
 */
@SpringBootApplication
@EnableDiscoveryClient
public class AccountApp {
    public static void main(String[] args) {
        new SpringApplicationBuilder(AccountApp.class).web(WebApplicationType.SERVLET).run(args);
    }
}
