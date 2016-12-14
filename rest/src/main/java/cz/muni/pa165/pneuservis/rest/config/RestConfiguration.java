package cz.muni.pa165.pneuservis.rest.config;

import org.springframework.web.servlet.config.annotation.*;

/**
 * Created by peter on 12/13/16.
 */
public class RestConfiguration extends WebMvcConfigurationSupport {
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }
}
