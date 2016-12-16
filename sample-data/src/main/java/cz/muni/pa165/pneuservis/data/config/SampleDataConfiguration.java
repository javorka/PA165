package cz.muni.pa165.pneuservis.data.config;

import cz.muni.pa165.pneuservis.data.facade.SampleDataFacade;
import cz.muni.pa165.pneuservis.service.config.ServiceConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.annotation.PostConstruct;
import java.io.IOException;

/**
 * Created by peter on 12/15/16.
 */
@Configuration
@ComponentScan(basePackageClasses = {SampleDataFacade.class})
@Import(ServiceConfiguration.class)
public class SampleDataConfiguration {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    SampleDataFacade sampleDataFacade;

    @PostConstruct
    public void dataLoading() throws IOException {
        logger.debug("Loading data...");
        sampleDataFacade.loadData();
    }
}
