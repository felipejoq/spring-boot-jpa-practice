package com.uncodigo.springboot.app;

// import java.nio.file.Paths;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;

import org.springframework.context.annotation.Configuration;
// import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/error_403").setViewName("error_403");
    }

//    private final Logger logger = LoggerFactory.getLogger(getClass()); 


//	@Override
//	public void addResourceHandlers(ResourceHandlerRegistry registry) {
//		WebMvcConfigurer.super.addResourceHandlers(registry);
//		
//		String directorioRecursos = Paths.get("uploads").toAbsolutePath().toUri().toString();
//		
//		logger.info("directorioRecursos: " + directorioRecursos);
//		
//		registry.addResourceHandler("/uploads/**").addResourceLocations(directorioRecursos);
//		
//	}

}
