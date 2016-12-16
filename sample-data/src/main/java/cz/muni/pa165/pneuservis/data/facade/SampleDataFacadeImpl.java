package cz.muni.pa165.pneuservis.data.facade;

import cz.muni.pa165.pneuservis.persistence.domain.AdditionalService;
import cz.muni.pa165.pneuservis.persistence.domain.Order;
import cz.muni.pa165.pneuservis.persistence.domain.Tire;
import cz.muni.pa165.pneuservis.persistence.domain.User;
import cz.muni.pa165.pneuservis.persistence.enums.OrderState;
import cz.muni.pa165.pneuservis.persistence.enums.Role;
import cz.muni.pa165.pneuservis.persistence.enums.TireType;
import cz.muni.pa165.pneuservis.service.AdditionalServiceService;
import cz.muni.pa165.pneuservis.service.OrderService;
import cz.muni.pa165.pneuservis.service.TireService;
import cz.muni.pa165.pneuservis.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;

/**
 * Created by peter on 12/15/16.
 */
@Component
@Transactional
public class SampleDataFacadeImpl implements SampleDataFacade {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    UserService userService;

    @Autowired
    TireService tireService;

    @Autowired
    OrderService orderService;

    @Autowired
    AdditionalServiceService asService;

    @Override
    public void loadData() throws IOException {
        logger.info("Creating users");
        User admin = userService.save(createAdmin());
        User customer = userService.save(createCustomer());

        logger.info("Creating tires");
        Tire tire1 = tireService.save(createAllSeasonTire());
        Tire tire2 = tireService.save(createOffRoadTire());

        logger.info("Creating additional services");
        AdditionalService washing = asService.save(createCarWashService());
        AdditionalService tireChange = asService.save(createPneuChangeService());

        logger.info("Creating orders");
        Order oldOrder = new Order();
        oldOrder.setUser(admin);
        oldOrder.setAdditionalServices(Collections.singletonList(washing));
        oldOrder.setTire(tire1);
        oldOrder.setTireQuantity(4);
        oldOrder.setAddress("Kounicova 5, 601 77 Brno");
        oldOrder.setState(OrderState.DONE);
        Calendar calendar = Calendar.getInstance();
        calendar.set(2000, 11, 1);
        oldOrder.setDateCreated(calendar.getTime());
        oldOrder.setPhone("+420 989 989 898");
        oldOrder.setPrice(washing.getPrice()
                .add(tire1.getPrice().multiply(BigDecimal.valueOf(oldOrder.getTireQuantity()))));

        orderService.save(oldOrder);

        Order receivedOrder = new Order();
        receivedOrder.setUser(customer);
        receivedOrder.setAdditionalServices(Arrays.asList(washing, tireChange));
        receivedOrder.setTire(tire2);
        receivedOrder.setTireQuantity(6);
        receivedOrder.setAddress("Kosicka 4, 036 01 Martin, Slovakia");
        receivedOrder.setState(OrderState.RECEIVED);
        calendar.set(2016, 12, 6);
        receivedOrder.setDateCreated(calendar.getTime());
        receivedOrder.setPhone("+421 111 222 333");
        receivedOrder.setPrice(washing.getPrice()
                .add(tireChange.getPrice())
                .add(tire1.getPrice()
                        .multiply(BigDecimal.valueOf(oldOrder.getTireQuantity()))));


        orderService.save(receivedOrder);

        Order canceledOrder = new Order();
        canceledOrder.setUser(customer);
        canceledOrder.setTire(tire2);
        canceledOrder.setTireQuantity(1);
        canceledOrder.setAddress("Makarska 5, Croatia");
        canceledOrder.setState(OrderState.CANCELED);
        calendar.set(2016, 4, 1);
        canceledOrder.setDateCreated(calendar.getTime());
        canceledOrder.setPhone("+398 777 888 999");
        canceledOrder.setPrice(tire2.getPrice().multiply(BigDecimal.valueOf(canceledOrder.getTireQuantity())));

        orderService.save(canceledOrder);

        logger.info("All done");
    }

    private User createAdmin() {
        User user = new User();
        user.setName("Admin");
        user.setPassword("$2a$10$J6GXBtY/DDq317kymm3zD.d9J6UEP2CYkw3kDSaXx81bDrw1JP.6S");
        user.setEmail("admin@pneuservis.fi.muni.cz");
        user.setRoles(Arrays.asList(Role.CUSTOMER, Role.ADMIN));
        return user;
    }

    private User createCustomer() {
        User user = new User();
        user.setName("User #1");
        user.setPassword("$2a$10$J6GXBtY/DDq317kymm3zD.d9J6UEP2CYkw3kDSaXx81bDrw1JP.6S");
        user.setEmail("student@fi.muni.cz");
        user.setRoles(Collections.singletonList(Role.CUSTOMER));
        return user;
    }

    private Tire createAllSeasonTire() {
        Tire tire = new Tire();
        tire.setName("Pneumatika 3000");
        tire.setManufacturer("Michelin");
        tire.setSize("21");
        tire.setTireType(TireType.AllSeason_Passenger);
        tire.setPrice(BigDecimal.valueOf(3000));
        tire.setVehicleType("velke modre");
        return tire;
    }

    private Tire createOffRoadTire() {
        Tire tire = new Tire();
        tire.setName("Off road pneu 4500");
        tire.setManufacturer("Matador");
        tire.setSize("22");
        tire.setTireType(TireType.OffRoad_LightTruck);
        tire.setPrice(BigDecimal.valueOf(4500));
        tire.setVehicleType("off road");
        return tire;
    }

    private AdditionalService createPneuChangeService() {
        AdditionalService service = new AdditionalService();
        service.setName("Tire change");
        service.setDescription("Change one tire.");
        service.setPrice(BigDecimal.valueOf(25));
        return service;
    }

    private AdditionalService createCarWashService() {
        AdditionalService service = new AdditionalService();
        service.setName("Car wash");
        service.setDescription("Car washing. Including shampooing and waxing");
        service.setPrice(BigDecimal.valueOf(20));
        return service;
    }
}
