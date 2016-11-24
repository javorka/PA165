package cz.muni.pa165.pneuservis.service;

import cz.muni.pa165.pneuservis.api.dto.OrderDTO;
import cz.muni.pa165.pneuservis.persistence.domain.AdditionalService;
import cz.muni.pa165.pneuservis.persistence.domain.Order;
import cz.muni.pa165.pneuservis.persistence.domain.Tire;
import cz.muni.pa165.pneuservis.persistence.domain.User;
import cz.muni.pa165.pneuservis.persistence.enums.OrderState;
import cz.muni.pa165.pneuservis.persistence.enums.Role;
import cz.muni.pa165.pneuservis.persistence.enums.TireType;
import cz.muni.pa165.pneuservis.service.config.ServiceConfiguration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 * Created by peter on 11/24/16.
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class BeanMappingServiceTest extends AbstractTestNGSpringContextTests {

    @Inject
    BeanMappingService mappingService;

    @Test
    public void shouldMapNestedStructures() {
        Order order = new Order();
        order.setPrice(BigDecimal.TEN);
        order.setAddress("Address");
        order.setUser(createUser());
        order.setAdditionalServices(createAdditionalServices());
        order.setTire(createTire());
        order.setTireQuantity(10);
        order.setState(OrderState.CANCELED);

        OrderDTO dto = mappingService.mapTo(order, OrderDTO.class);
        Assert.assertNull(dto.getId());
        Assert.assertEquals(order.getPrice(), dto.getPrice(), "Price should be the same");
        Assert.assertEquals(order.getAddress(), dto.getAddress(), "Address should be the same");
        Assert.assertEquals(order.getAdditionalServices().size(), dto.getAdditionalServices().size(), "Additional services should be mapped");
        Assert.assertEquals(order.getTire().getName(), dto.getTire().getName(), "Tire should be mapped");
        Assert.assertEquals(order.getState().name(), dto.getState().name(), "Order state should be mapped");
        Assert.assertEquals(order.getUser().getName(), dto.getUser().getName(), "User should be mapped");
        Assert.assertEquals(order.getUser().getRoles().size(), dto.getUser().getRoles().size(), "User roles should be mapped");
    }

    private List<AdditionalService> createAdditionalServices() {
        AdditionalService serviceA = new AdditionalService();
        serviceA.setName("A");
        serviceA.setPrice(BigDecimal.ZERO);
        serviceA.setDescription("Description A");

        AdditionalService serviceB = new AdditionalService();
        serviceB.setName("B");
        serviceB.setPrice(BigDecimal.ONE);
        serviceB.setDescription("Description B");
        return Arrays.asList(serviceA, serviceB);
    }

    private User createUser() {
        User user = new User();
        user.setName("Admin");
        user.setEmail("admin@admin.com");
        user.setRoles(Arrays.asList(Role.ADMIN, Role.CUSTOMER));
        return user;
    }

    private Tire createTire() {
        Tire tire = new Tire();
        tire.setPrice(BigDecimal.ONE);
        tire.setName("Tire");
        tire.setManufacturer("Manufacturer");
        tire.setVehicleType("Sedan");
        tire.setTireType(TireType.AllSeason_LightTruck);
        tire.setSize("48");
        return tire;
    }

}
