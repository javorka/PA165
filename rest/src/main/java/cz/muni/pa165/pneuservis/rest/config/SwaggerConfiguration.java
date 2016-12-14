package cz.muni.pa165.pneuservis.rest.config;

import cz.muni.pa165.pneuservis.rest.controller.AdditionalServiceController;
import cz.muni.pa165.pneuservis.service.config.ServiceConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created by xjavorka on 14.12.16.
 */
@Configuration
@ComponentScan(basePackageClasses = {AdditionalServiceController.class})
@EnableWebMvc
@EnableSwagger2
@Import(ServiceConfiguration.class)
public class SwaggerConfiguration extends WebMvcConfigurerAdapter {
    private static final String[] SERVLET_RESOURCE_LOCATIONS = { "/" };
    private static final String[] CLASSPATH_RESOURCE_LOCATIONS = {
            "classpath:/META-INF/resources/", "classpath:/resources/",
            "classpath:/static/", "classpath:/public/" };

    private static final String[] RESOURCE_LOCATIONS;
    static {
        RESOURCE_LOCATIONS = new String[CLASSPATH_RESOURCE_LOCATIONS.length + SERVLET_RESOURCE_LOCATIONS.length];
        System.arraycopy(SERVLET_RESOURCE_LOCATIONS, 0, RESOURCE_LOCATIONS, 0, SERVLET_RESOURCE_LOCATIONS.length);
        System.arraycopy(CLASSPATH_RESOURCE_LOCATIONS, 0, RESOURCE_LOCATIONS, SERVLET_RESOURCE_LOCATIONS.length, CLASSPATH_RESOURCE_LOCATIONS.length);
    }

    private static final String[] STATIC_INDEX_HTML_RESOURCES;
    static {
        STATIC_INDEX_HTML_RESOURCES = new String[RESOURCE_LOCATIONS.length];
        for (int i = 0; i < STATIC_INDEX_HTML_RESOURCES.length; i++) {
            STATIC_INDEX_HTML_RESOURCES[i] = RESOURCE_LOCATIONS[i] + "index.html";
        }
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        if (!registry.hasMappingForPattern("/webjars/**")) {
            registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
        }
        if (!registry.hasMappingForPattern("/**")) {
            registry.addResourceHandler("/**").addResourceLocations(RESOURCE_LOCATIONS);
        }
    }
}
