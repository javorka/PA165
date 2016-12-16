package cz.muni.pa165.pneuservis.mvc.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Michal Krajcovic <mkrajcovic@mail.muni.cz>
 */
public class UserEditForm {

    @NotNull
    @Size(min = 5, max = 50)
    private String name;

    @NotNull
    @Size(min = 6, max = 50)
    private String password;

    private String email;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
