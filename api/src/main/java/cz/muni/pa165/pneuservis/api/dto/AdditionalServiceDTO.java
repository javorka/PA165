package cz.muni.pa165.pneuservis.api.dto;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Created by peter on 11/18/16.
 */
public class AdditionalServiceDTO {

    private Long id;

    @NotNull
    private String name;

    private String description;

    @NotNull
    @DecimalMin("0.00")
    private BigDecimal price;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public int hashCode() {
        return 31 * ((name == null) ? 1 : name.hashCode());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof AdditionalServiceDTO)) {
            return false;
        }
        AdditionalServiceDTO other = (AdditionalServiceDTO) obj;
        if (name == null) {
            if (other.getName() != null) {
                return false;
            }
        } else if (!name.equals(other.getName()))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "AdditionalServiceDTO {" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                '}';
    }
}
