package cz.muni.pa165.pneuservis.service;

import cz.muni.pa165.pneuservis.persistence.domain.Order;
import cz.muni.pa165.pneuservis.persistence.domain.Tire;
import cz.muni.pa165.pneuservis.persistence.enums.TireType;
import cz.muni.pa165.pneuservis.persistence.repository.OrderRepository;
import cz.muni.pa165.pneuservis.persistence.repository.TireRepository;
import cz.muni.pa165.pneuservis.service.config.ServiceConfiguration;
import cz.muni.pa165.pneuservis.service.impl.TireServiceImpl;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.not;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Michal Krajcovic <mkrajcovic@mail.muni.cz>
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class TireServiceTest extends AbstractTestNGSpringContextTests {
    @Mock
    private TireRepository tireRepository;

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private TireServiceImpl tireService;

    private Tire tire;

    @BeforeClass
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    public void createTire() {
        tire = new Tire();
        tire.setId(1L);
        tire.setPrice(new BigDecimal("1000"));
        tire.setManufacturer("Adidas");
        tire.setTireType(TireType.AllSeason_LightTruck);
        tire.setName("Pneumatika 9001");
        tire.setVehicleType("modre auto");
        tire.setSize("21");
    }

    @Test
    public void testSaveTire() {
        when(tireRepository.save(any(Tire.class))).thenReturn(tire);

        Tire t = tireService.save(tire);

        Assert.assertEquals(t, tire);
    }

    @Test
    public void testFindOneTire() {
        when(tireRepository.findOne(1L)).thenReturn(tire);

        Tire t = tireService.findOne(1L);

        Assert.assertEquals(t, tire);
    }

    @Test
    public void testFindAllTires() {
        when(tireRepository.findAll()).thenReturn(Collections.singletonList(tire));

        List<Tire> all = tireService.findAll();

        Assert.assertTrue(all.size() == 1);
        Assert.assertTrue(all.contains(tire));
    }

    @Test
    public void testFindByTireType() {
        when(tireRepository.findByTireType(TireType.AllSeason_LightTruck)).thenReturn(Collections.singletonList(tire));
        when(tireRepository.findByTireType(argThat(not(TireType.AllSeason_LightTruck)))).thenReturn(new ArrayList<>());

        List<Tire> allSeasonLightTrucks = tireService.findByTireType(TireType.AllSeason_LightTruck);

        Assert.assertTrue(allSeasonLightTrucks.size() == 1);
        Assert.assertTrue(allSeasonLightTrucks.contains(tire));

        List<Tire> trailers = tireService.findByTireType(TireType.Trailer);

        Assert.assertTrue(trailers.isEmpty());
    }

    @Test
    public void testFindThreeBestSellingTires() {
        Tire t2 = newTire(2L);
        Tire t3 = newTire(3L);
        Tire t4 = newTire(4L);

        Order o4 = newOrder(t4);
        Order o1 = newOrder(t2);
        Order o3 = newOrder(t2);
        Order o5 = newOrder(t3);
        Order o6 = newOrder(t3);
        Order o2 = newOrder(tire);
        Order o7 = newOrder(tire);

        when(tireRepository.findOne(1L)).thenReturn(tire);
        when(tireRepository.findOne(2L)).thenReturn(t2);
        when(tireRepository.findOne(3L)).thenReturn(t3);
        when(tireRepository.findOne(4L)).thenReturn(t4);
        when(orderRepository.findAll()).thenReturn(Arrays.asList(o1, o2, o3, o4, o5, o6, o7));

        List<Tire> bestSellingTires = tireService.findThreeBestSelling();

        Assert.assertTrue(bestSellingTires.size() == 3);

        for (Tire bestSellingTire : bestSellingTires) {
            System.out.println(bestSellingTire.getName());
        }
        Assert.assertTrue(bestSellingTires.contains(tire));
        Assert.assertTrue(bestSellingTires.contains(t2));
        Assert.assertTrue(bestSellingTires.contains(t3));
    }

    private Order newOrder(Tire tire) {
        Order order = new Order();
        order.setTire(tire);
        order.setTireQuantity(4);
        return order;
    }

    @Test
    public void testDeleteTire() {
        tireService.delete(1L);
        verify(tireRepository).delete(1L);
    }

    private Tire newTire(Long id) {
        Tire tire = new Tire();
        tire.setId(id);
        tire.setPrice(new BigDecimal(id + "000"));
        tire.setManufacturer("Adidas" + id);
        tire.setTireType(TireType.AllSeason_LightTruck);
        tire.setName("Pneumatika 900" + id);
        tire.setVehicleType("modre auto" + id);
        tire.setSize("2" + id);
        return tire;
    }
}
