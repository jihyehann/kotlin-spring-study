package com.study.kopring

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import springfox.documentation.spring.data.rest.configuration.SpringDataRestConfiguration
import springfox.documentation.swagger2.annotations.EnableSwagger2

@SpringBootApplication
@EnableJpaAuditing
class KopringApplication

fun main(args: Array<String>) {
	runApplication<KopringApplication>(*args)
}
