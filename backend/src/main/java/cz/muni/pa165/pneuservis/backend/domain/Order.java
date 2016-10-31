package cz.muni.pa165.pneuservis.backend.domain;

//import cz.fi.muni.pa165.entity.OrderItem;
import cz.muni.pa165.pneuservis.backend.enums.OrderState;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Created by Michal Travnicek on 10/26/2016.
 */
@Entity
public class Order extends AbstractAuditedEntity {
    
    @ManyToOne(optional=false)
    @NotNull
    private User user;
    @NotNull
    private String address;
    @NotNull
    private String phone;
    @DecimalMin("0.0")
    @NotNull
    private BigDecimal price;
        
    @NotNull
    private Tire tire;
    
    @Min(1)
    @NotNull
    private Integer tireQuantity;
    
    @Enumerated
	@NotNull
	private OrderState state;



  public Order() {
  
  }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public Tire getTire() {
        return tire;
    }

    public void setTire(Tire tire) {
        this.tire = tire;
    }

    public Integer getTireQuantity() {
        return tireQuantity;
    }

    public void setTireQuantity(Integer tireQuantity) {
        this.tireQuantity = tireQuantity;
    }

    public OrderState getState() {
        return state;
    }

    public void setState(OrderState state) {
        this.state = state;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + Objects.hashCode(getUser());
        hash = 29 * hash + Objects.hashCode(getCreatedDate());
        hash = 29 * hash + Objects.hashCode(getState());
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
        if (!(obj instanceof Order)) return false;
        
        final Order other = (Order) obj;
        if (!Objects.equals(getState(), other.getState())) {
            return false;
        }
        if (!Objects.equals(getCreatedDate(), other.getCreatedDate())) {
            return false;
        }
        if (!Objects.equals(getUser(), other.getUser())) {
            return false;
        }
        return true;
    }
  
  
  
  
  
}
