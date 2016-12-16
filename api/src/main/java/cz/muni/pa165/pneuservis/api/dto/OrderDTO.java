package cz.muni.pa165.pneuservis.api.dto;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author Michal Travnicek xtravni2
 */
public class OrderDTO {

    private Long id;

    @NotNull
    private String address;

    @NotNull
    private String phone;

    @DecimalMin("0.0")
    @NotNull
    private BigDecimal price;

    @NotNull
    private TireDTO tire;

    @NotNull
    @Min(1)
    private Integer tireQuantity;

    private List<AdditionalServiceDTO> additionalServices;

    private Date dateCreated = new Date();

    private OrderStateDTO state = OrderStateDTO.RECEIVED;

    @NotNull
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
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + Objects.hashCode(getUser());
        hash = 29 * hash + Objects.hashCode(getDateCreated());        
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof OrderDTO)) {
            return false;
        }

        final OrderDTO other = (OrderDTO) obj;
        
        if (!Objects.equals(getDateCreated(), other.getDateCreated())) {
            return false;
        }
        if (!Objects.equals(getUser(), other.getUser())) {
            return false;
        }
        return true;
    }
}
