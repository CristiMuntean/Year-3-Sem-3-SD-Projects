package user_domain.view.language;

import java.io.Serializable;

public class OwnerFields implements Serializable {
    private String cnp;
    private String name;
    private String surname;

    public OwnerFields(String cnp, String name, String surname) {
        this.cnp = cnp;
        this.name = name;
        this.surname = surname;
    }

    public OwnerFields() {
    }

    public String getCnp() {
        return cnp;
    }

    public void setCnp(String cnp) {
        this.cnp = cnp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
