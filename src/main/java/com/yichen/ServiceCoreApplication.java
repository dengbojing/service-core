package com.yichen;

import com.ulisesbocchio.jasyptspringboot.environment.StandardEncryptableEnvironment;
import com.yichen.config.properties.StorageProperties;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;

/**
 * @author dengbojing
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableConfigurationProperties({StorageProperties.class})
@EnableJpaAuditing
@EnableTransactionManagement
public class ServiceCoreApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder()
                //.environment(new StandardEncryptableEnvironment())
                .sources(ServiceCoreApplication.class).run(args);
    }




}
