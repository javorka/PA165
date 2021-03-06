package cz.muni.pa165.pneuservis.mvc.form;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Michal Krajcovic <mkrajcovic@mail.muni.cz>
 */
public class NewOrderForm {

    @Min(value = 1, message="Must be greater than 0")
    private int tireQuantity;

    @NotNull
    private long tireId;

    @NotNull
    @Size(min = 12, max = 80, message = "Must be between {min} and {max}")
    private String address;

    @NotNull
    @Size(min = 3, max = 20, message = "Must be between {min} and {max}")
    @Pattern(regexp="\\+?[0-9]+", message= "Must match a phone number")
    private String phone;

    private List<Long> additionalServices = new ArrayList<>();


    public int getTireQuantity() {
        return tireQuantity;
    }

    public void setTireQuantity(int tireQuantity) {
        this.tireQuantity = tireQuantity;
    }

    public long getTireId() {
        return tireId;
    }

    public void setTireId(long tireId) {
        this.tireId = tireId;
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

    public List<Long> getAdditionalServices() {
        return additionalServices;
    }

    public void setAdditionalServices(List<Long> additionalServices) {
        this.additionalServices = additionalServices;
    }

    @Override
    public String toString() {
        return "NewOrderForm{" +
                "tireQuantity=" + tireQuantity +
                ", tireId=" + tireId +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", additionalServices=" + additionalServices +
                '}';
    }
}
