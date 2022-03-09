package com.ireland.agerimport

import org.springframework.context.annotation.Bean
import org.springframework.web.client.RestTemplate
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.cache.annotation.EnableCaching
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.security.web.firewall.HttpFirewall
import org.springframework.security.web.firewall.StrictHttpFirewall
import kotlin.jvm.JvmStatic
import org.springframework.boot.SpringApplication

@SpringBootApplication
@EnableJpaAuditing
@EnableCaching
@EnableScheduling
open class AgerProjectApplication {

    @Bean
    open fun getRestTemplate(): RestTemplate {
        return RestTemplate()
    }
    @Bean
    open fun allowUrlSemicolonHttpFirewall(): HttpFirewall {
        val firewall = StrictHttpFirewall()
        firewall.setAllowSemicolon(true)
        return firewall
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplication.run(AgerProjectApplication::class.java, *args)
        }
    }
}