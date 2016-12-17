package cz.muni.pa165.pneuservis.mvc.config;

import cz.muni.pa165.pneuservis.data.config.SampleDataConfiguration;
import cz.muni.pa165.pneuservis.mvc.controller.AdditionalServiceController;
import cz.muni.pa165.pneuservis.mvc.security.WebSecurityConfiguration;
import cz.muni.pa165.pneuservis.service.config.ServiceConfiguration;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

;

/**
 * Created by xjavorka on 14.12.16.
 */
@Configuration
@Import({ServiceConfiguration.class, WebSecurityConfiguration.class, SampleDataConfiguration.class})
@ComponentScan(basePackageClasses = {AdditionalServiceController.class})
@EnableWebMvc
public class MVCConfig extends WebMvcConfigurerAdapter {
    /**
     * Enables default Tomcat servlet that serves static files.
     */
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("home");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }

    @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/jsp/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }

    /**
     * Provides localized messages.
     */
    @Bean
    public MessageSource messageSource() {
//        log.debug("registering ResourceBundle 'Texts' for messages");
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("Texts");
        return messageSource;
    }
}
