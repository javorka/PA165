package cz.muni.pa165.pneuservis.api.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author Michal Travnicek xtravni2
 */
public class OrderDTO {
    private Long id;
    private String address;
    private String phone;
    private BigDecimal price;
    private TireDTO tire;
    private Integer tireQuantity;
    private List<AdditionalServiceDTO> additionalServices;
    private Date dateCreated;
    private OrderStateDTO state;
    private UserDTO user;

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public TireDTO getTire() {
        return tire;
    }

    public void setTire(TireDTO tire) {
        this.tire = tire;
    }

    public Integer getTireQuantity() {
        return tireQuantity;
    }

    public void setTireQuantity(Integer tireQuantity) {
        this.tireQuantity = tireQuantity;
    }

    public List<AdditionalServiceDTO> getAdditionalServices() {
        return additionalServices;
    }

    public void setAdditionalServices(List<AdditionalServiceDTO> additionalServices) {
        this.additionalServices = additionalServices;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public OrderStateDTO getState() {
        return state;
    }

    public void setState(OrderStateDTO state) {
        this.state = state;
    }
    
}
