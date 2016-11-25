package cz.muni.pa165.pneuservis.service;

import cz.muni.pa165.pneuservis.persistence.domain.AdditionalService;
import cz.muni.pa165.pneuservis.persistence.repository.AdditionalServiceRepository;
import cz.muni.pa165.pneuservis.service.config.ServiceConfiguration;
import cz.muni.pa165.pneuservis.service.util.Utils;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.util.ReflectionTestUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.math.BigDecimal;

/**
 * Created by peter on 11/22/16.
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class AdditionalServiceServiceTest extends AbstractTestNGSpringContextTests {
    @Mock
    AdditionalServiceRepository repository;

    @Autowired
    @InjectMocks
    AdditionalServiceService additionalServiceService;

    @BeforeClass
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
        AdditionalServiceService unwrappedProxy = (AdditionalServiceService) Utils.unwrapProxy(additionalServiceService);
        ReflectionTestUtils.setField(unwrappedProxy, "repository", repository);
    }

    @Test
    public void test1() {
        AdditionalService service = new AdditionalService();
        service.setName("Nazov");
        service.setDescription("Opis");
        service.setPrice(BigDecimal.TEN);
        additionalServiceService.save(service);
    }
}
