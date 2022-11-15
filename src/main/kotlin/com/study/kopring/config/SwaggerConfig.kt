package com.study.kopring.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders.AUTHORIZATION
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.*
import springfox.documentation.service.HttpAuthenticationScheme.JWT_BEARER_BUILDER
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spi.service.contexts.SecurityContext
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2


@Configuration
@EnableSwagger2
class SwaggerConfig {

    @Bean
    fun api(): Docket {
        return Docket(DocumentationType.OAS_30)
            .useDefaultResponseMessages(false)
            .securityContexts(listOf(securityContext()))
            .securitySchemes(listOf(httpScheme()))
            .select()
            .apis(RequestHandlerSelectors.any())
            .paths(PathSelectors.any())
            .build()
    }

    private fun securityContext(): SecurityContext {
        return SecurityContext.builder()
            .securityReferences(defaultAuth())
            .build();
    }

    private fun defaultAuth(): List<SecurityReference> {
        val authorizationScope = AuthorizationScope("global", "accessEverything")
        val authorizationScopes: Array<AuthorizationScope?> = arrayOfNulls(1)
        authorizationScopes[0] = authorizationScope
        return listOf(SecurityReference(AUTHORIZATION, authorizationScopes))
    }

    // ApiKey를 사용하는 방법
    private fun apiKey(): ApiKey = ApiKey("Authorization", "Authorization", "header")

    // Bearer 방식을 사용하는 방법
    private fun httpScheme(): HttpAuthenticationScheme = HttpAuthenticationScheme(
        /* name = */ AUTHORIZATION,
        /* description = */ "jwt 토큰",
        /* type = */ "http",
        /* scheme = */ JWT_BEARER_BUILDER.build().scheme,
        /* bearerFormat = */ JWT_BEARER_BUILDER.build().bearerFormat,
        /* extensions = */ ArrayList()
    )

}