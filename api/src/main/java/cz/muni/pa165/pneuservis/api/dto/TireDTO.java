package cz.muni.pa165.pneuservis.api.dto;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author Martin Spisiak <spisiak@mail.muni.cz> on 23/11/2016.
 */
public class TireDTO {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private TireTypeDTO tireType;

    @NotNull
    private String size;

    @NotNull
    private String manufacturer;

    @NotNull
    private String vehicleType;

    @DecimalMin("0.0")
    @NotNull
    private BigDecimal price;

    public Long getId() { return id; }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() { return name; }

    public void setName(String name) {
        this.name = name;
    }

    public TireTypeDTO getTireType() { return tireType; }

    public void setTireType(TireTypeDTO tireType) {
        this.tireType = tireType;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TireDTO)) return false;

        TireDTO tireDTO = (TireDTO) o;

        if (!name.equals(tireDTO.name)) return false;
        if (tireType != tireDTO.tireType) return false;
        if (!size.equals(tireDTO.size)) return false;
        if (!manufacturer.equals(tireDTO.manufacturer)) return false;
        if (!vehicleType.equals(tireDTO.vehicleType)) return false;
        return price.equals(tireDTO.price);

    }

    @Override
    public int hashCode() {
        int result = 31 + name.hashCode();
        result = 31 * result + tireType.hashCode();
        result = 31 * result + size.hashCode();
        result = 31 * result + manufacturer.hashCode();
        result = 31 * result + vehicleType.hashCode();
        result = 31 * result + price.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "TireDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", tireType=" + tireType +
                ", size='" + size + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                ", vehicleType='" + vehicleType + '\'' +
                ", price=" + price +
                '}';
    }
}
