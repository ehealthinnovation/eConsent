package org.ehealthinnovation.econsent.server.appcfg;

import org.ehealthinnovation.econsent.server.swaggerui.SwaggerUiController;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger-UI config
 */
@EnableSwagger2
@EnableWebMvc
public class AppCfgSwaggerUi implements WebMvcConfigurer {
    @Bean(autowire = Autowire.BY_TYPE)
    public SwaggerUiController swaggerUiController() {
        SwaggerUiController retVal = new SwaggerUiController();

        retVal.setAppContextPath("/server");

        return retVal;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry theRegistry) {
        theRegistry.addResourceHandler("/swagger-ui/**").addResourceLocations("classpath:/swagger-ui/");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry theRegistry) {
        theRegistry.addRedirectViewController("/swagger-ui", "/swagger-ui/");
        theRegistry.addViewController("/swagger-ui/").setViewName("/swagger-ui/index.html");
    }

}
