package cz.muni.pa165.pneuservis.service.config;

import cz.muni.pa165.pneuservis.api.dto.*;
import cz.muni.pa165.pneuservis.persistence.config.PersistenceConfiguration;
import cz.muni.pa165.pneuservis.persistence.domain.AdditionalService;
import cz.muni.pa165.pneuservis.persistence.domain.Tire;
import cz.muni.pa165.pneuservis.persistence.domain.User;
import cz.muni.pa165.pneuservis.persistence.enums.OrderState;
import cz.muni.pa165.pneuservis.persistence.enums.TireType;
import cz.muni.pa165.pneuservis.service.impl.UserServiceImpl;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created by peter on 11/18/16.
 */

@Configuration
@ComponentScan(basePackageClasses = {UserServiceImpl.class})
@Import(PersistenceConfiguration.class)
public class ServiceConfiguration {
    @Bean
    public Mapper dozer() {
        DozerBeanMapper mapper = new DozerBeanMapper();
        mapper.addMapping(new DozerCustomConfig());
        return mapper;
    }

    public class DozerCustomConfig extends BeanMappingBuilder {
        @Override
        protected void configure() {
            mapping(User.class, UserDTO.class);
            mapping(AdditionalService.class, AdditionalServiceDTO.class);
            mapping(Tire.class, TireDTO.class);
            mapping(OrderState.class, OrderStateDTO.class);
            mapping(TireType.class, TireTypeDTO.class);
        }
    }
}
