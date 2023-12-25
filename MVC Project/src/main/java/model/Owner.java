package model;

public class Owner {
    private String cnp;
    private String name;
    private String surname;


    public Owner() {
    }

    public Owner(String cnp, String name, String surname) {
        this.cnp = cnp;
        this.name = name;
        this.surname = surname;
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Owner owner)) return false;
        return this.cnp.equals(owner.getCnp())
                && this.name.equals(owner.getName())
                && this.surname.equals(owner.getSurname());
    }
}
