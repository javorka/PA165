package cz.muni.pa165.pneuservis.config;

import cz.muni.pa165.pneuservis.persistence.Config;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created by peter on 11/18/16.
 */

@Configuration
@ComponentScan(basePackages = "cz.muni.pa165.pneuservis.service.*")
@Import(Config.class)
public class TestConfiguration {
    @Bean
    public Mapper dozer() {
        return new DozerBeanMapper();
    }
}
