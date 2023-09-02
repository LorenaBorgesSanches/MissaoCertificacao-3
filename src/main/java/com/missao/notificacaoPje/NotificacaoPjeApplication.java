package com.missao.notificacaoPje;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class NotificacaoPjeApplication {

    public static void main(String[] args) {
        SpringApplication.run(NotificacaoPjeApplication.class, args);
    }

}
