package cz.muni.pa165.pneuservis.service;

import cz.muni.pa165.pneuservis.persistence.domain.Order;
import cz.muni.pa165.pneuservis.persistence.domain.Tire;
import cz.muni.pa165.pneuservis.persistence.domain.User;
import cz.muni.pa165.pneuservis.persistence.enums.OrderState;
import cz.muni.pa165.pneuservis.persistence.enums.Role;
import cz.muni.pa165.pneuservis.persistence.repository.OrderRepository;
import cz.muni.pa165.pneuservis.persistence.repository.UserRepository;
import cz.muni.pa165.pneuservis.service.config.ServiceConfiguration;
import cz.muni.pa165.pneuservis.service.util.Utils;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.*;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

/**
 * @author Michal Krajcovic <mkrajcovic@mail.muni.cz>
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class UserServiceTest extends AbstractTestNGSpringContextTests {
    @Mock
    private UserRepository userRepository;

    @Mock
    private OrderRepository orderRepository;

    @Inject
    @InjectMocks
    private UserService userService;

    private User admin;
    private User customer;
    private User guest;
    private User invalidUser;

    @BeforeClass
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
        UserService unwrappedProxy = (UserService) Utils.unwrapProxy(userService);
        ReflectionTestUtils.setField(unwrappedProxy, "userRepository", userRepository);
        ReflectionTestUtils.setField(unwrappedProxy, "orderRepository", orderRepository);
    }

    @BeforeMethod
    public void createUsers() {
        createAdmin();
        createCustomer();
        createGuest();
        createInvalidUser();
    }

    @Test
    public void testFindAllUsers() {
        when(userRepository.findAll()).thenReturn(Arrays.asList(admin, customer, guest));

        List<User> allUsers = Arrays.asList(admin, customer, guest);
        List<User> foundUsers = userService.findAll();
        Assert.assertEquals(allUsers.size(), foundUsers.size());
        foundUsers.forEach(user -> {
            Assert.assertNotNull(user.getId());
            Assert.assertTrue(allUsers.contains(user));
        });
    }

    @Test
    public void testFindOneUser() {
        when(userRepository.findOne(eq(1L))).thenReturn(admin);
        when(userRepository.findOne(eq(2L))).thenReturn(customer);
        when(userRepository.findOne(eq(3L))).thenReturn(guest);

        User u = userService.findOne(admin.getId());
        Assert.assertEquals(u, admin);

        u = userService.findOne(customer.getId());
        Assert.assertEquals(u, customer);

        u = userService.findOne(guest.getId());
        Assert.assertEquals(u, guest);
    }

    @Test
    public void testFindNonExistingUser() {
        long nonExistingUserId = 4L;
        when(userRepository.findOne(eq(nonExistingUserId))).thenReturn(null);

        User u = userService.findOne(nonExistingUserId);
        Assert.assertNull(u);
    }

    @Test
    public void testSaveUser() {
        when(userRepository.save(any(User.class))).thenReturn(admin);
        User u = userService.save(admin);
        Assert.assertEquals(admin, u);
    }

    @Test(expectedExceptions = {DataAccessException.class})
    public void testSaveInvalidUser() {
        when(userRepository.save(invalidUser)).thenThrow(new DataAccessException("Email not provided") {});

        userService.save(invalidUser);
    }

    @Test
    public void testFindUserByEmail() {
        when(userRepository.findByEmail(eq(admin.getEmail()))).thenReturn(admin);
        User u = userService.findByEmail(admin.getEmail());
        Assert.assertEquals(admin, u);
    }

    @Test
    public void testFindUserByNonExistingEmail() {
        when(userRepository.findByEmail(any(String.class))).thenReturn(null);
        User u = userService.findByEmail(admin.getEmail());
        Assert.assertNull(u);
    }

    @Test
    public void testDeleteUser() {
        when(userRepository.findOne(eq(admin.getId()))).thenReturn(admin).thenReturn(null);
        Assert.assertNotNull(userService.findOne(admin.getId()));
        userService.delete(admin.getId());
        Assert.assertNull(userService.findOne(admin.getId()));
    }

    @Test(expectedExceptions = {DataAccessException.class})
    public void testNonExistingUser() {
        long nonExistingUser = 4L;
        doThrow(new DataAccessException("Email not provided") {}).when(userRepository).delete(eq(nonExistingUser));

        userService.delete(nonExistingUser);
    }

    @Test
    public void testFindUsersWithOrdersLastSevenDays() {
        Order order = createOrder(admin);
        when(orderRepository.findByDateCreatedBetween(any(Date.class), any(Date.class)))
                .thenReturn(Collections.singletonList(order));

        Assert.assertTrue(userService.findUsersWithOrdersLastSevenDays().contains(admin));
    }

    private Order createOrder(User user) {
        Order order = new Order();
        order.setUser(user);
        order.setAddress("Botanicka 68, 60200 Brno");
        order.setPhone("+420 156 123 749");
        order.setPrice(new BigDecimal(3000));
        order.setTire(new Tire());
        order.setTireQuantity(4);
        order.setDateCreated(Calendar.getInstance().getTime());
        order.setState(OrderState.RECEIVED);
        return order;
    }

    private void createAdmin() {
        admin = new User();
        admin.setId(1L);
        admin.setEmail("admin@admin.com");
        admin.setName("Admin");
        admin.setPassword("password");
        admin.setRoles(Collections.singletonList(Role.ADMIN));
    }

    private void createCustomer() {
        customer = new User();
        customer.setId(2L);
        customer.setEmail("customer@customer.com");
        customer.setName("Customer");
        customer.setPassword("l;asdkklsadj");
        customer.setRoles(Collections.singletonList(Role.CUSTOMER));
    }

    private void createGuest() {
        guest = new User();
        guest.setId(3L);
        guest.setEmail("guest@guest.com");
        guest.setName("Guest");
        guest.setPassword("iuogaopdf4asd");
    }

    private void createInvalidUser() {
        invalidUser = new User();
        invalidUser.setName("Without email");
        invalidUser.setRoles(Collections.singletonList(Role.CUSTOMER));
        invalidUser.setPassword("ioasd4a>");
    }
}
