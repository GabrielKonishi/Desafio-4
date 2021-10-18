package com.trilha.back.finacys.config;

import java.util.ArrayList;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SpringFoxConfig {
	
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.trilha.back.finacys"))
				.paths(PathSelectors.any())
				.build()
				.apiInfo(metaInfo());
	}
	
	 public void addResourceHandlers(ResourceHandlerRegistry registry) 
	    {
	        //enabling swagger-ui part for visual documentation
	        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
	        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
	    }
	
	private ApiInfo metaInfo() {
		@SuppressWarnings("rawtypes")
		ApiInfo apiInfo = new ApiInfo(
				"Produtos Api Rest",
				"Api Rest de cadastro e listagem de categoria",
				"1.0",
				"terms of service",
				new Contact("gabriel henrique", "www.test.com.br", "gtest13@gmail.com"),
				"apache licence version",
				"test", new ArrayList<VendorExtension>()
				);
		return apiInfo;
	}
	
}
