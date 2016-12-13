package cz.muni.pa165.pneuservis.rest.config;

import cz.muni.pa165.pneuservis.rest.controller.AdditionalServiceController;
import cz.muni.pa165.pneuservis.service.config.ServiceConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by peter on 12/13/16.
 */
@EnableWebMvc
@Configuration
@Import(ServiceConfiguration.class)
@ComponentScan(basePackageClasses = {AdditionalServiceController.class})
public class RestConfiguration extends WebMvcConfigurerAdapter {

}
