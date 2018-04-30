package sango.spring.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = { "sango.spring.controller" })
public class WebConfig implements WebMvcConfigurer {
  @Override
  public void configureViewResolvers(ViewResolverRegistry registry) {
    registry.jsp().prefix("/WEB-INF/views/").suffix(".jsp");
  }

  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
    registry.addViewController("/login").setViewName("login");    
  }
  
  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
      //specifying static resource location for themes related files(css etc)
      registry.addResourceHandler("/css/**").addResourceLocations("/WEB-INF/css/");
      registry.addResourceHandler("/js/**").addResourceLocations("/WEB-INF/js/");
  }
}
