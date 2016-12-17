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

        if (getId() != null ? !getId().equals(tireDTO.getId()) : tireDTO.getId() != null) return false;
        if (getName() != null ? !getName().equals(tireDTO.getName()) : tireDTO.getName() != null) return false;
        if (getTireType() != tireDTO.getTireType()) return false;
        if (getSize() != null ? !getSize().equals(tireDTO.getSize()) : tireDTO.getSize() != null) return false;
        if (getManufacturer() != null ? !getManufacturer().equals(tireDTO.getManufacturer()) : tireDTO.getManufacturer() != null)
            return false;
        if (getVehicleType() != null ? !getVehicleType().equals(tireDTO.getVehicleType()) : tireDTO.getVehicleType() != null)
            return false;
        return getPrice() != null ? getPrice().equals(tireDTO.getPrice()) : tireDTO.getPrice() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getTireType() != null ? getTireType().hashCode() : 0);
        result = 31 * result + (getSize() != null ? getSize().hashCode() : 0);
        result = 31 * result + (getManufacturer() != null ? getManufacturer().hashCode() : 0);
        result = 31 * result + (getVehicleType() != null ? getVehicleType().hashCode() : 0);
        result = 31 * result + (getPrice() != null ? getPrice().hashCode() : 0);
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
