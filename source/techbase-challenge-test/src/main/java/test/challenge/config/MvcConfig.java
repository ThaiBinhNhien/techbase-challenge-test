package test.challenge.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class MvcConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
    	 registry.addResourceHandler(
                 "/webjars/**",
                 "/apps/**",
                 "/assets/**",
                 "/plugins/**"
                 )
                 .addResourceLocations(
                         "classpath:/META-INF/resources/webjars/",
                         "classpath:/static/apps/",
                         "classpath:/static/assets/",
                         "classpath:/static/plugins/");	
    }
}