package com.brayant.demo;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter{
	
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("home");
		registry.addViewController("/home").setViewName("home");
		registry.addViewController("/adminrecettes").setViewName("adminrecettes");
		registry.addViewController("/login").setViewName("login");
		registry.addViewController("/admincategories").setViewName("admincategories");
		registry.addViewController("/description").setViewName("description");
		registry.addViewController("/recetteCategorie").setViewName("recetteCategorie");
		registry.addViewController("/recettes").setViewName("recettes");
		registry.addViewController("/recherche").setViewName("recherche");
		registry.addViewController("/register").setViewName("register");
	}
	
	public void addRessourceHandler(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/css/**").addResourceLocations("classpath:/static/bootstrap-5.0.2-dist/css");
		registry.addResourceHandler("/js/**").addResourceLocations("classpath:/static/bootstrap-5.0.2-dist/js");
	}

}
